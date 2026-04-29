## Context

Ứng dụng CryptoPulse (Frontend) lấy dữ liệu tiền điện tử rất thường xuyên để hiển thị danh sách và biểu đồ. Backend hiện tại đóng vai trò là một middleware, gọi trực tiếp sang API của CoinGecko cho mỗi request. Do CoinGecko có giới hạn Rate Limit cực kỳ nghiêm ngặt đối với bản miễn phí, hệ thống rất dễ gặp lỗi 429 (Too Many Requests). Việc tích hợp giải pháp Caching In-Memory là yêu cầu bắt buộc để duy trì tính ổn định và tốc độ cho ứng dụng.

## Goals / Non-Goals

**Goals:**
- Triển khai Caching cho các lời gọi API tới CoinGecko.
- Giảm thiểu đáng kể số lượng HTTP requests đi ra ngoài.
- Đảm bảo thời gian phản hồi (latency) của API Backend nằm ở mức miligiây cho các dữ liệu đã được cache.
- Thiết lập cấu hình TTL (Time-To-Live) chuyên biệt cho từng loại dữ liệu (danh sách vs biểu đồ) nhằm bảo đảm độ tươi mới tối ưu.

**Non-Goals:**
- Tích hợp Distributed Cache (như Redis hay Memcached). Vì backend hiện tại hoạt động độc lập (single-instance), In-Memory Cache là giải pháp hoàn hảo và tối giản nhất.
- Triển khai cơ chế tự động làm mới ngầm (background refresh). Hệ thống chỉ thực hiện cache hit/miss thông thường.

## Decisions

- **Caffeine vs Redis**: Chọn **Caffeine**. Đây là một thư viện In-Memory Cache hiệu năng cực cao dành cho Java 8+, được viết bằng Java và vượt trội hơn Guava Cache. Nó chạy trực tiếp trong JVM, không cần cài đặt thêm server ngoài, giúp đơn giản hóa cấu hình.
- **Spring Cache Abstraction**: Sử dụng cặp annotation `@EnableCaching` và `@Cacheable`. Việc này giúp tách biệt hoàn toàn logic Caching ra khỏi Business Logic, code sẽ gọn gàng và dễ bảo trì hơn rất nhiều.
- **Multiple Cache Managers / Caches**: Sẽ định nghĩa một class `CacheConfig` (hoặc cấu hình application.properties tuỳ hỗ trợ) để tạo nhiều Cache Buckets với các cấu hình TTL khác nhau:
  - `coins_page` (TTL 1 phút): Cho danh sách coins.
  - `coin_chart` (TTL 5 phút): Cho dữ liệu market chart.
  - `coin_detail` (TTL 1 phút): Cho chi tiết đồng coin.
- **Tách Base URL API**: Di chuyển chuỗi `https://api.coingecko.com/api/v3` vào file cấu hình (ví dụ `application.properties` thông qua annotation `@Value` hoặc `application.yml`) để quản lý tập trung và tránh lặp code (DRY - Don't Repeat Yourself) bên trong `CoinServiceImpl`.

## Risks / Trade-offs

- **Dữ liệu trễ nhịp (Stale Data)**: Trade-off lớn nhất là người dùng có thể thấy giá chậm tối đa 1 phút. -> **Mitigation**: Đối với một Dashboard theo dõi thị trường thông thường, độ trễ 1 phút là con số lý tưởng để tối ưu, không ảnh hưởng lớn đến trải nghiệm người dùng so với rủi ro trắng trang do 429.
- **Tiêu tốn bộ nhớ (Memory Leak/Consumption)**: Cache dữ liệu biểu đồ dài hạn trong RAM có thể ngốn tài nguyên. -> **Mitigation**: Caffeine cho phép cấu hình `maximumSize`. Ta sẽ thiết lập giới hạn lưu trữ tối đa (ví dụ 1000 items) để tự động giải phóng RAM khi vượt ngưỡng.
