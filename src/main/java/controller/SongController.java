package controller;

import model.Song;
import service.SongService;
import view.SongView;
import java.util.List;
import java.util.Scanner;
import utils.InputValidate;

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
            choice = InputValidate.readInt(scanner, ""); // đọc lựa chọn từ bàn phím

            switch (choice) {
                case 1:
                    addSongFromInput();
                    break;
                case 2:
                    songService.printAllSongs();
                    break;
                case 3:
                    String delId = InputValidate.readNonEmptyString(scanner, "Nhap id bai hat can xoa: ");
                    songService.deleteSong(delId);
                    break;
                case 4:
                    String titleKeyword = InputValidate.readNonEmptyString(scanner, "Nhap tu khoa ten bai hat: ");
                    songService.printSongList(songService.searchByTitle(titleKeyword));
                    break;
                case 5:
                    String artistKeyword = InputValidate.readNonEmptyString(scanner, "Nhap tu khoa nghe si: ");
                    songService.printSongList(songService.searchByArtist(artistKeyword));
                    break;
                case 6:
                    String albumKeyword = InputValidate.readNonEmptyString(scanner, "Nhap tu khoa album: ");
                    songService.printSongList(songService.searchByAlbum(albumKeyword));
                    break;
                case 7:
                    String genreKeyword = InputValidate.readNonEmptyString(scanner, "Nhap tu khoa genre: ");
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
                    String playId = InputValidate.readNonEmptyString(scanner, "Nhap id bai hat muon phat: ");
                    songService.playSong(playId);
                    break;
                case 14:
                    songService.printRecentlyPlayed();
                    break;
                case 15:
                    int topN = InputValidate.readIntInRange(scanner, "Nhap so luong top can xem: ", 1, 100);
                    songService.printMostPlayed(topN);
                    break;
                case 16:
                    int rankTopN = InputValidate.readIntInRange(scanner, "Nhap so luong top can xem: ", 1, 100);
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
        String id = InputValidate.readNonEmptyString(scanner, "Nhap id: ");
        String title = InputValidate.readNonEmptyString(scanner, "Nhap ten bai hat: ");
        String artist = InputValidate.readNonEmptyString(scanner, "Nhap nghe si: ");
        String album = InputValidate.readNonEmptyString(scanner, "Nhap album: ");
        String genre = InputValidate.readNonEmptyString(scanner, "Nhap genre: ");
        int duration = InputValidate.readIntInRange(scanner, "Nhap thoi luong (giay): ", 1, 3600);

        songService.addSong(new Song(id, title, artist, album, genre, duration));
    }
    
    private void toggleFavorite(){
        String id = InputValidate.readNonEmptyString(scanner, "Nhap id bai hat: ");
        Song song = songService.findSongById(id);
        if (song == null){
            System.out.println("Khong tim thay bai hat!");
            return;
        }
        song.toggleFavorite();
        System.out.println("Bai hat " + song.getTitle() + " gio la favorite: " + song.isFavorite());
    }
}