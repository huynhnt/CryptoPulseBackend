## Context

Hệ thống hiện tại đang gặp vấn đề với tính năng tìm kiếm. API `/search` của CoinGecko trả về thông tin rất nhanh nhưng thiếu dữ liệu giá (price), phần trăm thay đổi (24h change) và biểu đồ nhỏ (sparkline). Để có giao diện nhất quán với danh sách toàn bộ coin (All Coins) và mang lại trải nghiệm đầy đủ cho người dùng, Frontend cần dữ liệu theo cấu trúc `CoinListItem`. Do đó, Backend cần nhận trách nhiệm gọi API `/search`, lọc ra các ID hàng đầu, và gọi tiếp API `/coins/markets` để lấy dữ liệu trọn vẹn.

## Goals / Non-Goals

**Goals:**
- Tích hợp 2 API của CoinGecko (`/search` và `/coins/markets`) tại Backend để trả về danh sách `List<Coin>` hoàn chỉnh (bao gồm sparkline) cho từ khóa tìm kiếm.
- Cấu hình Cache (`@Cacheable`) cho kết quả tìm kiếm từ khóa để tránh bị giới hạn API (rate-limiting) và tăng tốc phản hồi cho các từ khóa trùng lặp.
- Đảm bảo thời gian phản hồi nhanh nhất có thể bằng cách chỉ lấy dữ liệu thị trường cho tối đa 10 đồng coin đầu tiên.

**Non-Goals:**
- Không hỗ trợ tìm kiếm theo nhiều tiêu chí phức tạp khác ngoài tên/symbol của coin.
- Không lưu trữ dữ liệu giá lịch sử của coin trong database nội bộ (vẫn dựa hoàn toàn vào CoinGecko).
- Không tự xây dựng hệ thống search engine riêng.

## Decisions

1. **Kiến trúc Hybrid 2 bước:**
   - **Bước 1:** Gọi `https://api.coingecko.com/api/v3/search?query={keyword}`. Trích xuất danh sách `id` của tối đa 10 kết quả đầu tiên.
   - **Bước 2:** Xây dựng chuỗi tham số `ids` (ví dụ `bitcoin,ethereum`) và truyền vào API `https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids={ids}&sparkline=true`.
   - Trả về mảng `List<Coin>` thu được từ bước 2.

2. **Cấu hình Cache:**
   - Tạo hoặc tái sử dụng cơ chế Cache trong Spring Boot.
   - Thay vì cache danh sách ID quá lâu và giá cả quá ngắn một cách rời rạc, chúng ta sẽ áp dụng `@Cacheable` cho toàn bộ kết quả `List<Coin>` đầu ra của hàm `searchCoin` với key là `keyword`.
   - Thời gian tồn tại của cache (TTL) sẽ tuân theo cấu hình mặc định (ví dụ 1-5 phút) để đảm bảo giá được làm mới mà không spam gọi API CoinGecko mỗi khi người dùng gõ lại cùng một chữ (do tính chất search-as-you-type).

## Risks / Trade-offs

- **Độ trễ khi chưa có cache (Cold Start):** Lần tìm kiếm đầu tiên của một từ khóa sẽ chậm hơn vì phải gọi qua 2 API của CoinGecko liên tiếp. Trade-off: Giới hạn 10 kết quả giúp API thứ 2 trả về cực nhanh, bù đắp lại độ trễ này.
- **Giới hạn Rate-Limit của CoinGecko:** Dù đã có cache, nếu nhiều người dùng gõ tìm kiếm các từ khóa khác nhau liên tục, Backend vẫn có nguy cơ chạm ngưỡng rate-limit. Giải pháp bắt buộc đi kèm là Frontend phải có tính năng "Debounce" (chờ 300ms sau khi ngừng gõ mới gửi yêu cầu).
