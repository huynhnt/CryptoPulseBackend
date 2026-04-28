---
name: springboot-clean-feature
description: Tự động khởi tạo cấu trúc thư mục và các file mẫu cho một tính năng (feature/endpoint group) mới theo chuẩn Layered Architecture trong Spring Boot.
---

# Spring Boot Clean Feature Skill

Sử dụng skill này khi bạn cần bắt đầu xây dựng một nhóm endpoint mới cho ứng dụng CryptoPulse Backend.

## Cấu trúc thư mục mục tiêu

Mỗi tính năng mới sẽ được tạo tại `src/main/java/com/cryptopulse/backend/<feature>/` với cấu trúc:

```
<feature>/
├── controller/
│   └── <Feature>Controller.java    (REST endpoints, @RestController)
├── service/
│   └── <Feature>Service.java       (Business logic, WebClient calls)
└── model/
    ├── <Feature>Response.java      (DTO trả về cho client)
    └── <Feature>Request.java       (DTO nhận từ client - nếu cần)
```

## Quy trình thực hiện

1. **Phân tích yêu cầu**: Xác định tên tính năng và các endpoint cần xây dựng.
2. **Tạo thư mục**: Sử dụng lệnh terminal để tạo toàn bộ cấu trúc thư mục trên.
3. **Gen code mẫu**:
    - Tạo `<Feature>Response.java` trong `model/` — record hoặc class với Lombok.
    - Tạo `<Feature>Service.java` trong `service/` — inject `WebClient`, gọi CoinGecko API.
    - Tạo `<Feature>Controller.java` trong `controller/` — `@RestController`, `@RequestMapping`.

## Tiêu chuẩn code

- Luôn sử dụng **`WebClient`** (reactive, non-blocking) để gọi CoinGecko API, không dùng `RestTemplate`.
- Luôn thêm **`@CrossOrigin`** ở controller để hỗ trợ Flutter Web và emulator.
- Dùng **Lombok** (`@Data`, `@Builder`, `@RequiredArgsConstructor`) để giảm boilerplate.
- Dùng **`@Cacheable`** ở service methods khi phù hợp để cache response từ CoinGecko.
- Base URL CoinGecko phải lấy từ `WebClient` bean đã được config trong `WebClientConfig.java`, không hardcode.

## Ví dụ lệnh tạo nhanh (PowerShell)

```powershell
$f="coin"; $base="src/main/java/com/cryptopulse/backend"
mkdir "$base/$f/controller", "$base/$f/service", "$base/$f/model"
```

## Lưu ý

- Đảm bảo các endpoint mới được document trong `README.md` ở phần **API Endpoints**.
- Kiểm tra CORS config trong `WebClientConfig.java` nếu có vấn đề kết nối từ Flutter.
