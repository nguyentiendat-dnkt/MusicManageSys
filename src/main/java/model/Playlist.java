package model;

import java.util.ArrayList;
import java.util.List;
import structure.MyStack;

public class Playlist {
    private String id;
    private String name;
    private List<Song> songs; // Playlist chứa 1 danh sách Song bên trong
    private MyStack<Action> undoStack;
    private MyStack<Action> redoStack;

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>(); // playlist mới tạo thì rỗng
        this.undoStack = new MyStack<>(); 
        this.redoStack = new MyStack<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Song> getSongs() { return songs; }

    // Thêm 1 bài hát vào playlist
    public boolean addSong(Song song) {
        if (songs.contains(song)) {
            System.out.println("Bai hat da co trong playlist!");
            return false;
        }
        songs.add(song);
        undoStack.push(new Action(Action.ADD, song)); // ghi lại: vừa THEM bài này
        redoStack.clear(); // có hành động mới -> xóa sạch redo cũ
        return true;
    }

    // Xóa 1 bài hát khỏi playlist theo id
    public boolean removeSong(String songId) {
        for (Song s : songs) {
            if (s.getId().equals(songId)) {
                songs.remove(s);
                undoStack.push(new Action(Action.REMOVE, s)); // ghi lại: vừa XOA bài này
                redoStack.clear();
                return true;
            }
        }
        return false;
    }

    // Tính tổng thời lượng playlist (giây)
    public int getTotalDuration() {
        int total = 0;
        for (Song s : songs) {
            total += s.getDuration();
        }
        return total;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + songs.size() + " bai hat | tong thoi luong: " + getTotalDuration() + "s";
    }
    
    //Repeat mode
    private RepeatMode repeatMode = RepeatMode.OFF; 
    public RepeatMode getRepeatMode(){ 
        return repeatMode; 
    }
    public void setRepeatMode(RepeatMode repeatMode){ 
        this.repeatMode = repeatMode; 
    }
    
    //UNDO
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Khong co gi de undo.");
            return;
        }
        Action lastAction = undoStack.pop(); // lấy hành động gần nhất ra

        if (lastAction.getType().equals(Action.ADD)) {
            // hành động gốc là THEM -> undo nghĩa là XOA bài đó đi
            songs.remove(lastAction.getSong());
        } else {
            // hành động gốc là XOA -> undo nghĩa là THEM lại bài đó
            songs.add(lastAction.getSong());
        }

        redoStack.push(lastAction); // lưu lại để có thể redo sau này
        System.out.println("Da undo: " + lastAction.getType() + " " + lastAction.getSong().getTitle());
    }
    
    //REDO
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Khong co gi de redo.");
            return;
        }
        Action lastUndone = redoStack.pop(); // lấy hành động vừa undo ra

        if (lastUndone.getType().equals(Action.ADD)) {
            // hành động gốc là THEM -> redo nghĩa là THEM lại
            songs.add(lastUndone.getSong());
        } else {
            // hành động gốc là XOA -> redo nghĩa là XOA lại
            songs.remove(lastUndone.getSong());
        }

        undoStack.push(lastUndone); // đưa trở lại undoStack, để có thể undo lại lần nữa nếu muốn
        System.out.println("Da redo: " + lastUndone.getType() + " " + lastUndone.getSong().getTitle());
    }
}