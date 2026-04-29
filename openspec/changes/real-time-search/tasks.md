## 1. Phân tích Dữ liệu và Thiết lập API Search (Backend)

- [x] 1.1 Tạo record/DTO mới để hứng dữ liệu từ API `/search` của CoinGecko (VD: `CoinGeckoSearchResponse`, `CoinGeckoSearchItem`) vì cấu trúc trả về khác với `Coin` thông thường.
- [x] 1.2 Bổ sung cấu hình Cache: Kiểm tra xem `@EnableCaching` đã được khai báo hay chưa, cấu hình bộ đệm cho `searchKeywordCache`.

## 2. Cập nhật CoinService (Backend)

- [x] 2.1 Cập nhật hàm `searchCoin(String keyword)` trong `CoinServiceImpl`:
  - Gọi API `https://api.coingecko.com/api/v3/search?query={keyword}` bằng RestTemplate.
  - Parse kết quả, trích xuất tối đa 10 `id` của các đồng coin đứng đầu.
  - Ghép danh sách ID thành một chuỗi (VD: `bitcoin,ethereum`).
- [x] 2.2 Gọi API Markets:
  - Nếu chuỗi ID không rỗng, truyền chuỗi ID này vào API `/coins/markets` (cùng tham số `sparkline=true`).
  - Lấy kết quả trả về dưới dạng `List<Coin>`.
- [x] 2.3 Tối ưu hóa với Cache: Thêm annotation `@Cacheable(value = "searchKeywordCache", key = "#keyword")` vào phương thức.

## 3. Cập nhật CoinController (Backend)

- [x] 3.1 Cập nhật hoặc thêm mới endpoint `GET /coins/search` trong `CoinController`.
- [x] 3.2 Nhận request parameter `keyword` (hoặc `q`), gọi tới `coinService.searchCoin(keyword)`.
- [x] 3.3 Trả về `ResponseEntity<List<Coin>>` cho client.

## 4. Kiểm thử Backend

- [x] 4.1 Khởi động lại Spring Boot application.
- [x] 4.2 Gọi API `/coins/search?q=bit` bằng trình duyệt hoặc Postman. Xác minh kết quả trả về là một mảng (tối đa 10 phần tử), trong đó có chứa dữ liệu `current_price`, `price_change_percentage_24h` và `sparkline_in_7d`.
- [x] 4.3 Gọi lại API cùng từ khóa ngay lập tức để kiểm chứng tốc độ phản hồi cực nhanh từ Cache.
