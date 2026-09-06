# MUSIC LIBRARY MANAGEMENT SYSTEM
(Ứng dụng quản lý thư viện nhạc dạng console, xây dựng bằng Java và lưu trữ dữ liệu bằng file text.)

## Công nghệ sử dụng
- Java
- Lưu trữ dữ liệu bằng file `.txt` 

## Cấu trúc thư mục
```
src/main/java/
├── model/          # Các lớp dữ liệu: Song, Playlist, Action, RepeatMode
├── service/        # Xử lý logic nghiệp vụ: SongService, PlaylistService
├── controller/     # Điều phối luồng: SongController, PlaylistController
├── view/           # Hiển thị menu ra console
├── structure/      # Cấu trúc dữ liệu tự viết: MyStack, MyQueue
├── utils/          # Tiện ích dùng chung: InputValidate
data/               # Nơi lưu file dữ liệu (songs.txt, playlists.txt)
```
## Tính năng đã hoàn thành

### Cơ bản (Basic)
- CRUD bài hát, playlist
- Tìm kiếm bài hát theo tên, nghệ sĩ, album, genre
- Sắp xếp bài hát theo tên, nghệ sĩ, thời lượng, độ phổ biến
- Thêm/xóa bài hát khỏi playlist
- Đánh dấu/bỏ đánh dấu bài hát yêu thích
- Xem chi tiết playlist

### Trung bình (Medium)
- Lịch sử nghe nhạc gần đây
- Phát ngẫu nhiên playlist (Shuffle)
- Chế độ lặp lại (Repeat: Off / Repeat One / Repeat All)
- Thống kê thời lượng playlist, bài hát nghe nhiều nhất
- Tìm kiếm bài hát trong playlist

### Nâng cao (Hard)
- Undo/Redo thao tác thêm/xóa bài hát trong playlist (dùng Stack tự viết)
- Giới hạn kích thước lịch sử nghe nhạc (dùng Queue tự viết)
- Tạo playlist tự động theo điều kiện lọc (genre, nghệ sĩ, thời lượng)
- Xếp hạng bài hát theo nhiều tiêu chí (lượt nghe, yêu thích)

## Kiến trúc & Cấu trúc dữ liệu
- Tuân thủ mô hình MVC, tách biệt rõ Model - View - Controller - Service
- Tự cài đặt Stack (`MyStack`) và Queue (`MyQueue`) bằng Linked Node, không dùng thư viện có sẵn của Java
- Thuật toán sắp xếp Bubble Sort tự viết cho các chức năng sort
- Validate input và xử lý exception cho toàn bộ luồng nhập liệu

## Cách chạy chương trình
1. Mở project bằng NetBeans
2. Build project
3. Chạy file `MusicManageSys.java`
4. Làm theo menu hiển thị trên console

## Dữ liệu
Dữ liệu được lưu tự động vào thư mục `data/` khi thoát chương trình (chọn `0` ở menu chính), và được nạp lại khi mở chương trình lần sau.
