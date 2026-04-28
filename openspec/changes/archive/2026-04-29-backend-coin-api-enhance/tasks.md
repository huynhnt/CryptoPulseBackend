# Tasks — backend-coin-api-enhance

## Status: ready

---

## Task 1 — Đổi port sang 8088

**File:** `src/main/resources/application.properties`

- [x] Thêm `server.port=8088`
- [ ] Verify: chạy app và kiểm tra log `Tomcat started on port 8088`

---

## Task 2 — Fix CORS cho Flutter

**File:** `src/main/java/com/trading/config/AppConfig.java`

- [x] Thay `cfg.setAllowedOrigins(Arrays.asList(...))` bằng `cfg.setAllowedOriginPatterns(Collections.singletonList("*"))`
- [x] Đảm bảo `setAllowCredentials(true)` vẫn giữ nguyên (tương thích với `allowedOriginPatterns`)
- [ ] Test: Gửi request từ origin bất kỳ và kiểm tra response header `Access-Control-Allow-Origin`

---

## Task 3 — Thêm SparklineDeserializer

**File mới:** `src/main/java/com/trading/utils/SparklineDeserializer.java`

- [x] Tạo class `SparklineDeserializer extends JsonDeserializer<List<Double>>`
- [x] Implement `deserialize()`: đọc field `price` từ nested JSON object `{ "price": [...] }`
- [x] Xử lý trường hợp null (CoinGecko không trả sparkline)

```java
// Logic cần implement:
// Input:  { "price": [64000.0, 64200.0, 65000.0] }
// Output: [64000.0, 64200.0, 65000.0]
```

---

## Task 4 — Thêm field sparkline vào Coin entity

**File:** `src/main/java/com/trading/modal/Coin.java`

- [x] Thêm import: `jakarta.persistence.Transient`, `com.fasterxml.jackson.databind.annotation.JsonDeserialize`
- [x] Thêm field:
  ```java
  @Transient
  @JsonProperty("sparkline_in_7d")
  @JsonDeserialize(using = SparklineDeserializer.class)
  private List<Double> sparkline;
  ```
- [x] Thêm import `java.util.List` nếu chưa có
- [x] Verify: field được serialize ra JSON với key `sparkline` khi response về Flutter

---

## Task 5 — Cập nhật CoinServiceImpl để request sparkline từ CoinGecko

**File:** `src/main/java/com/trading/service/CoinServiceImpl.java`

- [x] Cập nhật URL trong `getCoinList()`:
  ```java
  // Trước:
  String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
  
  // Sau:
  String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page
             + "&sparkline=true";
  ```
- [x] Verify: Response JSON từ CoinGecko bây giờ có field `sparkline_in_7d`
- [x] Verify: Sau khi map, `Coin` object có `sparkline` list populated

---

## Task 6 — Verify toàn bộ

- [x] Chạy backend trên port 8088
- [x] Test `GET http://localhost:8088/coins?page=1` — kiểm tra response có field `sparkline`
- [x] Test `GET http://localhost:8088/coins/{id}/chart?days=7` — kiểm tra format JSON
- [x] Test CORS: gửi request với header `Origin: http://localhost:9999` — kiểm tra không bị block
- [x] Test `GET http://localhost:8088/coins/details/bitcoin` — vẫn hoạt động bình thường
