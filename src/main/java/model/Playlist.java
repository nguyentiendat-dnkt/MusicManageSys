package model;

import java.util.ArrayList;
import java.util.List;
import structure.MyStack;

public class Playlist {
    private String id;
    private String name;
    private List<Song> songs; // Playlist chua ds song
    private MyStack<Action> undoStack;
    private MyStack<Action> redoStack;

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>();
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
        undoStack.push(new Action(Action.ADD, song)); // ghi lai hanh dong add
        redoStack.clear(); 
        return true;
    }

    // Xoa bai hat
    public boolean removeSong(String songId) {
        for (Song s : songs) {
            if (s.getId().equals(songId)) {
                songs.remove(s);
                undoStack.push(new Action(Action.REMOVE, s)); // ghi lai hanh dong remove
                redoStack.clear();
                return true;
            }
        }
        return false;
    }

    // tinh tong thoi luong
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
        Action lastAction = undoStack.pop(); // lay hanh dong gan nhat ra

        if (lastAction.getType().equals(Action.ADD)) {
            // hanh dong la add thi undo la remove
            songs.remove(lastAction.getSong());
        } else {
            songs.add(lastAction.getSong());
        }

        redoStack.push(lastAction); // luu lai de redo
        System.out.println("Da undo: " + lastAction.getType() + " " + lastAction.getSong().getTitle());
    }
    
    //REDO
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Khong co gi de redo.");
            return;
        }
        Action lastUndone = redoStack.pop(); // lay hanh dong undo ra

        if (lastUndone.getType().equals(Action.ADD)) {
            songs.add(lastUndone.getSong());
        } else {
            songs.remove(lastUndone.getSong());
        }

        undoStack.push(lastUndone); // dua ve lai undo de co the undo tiep
        System.out.println("Da redo: " + lastUndone.getType() + " " + lastUndone.getSong().getTitle());
    }
}