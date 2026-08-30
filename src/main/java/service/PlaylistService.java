package service;

import model.Playlist;
import model.Song;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import model.RepeatMode;
import java.util.Collections;
import java.util.Scanner;

public class PlaylistService {
    private List<Playlist> playlistList;

    public PlaylistService() {
        playlistList = new ArrayList<>();
    }

    // CREATE
    public void addPlaylist(Playlist playlist) {
        playlistList.add(playlist);
        System.out.println("Da tao playlist: " + playlist.getName());
    }

    // READ
    public void printAllPlaylists() {
        if (playlistList.isEmpty()) {
            System.out.println("Chua co playlist nao.");
            return;
        }
        for (Playlist p : playlistList) {
            System.out.println(p);
        }
    }

    // Tìm playlist theo id
    public Playlist findPlaylistById(String id) {
        for (Playlist p : playlistList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // DELETE
    public boolean deletePlaylist(String id) {
        Playlist p = findPlaylistById(id);
        if (p != null) {
            playlistList.remove(p);
            System.out.println("Da xoa playlist: " + p.getName());
            return true;
        }
        System.out.println("Khong tim thay playlist co id: " + id);
        return false;
    }

    // Xem chi tiết playlist (danh sách bài hát bên trong)
    public void viewPlaylistDetails(String id) {
        Playlist p = findPlaylistById(id);
        if (p == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }
        System.out.println("=== " + p.getName() + " ===");
        if (p.getSongs().isEmpty()) {
            System.out.println("Playlist rong.");
        } else {
            for (Song s : p.getSongs()) {
                System.out.println(s);
            }
        }
        System.out.println("Tong thoi luong: " + p.getTotalDuration() + "s");
    }
    
        private static final String FILE_PATH = "data/playlists.txt";

    // GHI dữ liệu playlist ra file
    public void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Playlist p : playlistList) {
                StringBuilder songIds = new StringBuilder();
                for (Song s : p.getSongs()) {
                    if (songIds.length() > 0) songIds.append(",");
                    songIds.append(s.getId());
                }
                String line = p.getId() + "|" + p.getName() + "|" + songIds.toString();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            System.out.println("Da luu du lieu playlist vao file.");
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    // ĐỌC dữ liệu playlist từ file - cần songService để tìm lại Song theo id
    public void loadFromFile(SongService songService) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Chua co file playlist, bat dau voi danh sach trong.");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            playlistList.clear();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1); // -1 để giữ lại phần tử rỗng (playlist chưa có bài hát nào)
                if (parts.length < 3) continue;

                Playlist p = new Playlist(parts[0], parts[1]);

                if (!parts[2].isEmpty()) {
                    String[] songIds = parts[2].split(",");
                    for (String songId : songIds) {
                        Song s = songService.findSongById(songId);
                        if (s != null) {
                            p.addSong(s);
                        }
                    }
                }
                playlistList.add(p);
            }
            reader.close();
            System.out.println("Da doc " + playlistList.size() + " playlist tu file.");
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        }
    }
    
        // shuffle playlist và phat
    public void shufflePlay(String playlistId, SongService songService, Scanner scanner) {
        Playlist p = findPlaylistById(playlistId);
        if (p == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }
        if (p.getSongs().isEmpty()) {
            System.out.println("Playlist rong, khong co gi de phat.");
            return;
        }

        List<Song> shuffled = new ArrayList<>(p.getSongs());
        Collections.shuffle(shuffled);

        // Bước 1: in ra toàn bộ danh sách đã xáo trộn trước
        System.out.println("--- Danh sach phat ngau nhien: " + p.getName() + " ---");
        for (int i = 0; i < shuffled.size(); i++) {
            System.out.println((i + 1) + ". " + shuffled.get(i));
        }

        // Bước 2: phát lần lượt, hỏi tiếp tục sau mỗi bài
        for (int i = 0; i < shuffled.size(); i++) {
            Song current = shuffled.get(i);
            songService.playSong(current.getId());

            // Nếu đây là bài cuối cùng thì không cần hỏi tiếp nữa
            if (i == shuffled.size() - 1) {
                System.out.println("Da phat het playlist.");
                break;
            }

            System.out.print("Nghe bai tiep theo? (y/n): ");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("y")) {
                System.out.println("Dung phat.");
                break;
            }
        }
    }
    
    // Mô phỏng phát playlist theo chế độ lặp, "steps" là số lần bấm "next"
    public void playWithRepeat(String playlistId, int steps, int startIndex, SongService songService) {
        Playlist p = findPlaylistById(playlistId);
        if (p == null || p.getSongs().isEmpty()) {
            System.out.println("Playlist khong hop le hoac rong.");
            return;
        }

        List<Song> songs = p.getSongs();
        int index = startIndex; // chon diem bat dau

        for (int i = 0; i < steps; i++) {
            Song current = songs.get(index);
            songService.playSong(current.getId());

            if (p.getRepeatMode() == RepeatMode.REPEAT_ONE) {
            } else if (p.getRepeatMode() == RepeatMode.REPEAT_ALL) {
                index = (index + 1) % songs.size();
            } else {
                index++;
                if (index >= songs.size()) {
                    System.out.println("Da phat het playlist (khong lap lai).");
                    return;
                }
            }
        }
    }
    
    //search trong playlist
    public List<Song> searchInPlaylist(String playlistId, String keyword) {
        Playlist p = findPlaylistById(playlistId);
        List<Song> result = new ArrayList<>();
        if (p == null) return result;

        for (Song s : p.getSongs()) {
            if (s.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }
    
    // Tao playlist moi tu dieu kien loc - de trong "" hoac -1 neu khong loc theo tieu chi do
    public Playlist generatePlaylistByRule(String newPlaylistId, String newPlaylistName,
                                            SongService songService, String genre, String artist, int maxDuration) {
        Playlist newPlaylist = new Playlist(newPlaylistId, newPlaylistName);

        for (Song s : songService.getAllSongs()) {
            boolean match = true;

            if (!genre.isEmpty() && !s.getGenre().equalsIgnoreCase(genre)) {
                match = false;
            }
            if (!artist.isEmpty() && !s.getArtist().equalsIgnoreCase(artist)) {
                match = false;
            }
            if (maxDuration > 0 && s.getDuration() > maxDuration) {
                match = false;
            }

            if (match) {
                newPlaylist.addSong(s);
            }
        }

        playlistList.add(newPlaylist);
        System.out.println("Da tao playlist '" + newPlaylistName + "' voi " + newPlaylist.getSongs().size() + " bai hat.");
        return newPlaylist;
    }
}