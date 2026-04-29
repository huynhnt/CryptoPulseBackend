## ADDED Requirements

### Requirement: Search API Kết hợp Dữ liệu Market (Hybrid API)
Hệ thống **SHALL** nhận một từ khóa (`keyword`), truy xuất danh sách ID coin phù hợp nhất từ CoinGecko Search API, và lấy dữ liệu giá, phần trăm thay đổi, cùng biểu đồ (sparkline) từ CoinGecko Markets API.

#### Scenario: Tìm kiếm với từ khóa hợp lệ
- **WHEN** Backend nhận được yêu cầu `GET /api/v1/coins/search?keyword=bit` (hoặc định dạng tương đương).
- **THEN** Backend gọi API `/search?query=bit` của CoinGecko để lấy mảng ID (lấy tối đa 10 IDs đầu tiên).
- **AND THEN** Backend gọi tiếp API `/coins/markets` của CoinGecko với tham số là chuỗi các ID trên và `sparkline=true`.
- **AND THEN** Backend trả về cho Client một mảng JSON định dạng `List<Coin>` hoàn chỉnh với biểu đồ.

### Requirement: Cơ chế Caching
Hệ thống **SHALL** lưu trữ (cache) kết quả trả về của hàm tìm kiếm theo từ khóa để tối ưu hóa hiệu năng và tránh bị CoinGecko rate-limit.

#### Scenario: Tìm kiếm lại cùng từ khóa
- **WHEN** Có một yêu cầu tìm kiếm với `keyword=eth`.
- **THEN** Backend gọi API bên ngoài, cache lại kết quả trả về với key là `eth`.
- **AND WHEN** Có một yêu cầu tìm kiếm khác với cùng `keyword=eth` trong khoảng thời gian hiệu lực của cache (ví dụ 1-5 phút).
- **THEN** Backend bỏ qua việc gọi tới CoinGecko và trả về kết quả lập tức từ bộ nhớ cache.
