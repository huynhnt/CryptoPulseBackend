## Why

Ứng dụng Frontend thường xuyên gọi API lấy dữ liệu coin (top coins, biểu đồ, chi tiết) thông qua Backend. Hiện tại, mỗi request từ Frontend đều yêu cầu Backend gọi trực tiếp sang CoinGecko API. Điều này dẫn đến nguy cơ rất cao bị chặn bởi giới hạn request của CoinGecko (lỗi 429 Too Many Requests), đồng thời làm tăng độ trễ tổng thể của hệ thống khi phản hồi lại người dùng.

## What Changes

- Kích hoạt tính năng Caching của Spring Boot (sử dụng `@EnableCaching`).
- Tích hợp thư viện `Caffeine` như giải pháp In-Memory Cache.
- Thiết lập các chính sách TTL (Time-To-Live) phân tách theo từng loại dữ liệu:
  - Danh sách coins (phân trang): TTL = 1 phút.
  - Biểu đồ giá (Market chart): TTL = 5 phút.
  - Chi tiết Coin: TTL = 1 phút.
- Gắn annotation `@Cacheable` vào các phương thức gọi tới CoinGecko tương ứng trong Service.

## Capabilities

### New Capabilities
- `api-caching`: Tự động lưu lại các API responses từ CoinGecko vào bộ nhớ đệm nội bộ (Caffeine) với các chính sách TTL tuỳ biến theo từng loại dữ liệu để tối ưu hoá hiệu năng và tuân thủ Rate Limit.

### Modified Capabilities
- `<existing-name>`: (Không có yêu cầu cũ bị thay đổi, chỉ thêm mới cơ chế hạ tầng).

## Impact

- **Dependencies**: Cần bổ sung `spring-boot-starter-cache` và `com.github.ben-manes.caffeine:caffeine` vào file `pom.xml`.
- **Performance**: Tốc độ phản hồi của API sẽ được cải thiện đáng kể nhờ dữ liệu lấy trực tiếp từ RAM, giảm mạnh số lượng outbound request thực tế gọi ra CoinGecko.
- **Application Configuration**: Class Main (chứa `@SpringBootApplication`) hoặc một class Configuration riêng sẽ cần thêm `@EnableCaching`.
- **Service Layer**: Các class Service giao tiếp với CoinGecko sẽ thay đổi cơ chế trả về (thông qua Proxy caching).
