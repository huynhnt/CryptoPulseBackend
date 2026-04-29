# Quy tắc quy trình làm việc (Workflow Rules)

Dự án này tuân thủ nghiêm ngặt quy trình **OpenSpec** để đảm bảo mọi thay đổi mã nguồn đều có kế hoạch và tài liệu đi kèm.

## 1. Chu trình thực hiện một tính năng/thay đổi
Mọi thay đổi (không bao gồm bug fix cực nhỏ) PHẢI đi qua 3 giai đoạn:

1. **Giai đoạn Đề xuất (Propose)**:
   - Sử dụng lệnh `/opsx-propose` hoặc `/opsx-explore`.
   - Kết quả mong đợi: Tạo được hồ sơ thay đổi trong `openspec/changes/` gồm `design.md`, `specs/` và `tasks.md`.
   - **Cấm**: Tuyệt đối không sửa code dự án trong giai đoạn này.

## 2. Các lệnh quy ước trong dự án
- **/opsx-propose**: Khởi tạo một tính năng mới (tạo hồ sơ mới).
- **/opsx-update**: Cập nhật/Sửa đổi yêu cầu cho tính năng hiện tại (sửa Spec/Tasks cũ).
- **/opsx-apply**: Bắt đầu/Tiếp tục viết code dựa trên Spec đã thống nhất.
- **/opsx-archive**: Hoàn tất và lưu trữ tính năng.

## 3. Phân biệt Chat và Chỉnh sửa
- **Chat thông thường**: Trao đổi kiến thức, giải thích code, hỏi đáp (Không thay đổi tài liệu).
- **Yêu cầu chỉnh sửa**: PHẢI bắt đầu bằng lệnh **/opsx-update** để AI biết cần cập nhật lại hồ sơ thiết kế trước khi thực hiện code.

## 4. Ưu tiên tài liệu
- Luôn cập nhật Spec/Design thông qua lệnh `/opsx-update` trước khi cập nhật mã nguồn khi có yêu cầu thay đổi logic lớn.

## 5. Quản lý phiên bản (Git Commit)
- Sau mỗi đợt `/opsx-apply` hoàn thành một tính năng hoặc một mốc quan trọng, AI Agent có trách nhiệm **nhắc nhở** người dùng thực hiện commit để lưu lại lịch sử.
- AI Agent nên gợi ý nội dung commit message (Tiếng Việt hoặc Tiếng Anh tùy ngữ cảnh) để người dùng dễ dàng thao tác.
