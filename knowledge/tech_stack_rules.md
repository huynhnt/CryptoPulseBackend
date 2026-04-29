# Quy tắc kỹ thuật (Tech Stack Rules)

Quy định về kiến trúc và công nghệ sử dụng trong dự án CryptoPulseBackend.

## 1. Kiến trúc (Architecture)
- Dự án tuân thủ **Layered Architecture** (Kiến trúc phân lớp) chia làm các lớp chính:
    - **Controller**: Xử lý request/response HTTP, routing và validation dữ liệu đầu vào.
    - **Service**: Chứa business logic cốt lõi.
    - **Repository**: Tương tác với cơ sở dữ liệu.
    - **Model/Entity/DTO**: Cấu trúc dữ liệu, phân tách rõ ràng giữa Entity (cấu trúc bảng Database) và DTO (Data Transfer Object cho API).

## 2. Công nghệ cốt lõi
- Sử dụng **Java** và **Spring Boot**.
- Quản lý dependencies và build project bằng **Maven**.
- Tương tác cơ sở dữ liệu với **Spring Data JPA** / **Hibernate**.

## 3. Tiêu chuẩn API
- Tuân thủ thiết kế **RESTful API**.
- Đặt tên endpoint rõ ràng, sử dụng danh từ (ví dụ: `/api/v1/users`).
- Response trả về chuẩn hóa với cấu trúc thống nhất (khuyến khích có wrapper class bao gồm các trường như `code`, `message`, `data`).

## 4. Kiểm thử (Testing)
- Viết unit test cho tầng Service bằng **JUnit 5** và **Mockito**.
- Khi hoàn thành một tính năng hoặc thay đổi quan trọng, khuyến khích chạy lệnh test (như `mvn test`) để đảm bảo code không phá vỡ logic cũ.
