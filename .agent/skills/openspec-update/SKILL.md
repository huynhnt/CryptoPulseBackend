---
name: openspec-update
description: Cập nhật tài liệu thiết kế và danh sách nhiệm vụ khi có yêu cầu thay đổi yêu cầu trong quá trình thực hiện.
---

# Skill: OpenSpec Update

Skill này quy định cách Agent xử lý lệnh `/opsx-update` trong dự án.

## Hướng dẫn cho Agent:
1. Khi nhận lệnh `/opsx-update`, KHÔNG được sửa code ngay.
2. Mở các file trong `openspec/changes/[feature-name]/`.
3. Cập nhật `design.md` và `specs/` để phản ánh yêu cầu mới.
4. Bổ sung các task cần thiết vào `tasks.md` (đánh dấu là `[ ]`).
5. Báo cáo lại cho người dùng và chờ lệnh `/opsx-apply`.
