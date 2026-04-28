# Design — backend-coin-api-enhance

## Architecture Overview

```
Flutter App
    │
    ▼
Spring Boot Backend (localhost:8088)
    │
    ├── GET /coins?page=n          → CoinGecko /coins/markets (+ sparkline=true)
    ├── GET /coins/{id}/chart      → CoinGecko /coins/{id}/market_chart
    ├── GET /coins/details/{id}    → CoinGecko /coins/{id}
    ├── GET /coins/top50           → CoinGecko /coins/markets?per_page=50
    ├── GET /coins/search?q=       → CoinGecko /search
    └── GET /coins/trading         → CoinGecko /search/trending
    │
    ▼
CoinGecko API (với API key, rate-limited)
```

## Chi tiết thay đổi

### 1. Thêm sparkline vào `GET /coins?page=n`

**Vấn đề:** `CoinServiceImpl.getCoinList()` gọi CoinGecko với URL không có `sparkline=true`, nên CoinGecko không trả về dữ liệu sparkline. Sau đó map vào entity `Coin` (không có field sparkline) và lưu DB.

**Giải pháp:**
- Thêm field `sparkline` vào `Coin.java` entity: `List<Double> sparkline`
- Map với JSON key `sparkline_in_7d.price` từ CoinGecko response
- Dùng `@Transient` để không lưu vào DB (sparkline là dữ liệu real-time, không cần persist)
- Thêm `sparkline=true` vào URL gọi CoinGecko trong `CoinServiceImpl`

**Response format mới của `GET /coins?page=n`:**
```json
[
  {
    "id": "bitcoin",
    "symbol": "btc",
    "name": "Bitcoin",
    "image": "https://...",
    "current_price": 65000.0,
    "market_cap_rank": 1,
    "price_change_percentage_24h": 2.5,
    "high_24h": 66000.0,
    "low_24h": 64000.0,
    "total_volume": 30000000000,
    "total_supply": 21000000,
    "sparkline": [64000.0, 64500.0, 65000.0, ...]
  }
]
```

### 2. Chuẩn hóa `GET /coins/{id}/chart?days=n`

**Hiện tại:** Trả về raw JSON từ CoinGecko: `{ "prices": [[timestamp, price], ...], "market_caps": [...], "total_volumes": [...] }`

**Flutter cần:** Chỉ cần mảng giá — `List<double>` để vẽ chart.

**Giải pháp:** Thêm một endpoint mới hoặc transform response. Ta sẽ **giữ nguyên raw format** vì Flutter có thể tự parse `prices[1]`. Chỉ cần đảm bảo response trả về đúng Content-Type `application/json`.

> Quyết định: Giữ raw format, không transform. Flutter sẽ tự parse ở tầng data layer.

### 3. Fix CORS

**Hiện tại** trong `AppConfig.java`:
```java
cfg.setAllowedOrigins(Arrays.asList(
    "http://localhost:3000",
    "http://localhost:5173",
    "http://localhost:5174",
    "http://localhost:4200"
));
```

**Thay đổi:** Trong môi trường dev, mở rộng CORS để cho phép tất cả origins:
```java
cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
```

Hoặc thêm cụ thể Flutter web port. Vì đây là dev-only, dùng `allowedOriginPatterns("*")` cho đơn giản.

**Lưu ý:** `setAllowedOrigins("*")` không dùng được khi `setAllowCredentials(true)`. Cần dùng `setAllowedOriginPatterns("*")` thay thế.

### 4. Đổi Port sang 8088

Thêm vào `application.properties`:
```properties
server.port=8088
```

## Files sẽ thay đổi

| File | Thay đổi |
|---|---|
| `src/main/java/com/trading/modal/Coin.java` | Thêm `@Transient List<Double> sparkline` với `@JsonProperty("sparkline_in_7d")` custom deserializer |
| `src/main/java/com/trading/service/CoinServiceImpl.java` | Thêm `sparkline=true&sparkline_days=7` vào URL gọi CoinGecko |
| `src/main/java/com/trading/config/AppConfig.java` | Fix CORS dùng `allowedOriginPatterns("*")` |
| `src/main/resources/application.properties` | Thêm `server.port=8088` |

## Sparkline Deserialization Strategy

CoinGecko trả về sparkline theo format:
```json
"sparkline_in_7d": { "price": [64000.0, 64200.0, ...] }
```

Trong `Coin.java`, dùng custom `@JsonDeserialize` hoặc `@JsonProperty` với nested object:
```java
@Transient
@JsonProperty("sparkline_in_7d")
@JsonDeserialize(using = SparklineDeserializer.class)
private List<Double> sparkline;
```

Viết `SparklineDeserializer` để extract `price` array từ nested object.
