# Skill: OpenSpec Update (opsx-update)

Skill này hướng dẫn AI Agent cách xử lý yêu cầu cập nhật đặc tả tính năng trong dự án CryptoPulseBackend.

## Cách thức hoạt động
Khi người dùng sử dụng lệnh `/opsx-update`, Agent phải thực hiện các bước sau:

1. **Phân tích yêu cầu**: Hiểu rõ phần nào trong Design hoặc Spec cần thay đổi.
2. **Cập nhật tài liệu**:
   - Sửa file `design.md` trong `openspec/changes/...` tương ứng.
   - Sửa các file trong `specs/` nếu cần.
   - **Quan trọng**: Thêm các task mới vào `tasks.md` và để ở trạng thái chưa hoàn thành `[ ]`.
3. **Xác nhận**: Thông báo cho người dùng các file đã cập nhật và chờ lệnh `/opsx-apply`.

## Nguyên tắc
- TUYỆT ĐỐI không sửa code ngay lập tức.
- Luôn đảm bảo tài liệu phản ánh đúng ý định mới của người dùng.
