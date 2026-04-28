---
description: Cập nhật tài liệu thiết kế và đặc tả cho tính năng đang thực hiện.
---

Cập nhật tài liệu thiết kế và đặc tả (OpenSpec) khi có thay đổi yêu cầu.

**Input**: Mô tả yêu cầu thay đổi (Ví dụ: `/opsx-update thêm cache cho endpoint coins/markets`).

**Steps**

1. **Xác định tính năng hiện tại**
   - Đọc ngữ cảnh hội thoại để xác định thay đổi (change) nào đang được thực hiện.
   - Nếu không rõ, yêu cầu người dùng xác nhận.

2. **Cập nhật tài liệu thiết kế (Design)**
   - Đọc file `design.md` trong thư mục `openspec/changes/<name>/`.
   - Cập nhật các mục thiết kế để phù hợp với yêu cầu mới.

3. **Cập nhật Đặc tả kỹ thuật (Specs)**
   - Đọc và sửa các file trong thư mục `specs/` của tính năng đó.
   - Đảm bảo các kịch bản (scenarios) phản ánh đúng thay đổi.

4. **Cập nhật danh sách nhiệm vụ (Tasks)**
   - Đọc file `tasks.md`.
   - Thêm các nhiệm vụ mới vào cuối danh sách hoặc sửa các task hiện có.
   - Các task mới PHẢI ở trạng thái chưa hoàn thành `[ ]`.

5. **Báo cáo kết quả**
   - Liệt kê các file đã được cập nhật.
   - Nhắc nhở người dùng sử dụng lệnh `/opsx-apply` để bắt đầu áp dụng thay đổi vào code.

**Guardrails**
- TUYỆT ĐỐI không sửa code dự án trong workflow này. Chỉ sửa tài liệu trong thư mục `openspec/`.
- Luôn sử dụng tiếng Việt cho nội dung tài liệu.
