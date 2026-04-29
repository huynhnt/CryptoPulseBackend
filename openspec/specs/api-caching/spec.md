# Capability: api-caching

## Yêu cầu bổ sung

### Yêu cầu: Backend lưu bộ nhớ đệm (cache) danh sách coin
Hệ thống backend PHẢI cache kết quả trả về của API `/coins/markets` từ CoinGecko trong 1 phút để tránh bị chặn (rate limiting) đồng thời vẫn giữ cho dữ liệu được cập nhật mới.

#### Kịch bản: Gọi liên tiếp API danh sách coin
- **KHI** frontend yêu cầu danh sách các đồng coin hàng đầu nhiều lần trong vòng 1 phút
- **THÌ** backend chỉ thực hiện gọi thực tế sang API CoinGecko ở lần yêu cầu đầu tiên
- **VÀ** trả về dữ liệu từ bộ nhớ đệm (cache) cho tất cả các yêu cầu tiếp theo trong khoảng thời gian 1 phút đó

### Yêu cầu: Backend lưu bộ nhớ đệm (cache) biểu đồ giá
Hệ thống backend PHẢI cache kết quả trả về của API `/coins/{id}/market_chart` từ CoinGecko trong 5 phút, do dữ liệu biểu đồ lịch sử ít nhạy cảm với các biến động từng phút.

#### Kịch bản: Gọi liên tiếp API biểu đồ giá
- **KHI** frontend yêu cầu dữ liệu biểu đồ của một đồng coin cụ thể nhiều lần trong vòng 5 phút
- **THÌ** backend chỉ thực hiện gọi thực tế sang API CoinGecko ở lần yêu cầu đầu tiên
- **VÀ** trả về dữ liệu từ bộ nhớ đệm (cache) cho tất cả các yêu cầu tiếp theo trong khoảng thời gian 5 phút đó

### Yêu cầu: Backend lưu bộ nhớ đệm (cache) chi tiết coin
Hệ thống backend PHẢI cache kết quả trả về của API `/coins/{id}` từ CoinGecko trong 1 phút.

#### Kịch bản: Gọi liên tiếp API chi tiết coin
- **KHI** frontend yêu cầu dữ liệu chi tiết của một đồng coin cụ thể nhiều lần trong vòng 1 phút
- **THÌ** backend chỉ thực hiện gọi thực tế sang API CoinGecko ở lần yêu cầu đầu tiên
- **VÀ** trả về dữ liệu từ bộ nhớ đệm (cache) cho tất cả các yêu cầu tiếp theo trong khoảng thời gian 1 phút đó

### Yêu cầu: Cấu hình tập trung Base URL cho CoinGecko
Hệ thống backend PHẢI trích xuất đoạn Base URL đang bị gán cứng (hardcode) là `https://api.coingecko.com/api/v3` thành một biến cấu hình (`coingecko.api.baseUrl`) bên trong file `application.properties`.

#### Kịch bản: Gọi API của CoinGecko
- **VỚI GIẢ ĐỊNH** ứng dụng đã cấu hình thuộc tính `coingecko.api.baseUrl=https://api.coingecko.com/api/v3`
- **KHI** `CoinServiceImpl` tạo bất kỳ request nào đến CoinGecko
- **THÌ** nó phải tự động nối endpoint tương ứng vào Base URL được lấy từ cấu hình để tạo ra một đường dẫn URL hoàn chỉnh
