package service;
import model.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import structure.MyQueue;
import java.io.*;

public class SongService {
    private List<Song> songList;
    private MyQueue<Song> playHistory;
    private static final int MAX_HISTORY_SIZE = 5;
    
    public SongService(){
        songList = new ArrayList<>();
        playHistory = new MyQueue<>();
    }
    
    public void addSong(Song song){
        songList.add(song);
        System.out.println("Da them bai hat: "+ song.getTitle());
    }
    
    public List<Song> getAllSongs(){
        return songList;
    }
    
    public void printAllSongs(){
        if (songList.isEmpty()){
            System.out.println("Danh sach trong!");
            return;
        }
        for(Song s : songList){
            System.out.println(s);
        }
    }
    
    public boolean deleteSong(String id){
        for (Song s : songList){
            if (s.getId().equals(id)){
                songList.remove(s);
                System.out.println("Da xoa bai hat: "+ s.getTitle());
                return true;
            }
        }
        System.out.println("Khong tim thay bai hat co id: "+ id);
        return false;
    }
    
    public Song findSongById(String id){
        for (Song s : songList){
            if (s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }
    
    //Search theo ten bai
    public List<Song> searchByTitle(String keyword){
        List<Song> result = new ArrayList<>();
        for (Song s : songList){
            if(s.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                result.add(s);
            }
        }
        return result;
    }
    
    //Search theo ten tac gia
    public List<Song> searchByArtist(String keyword){
        List<Song> result = new ArrayList<>();
        for (Song s : songList){
            if(s.getArtist().toLowerCase().contains(keyword.toLowerCase())){
                result.add(s);
            }
        }
        return result;
    }
    
    //Search theo album
    public List<Song> searchByAlbum(String keyword){
        List<Song> result = new ArrayList<>();
        for (Song s : songList){
            if(s.getAlbum().toLowerCase().contains(keyword.toLowerCase())){
                result.add(s);
            }
        }
        return result;
    }
    
    //Search theo genre
    public List<Song> searchByGenre(String keyword){
        List<Song> result = new ArrayList<>();
        for (Song s : songList){
            if(s.getGenre().toLowerCase().contains(keyword.toLowerCase())){
                result.add(s);
            }
        }
        return result;
    }
    
    //Print ds ket qua
    public void printSongList(List<Song> list){
        if(list.isEmpty()){
            System.out.println("Khong co ket qua!");
            return;
        }
        for(Song s : list){
            System.out.println(s);
        }
    }
    
    //Sort theo ten(A-Z)
    public void sortByTitle(){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song s1, Song s2){
                return s1.getTitle().compareTo(s2.getTitle());
            }
        });
    }
    
    //Sort theo tac gia(A-Z)
    public void sortByArtist(){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song s1, Song s2){
                return s1.getArtist().compareTo(s2.getArtist());
            }
        });
    }
    
    //Sort theo duration(tang dan)
    public void sortByDuration(){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song s1, Song s2){
                return s1.getDuration() - s2.getDuration();
            }
        });
    }
    
    //Sort theo playcount
    public void sortByPopularity(){
        Collections.sort(songList, new Comparator<Song>(){
            @Override
            public int compare(Song s1, Song s2){
                return s1.getPlayCount() - s2.getPlayCount();
            }
        });
    }
    
        // Đường dẫn file cố định để lưu dữ liệu
    private static final String FILE_PATH = "data/songs.txt";

    // GHI dữ liệu ra file
    public void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // tự tạo thư mục "data" nếu chưa có

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Song s : songList) {
                // Ghi mỗi bài hát thành 1 dòng, các trường cách nhau bằng "|"
                String line = s.getId() + "|" + s.getTitle() + "|" + s.getArtist() + "|"
                        + s.getAlbum() + "|" + s.getGenre() + "|" + s.getDuration() + "|"
                        + s.getPlayCount() + "|" + s.isFavorite();
                writer.write(line);
                writer.newLine(); // xuống dòng cho bài tiếp theo
            }
            writer.close();
            System.out.println("Da luu du lieu bai hat vao file.");
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    // ĐỌC dữ liệu từ file
    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Chua co file du lieu, bat dau voi danh sach trong.");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            songList.clear(); // xóa list cũ trước khi nạp lại từ file

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // tách chuỗi theo dấu "|"
                if (parts.length < 8) continue; // dòng lỗi, bỏ qua

                Song s = new Song(parts[0], parts[1], parts[2], parts[3], parts[4],
                        Integer.parseInt(parts[5]));
                // Nạp lại playCount và favorite đã lưu trước đó
                for (int i = 0; i < Integer.parseInt(parts[6]); i++) {
                    s.increasePlayCount();
                }
                if (Boolean.parseBoolean(parts[7])) {
                    s.toggleFavorite();
                }
                songList.add(s);
            }
            reader.close();
            System.out.println("Da doc " + songList.size() + " bai hat tu file.");
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        }
    }
    
    //tim bai hat, moi lan nghe count++ và de dau ds
    public void playSong(String id) {
        Song song = findSongById(id);
        if (song == null) {
            System.out.println("Khong tim thay bai hat.");
            return;
        }
        song.increasePlayCount();

        playHistory.enqueue(song); // thêm bài mới vào cuối hàng đợi
        if (playHistory.size() > MAX_HISTORY_SIZE) {
            playHistory.dequeue(); // vượt giới hạn -> tự động loại bài cũ nhất (ở đầu hàng)
        }

        System.out.println("Dang phat: " + song.getTitle());
    }
    
    //in ds cac bai da nghe
    public void printRecentlyPlayed() {
        playHistory.printNewestFirst();
    }
    
    //bai hat nghe nhieu nhat
    public void printMostPlayed(int topN) {
        List<Song> sorted = new ArrayList<>(songList); // tạo bản sao, không đụng vào songList gốc

        Collections.sort(sorted, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                return s2.getPlayCount() - s1.getPlayCount(); // giảm dần, ai nghe nhiều hơn lên trước
            }
        });

        int count = Math.min(topN, sorted.size()); // tránh lỗi nếu list ít hơn topN
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
    }
    
    // Xep hang bai hat theo nhieu tieu chi: playCount va favorite
    public void printRanking(int topN) {
        List<Song> sorted = new ArrayList<>(songList);

        Collections.sort(sorted, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                double score1 = calculateScore(s1);
                double score2 = calculateScore(s2);
                return Double.compare(score2, score1); // giam dan: diem cao hon dung truoc
            }
        });

        int count = Math.min(topN, sorted.size());
        for (int i = 0; i < count; i++) {
            Song s = sorted.get(i);
            System.out.println((i + 1) + ". " + s + " | diem: " + calculateScore(s));
        }
    }

    // Cong thuc tinh diem: playCount la chinh, favorite duoc cong them diem thuong
    private double calculateScore(Song s) {
        double score = s.getPlayCount() * 1.0;
        if (s.isFavorite()) {
            score += 5; // bai favorite duoc cong them 5 diem thuong
        }
        return score;
    }
}

