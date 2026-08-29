package controller;

import model.Song;
import service.SongService;
import view.SongView;
import java.util.List;
import java.util.Scanner;

public class SongController {
    private SongService songService;
    private SongView songView;
    private Scanner scanner;

    public SongController(SongService songService, Scanner scanner) {
        this.songService = songService;
        this.songView = new SongView();
        this.scanner = scanner;
    }

    public void run() {
        int choice;
        do {
            songView.showMenu();
            choice = Integer.parseInt(scanner.nextLine()); // đọc lựa chọn từ bàn phím

            switch (choice) {
                case 1:
                    addSongFromInput();
                    break;
                case 2:
                    songService.printAllSongs();
                    break;
                case 3:
                    System.out.print("Nhap id bai hat can xoa: ");
                    String delId = scanner.nextLine();
                    songService.deleteSong(delId);
                    break;
                case 4:
                    System.out.print("Nhap tu khoa ten bai hat: ");
                    String titleKeyword = scanner.nextLine();
                    songService.printSongList(songService.searchByTitle(titleKeyword));
                    break;
                case 5:
                    System.out.print("Nhap tu khoa nghe si: ");
                    String artistKeyword = scanner.nextLine();
                    songService.printSongList(songService.searchByArtist(artistKeyword));
                    break;
                case 6:
                    System.out.print("Nhap tu khoa album: ");
                    String albumKeyword = scanner.nextLine();
                    songService.printSongList(songService.searchByAlbum(albumKeyword));
                    break;
                case 7:
                    System.out.print("Nhap tu khoa genre: ");
                    String genreKeyword = scanner.nextLine();
                    songService.printSongList(songService.searchByGenre(genreKeyword));
                    break;
                case 8:
                    songService.sortByTitle();
                    songService.printAllSongs();
                    break;
                case 9:
                    songService.sortByArtist();
                    songService.printAllSongs();
                    break;
                case 10:
                    songService.sortByDuration();
                    songService.printAllSongs();
                    break;
                case 11:
                    songService.sortByPopularity();
                    songService.printAllSongs();
                    break;
                case 12:
                    toggleFavorite();
                    break;
                case 13:
                    System.out.print("Nhap id bai hat muon phat: ");
                    songService.playSong(scanner.nextLine());
                    break;
                case 14:
                    songService.printRecentlyPlayed();
                    break;
                case 15:
                    System.out.print("Nhap so luong top can xem: ");
                    int topN = Integer.parseInt(scanner.nextLine());
                    songService.printMostPlayed(topN);
                    break;
                case 16:
                    System.out.print("Nhap so luong top can xem: ");
                    int rankTopN = Integer.parseInt(scanner.nextLine());
                    songService.printRanking(rankTopN);
                    break;
                case 0:
                    System.out.println("Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, thu lai.");
            }
        } while (choice != 0);
    }

    private void addSongFromInput() {
        System.out.print("Nhap id: ");
        String id = scanner.nextLine();
        System.out.print("Nhap ten bai hat: ");
        String title = scanner.nextLine();
        System.out.print("Nhap nghe si: ");
        String artist = scanner.nextLine();
        System.out.print("Nhap album: ");
        String album = scanner.nextLine();
        System.out.print("Nhap genre: ");
        String genre = scanner.nextLine();
        System.out.print("Nhap thoi luong (giay): ");
        int duration = Integer.parseInt(scanner.nextLine());

        songService.addSong(new Song(id, title, artist, album, genre, duration));
    }
    
    private void toggleFavorite(){
        System.out.print("Nhap id bai hat: ");
        String id = scanner.nextLine();
        Song song = songService.findSongById(id);
        if (song == null){
            System.out.println("Khong tim thay bai hat!");
            return;
        }
        song.toggleFavorite();
        System.out.println("Bai hat " + song.getTitle() + " gio la favorite: " + song.isFavorite());
    }
}