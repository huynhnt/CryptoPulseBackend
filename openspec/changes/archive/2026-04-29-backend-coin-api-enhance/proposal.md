# backend-coin-api-enhance

## What & Why

Hiện tại Flutter app (CryptoPulse) đang gọi thẳng CoinGecko API, bỏ qua backend hoàn toàn.
Mục tiêu là chuyển Flutter sang dùng backend làm trung gian (proxy pattern), nhưng để làm được điều đó, backend cần được nâng cấp để đáp ứng đầy đủ dữ liệu mà Flutter cần.

**3 vấn đề cần fix trước khi Flutter có thể dùng backend:**

1. **Thiếu sparkline data** — `GET /coins?page=n` trả về `List<Coin>` từ DB, nhưng entity `Coin` không có trường `sparkline_in_7d`. Flutter dashboard dùng sparkline để vẽ mini-chart cho mỗi coin.

2. **Format market chart không chuẩn** — `GET /coins/{id}/chart?days=n` trả về raw JSON từ CoinGecko (format `{ prices: [[timestamp, price], ...] }`). Flutter cần parse đúng format này hoặc backend cần chuẩn hóa response.

3. **CORS chưa cho phép Flutter** — `AppConfig.java` chỉ whitelist `localhost:3000/5173/5174/4200`. Flutter Web (dev) chạy trên các port khác sẽ bị block. Cần mở CORS cho dev environment.

4. **Port** — Đổi server port từ `8080` sang `8088` để tránh xung đột với các service khác.

## Scope

- Chỉ thay đổi backend (`CryptoPulseBackend`)
- Không thay đổi Flutter app trong change này
- Không thay đổi business logic (order, wallet, payment)
- Chỉ tập trung vào Coin-related APIs và server config

## Out of Scope

- JWT authentication (sẽ làm sau)
- Caching với Caffeine (có thể làm sau như một optimization riêng)
- Các API khác ngoài Coin (watchlist, asset, order...)
- Deploy production
