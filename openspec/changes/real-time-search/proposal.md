## Why

Status: completed

API `/search` hiện tại của CoinGecko chỉ cung cấp siêu dữ liệu (metadata) của các đồng coin (id, tên, ký hiệu, hình thu nhỏ) nhưng thiếu các dữ liệu tài chính theo thời gian thực quan trọng như giá hiện tại, phần trăm thay đổi, và dữ liệu biểu đồ (sparkline). Khi người dùng tìm kiếm, họ kỳ vọng sẽ thấy đầy đủ thông tin ngữ cảnh về biến động thị trường của coin đó ngay lập tức, tương tự như trên màn hình chủ hoặc màn hình "All Coins". Chúng ta cần một giải pháp kết hợp (hybrid) tại Backend: gọi API tìm kiếm, sau đó làm giàu kết quả với dữ liệu thị trường chi tiết trước khi trả về cho Frontend. Ngoài ra, để tránh bị chặn do giới hạn request (rate-limiting) và tăng tốc độ phản hồi, toàn bộ quá trình này cần được áp dụng cơ chế Cache.

## What Changes

- Sửa đổi endpoint search hiện tại trong `CoinService` để thực hiện quy trình 2 bước:
  1. Gọi API Search của CoinGecko để lấy danh sách ID của các coin phù hợp nhất với từ khóa.
  2. Gọi API Markets của CoinGecko sử dụng các ID vừa lấy, kèm theo tham số `sparkline=true` để lấy toàn bộ dữ liệu thị trường.
- Giới hạn kết quả làm giàu tối đa 10 mục để duy trì hiệu năng cao.
- Thêm cơ chế cache (`@Cacheable`) cho tính năng tìm kiếm: tạo một bộ đệm (cache) sống lâu cho việc map từ khóa tìm kiếm ra danh sách ID để tối ưu việc gọi API bên ngoài.
- Cập nhật định dạng response trả về thành `List<Coin>` (giống hệt định dạng của danh sách coin chính), cho phép Frontend tái sử dụng Widget `CoinListItem` một cách hoàn hảo mà không cần code thêm UI mới.

## Capabilities

### New Capabilities

- `real-time-search`: Triển khai API tìm kiếm theo thời gian thực siêu tốc, có áp dụng cache. API này trả về dữ liệu thị trường đầy đủ kèm biểu đồ sparkline cho tối đa 10 đồng coin khớp với từ khóa, bằng cách kết hợp gọi API Search và API Markets của CoinGecko.

### Modified Capabilities

- Không có.

## Impact

- `CoinService` và `CoinServiceImpl` sẽ được cập nhật để xử lý việc gọi kết hợp 2 API.
- Cấu hình Cache của Spring Boot sẽ cần cập nhật hoặc thêm mới để định nghĩa TTL (Thời gian sống) cho `searchKeywordCache`.
