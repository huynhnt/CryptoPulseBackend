## 1. Setup Dependencies and Configuration

- [x] 1.1 Thêm thư viện `spring-boot-starter-cache` và `caffeine` vào file `pom.xml`.
- [x] 1.2 Kích hoạt Caching bằng cách gắn annotation `@EnableCaching` vào class cấu hình chính (hoặc class Application).

## 2. Configure Caffeine Cache Buckets

- [x] 2.1 Tạo class `CacheConfig` có gắn `@Configuration`.
- [x] 2.2 Khởi tạo bean `CacheManager` sử dụng `CaffeineCacheManager`.
- [x] 2.3 Cấu hình Cache bucket `coins_page` với TTL là 1 phút và `maximumSize` phù hợp (VD: 500).
- [x] 2.4 Cấu hình Cache bucket `coin_detail` với TTL là 1 phút và `maximumSize` phù hợp.
- [x] 2.5 Cấu hình Cache bucket `coin_chart` với TTL là 5 phút và `maximumSize` phù hợp.

## 3. Apply Cacheable to Service Methods

- [x] 3.1 Gắn `@Cacheable(value = "coins_page")` vào hàm gọi API `/coins/markets` trong Service.
- [x] 3.2 Gắn `@Cacheable(value = "coin_chart")` vào hàm gọi API `/coins/{id}/market_chart` trong Service.
- [x] 3.3 Gắn `@Cacheable(value = "coin_detail")` vào hàm gọi API `/coins/{id}` trong Service.

## 4. Refactor Base URL Configuration

- [x] 4.1 Thêm thuộc tính `coingecko.api.baseUrl=https://api.coingecko.com/api/v3` vào file `application.properties` (hoặc `application.yml`).
- [x] 4.2 Trong `CoinServiceImpl`, dùng `@Value("${coingecko.api.baseUrl}")` để lấy giá trị base URL.
- [x] 4.3 Cập nhật tất cả các URL hardcode trong `CoinServiceImpl` để sử dụng biến `baseUrl`.
