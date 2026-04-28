# CryptoPulse Backend

Spring Boot backend middleware cho ứng dụng CryptoPulse Flutter.

## Kiến trúc

```
CryptoPulse (Flutter)  →  CryptoPulse Backend (Spring Boot)  →  CoinGecko API
                              localhost:8080
```

Backend đóng vai trò **proxy / middleware**:
- Nhận request từ Flutter app
- Gọi CoinGecko API
- Cache kết quả để tránh rate limit
- Trả về dữ liệu đã được chuẩn hóa

## API Endpoints

| Method | Endpoint | Mô tả |
|--------|----------|--------|
| `GET` | `/api/coins/markets` | Danh sách top coins theo market cap |
| `GET` | `/api/coins/{id}` | Chi tiết một coin |
| `GET` | `/api/coins/{id}/market-chart?days=7` | Lịch sử giá (hỗ trợ: 1, 7, 30, 90, 365) |
| `GET` | `/actuator/health` | Health check |

## Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Maven**
- **WebClient** (reactive HTTP client gọi CoinGecko)
- **Caffeine Cache** (cache response 60 giây)
- **Lombok**
- **Spring Actuator**

## Chạy local

```bash
mvn spring-boot:run
```

Server khởi động tại `http://localhost:8080`

## Cấu hình

Xem `src/main/resources/application.properties` để điều chỉnh:
- Port server
- Thời gian cache
- Base URL CoinGecko
- API Key CoinGecko (nếu có)
