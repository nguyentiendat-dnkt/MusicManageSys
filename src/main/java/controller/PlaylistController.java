package controller;

import model.Playlist;
import model.Song;
import service.PlaylistService;
import service.SongService;
import view.PlaylistView;
import java.util.Scanner;
import model.RepeatMode;
import java.util.List;
import utils.InputValidate;

public class PlaylistController {
    private PlaylistService playlistService;
    private SongService songService; // dùng chung để tìm Song có sẵn
    private PlaylistView playlistView;
    private Scanner scanner;

    // Nhận songService từ bên ngoài truyền vào, thay vì tự tạo mới
    public PlaylistController(SongService songService, Scanner scanner) {
        this.playlistService = new PlaylistService();
        this.songService = songService;
        this.playlistView = new PlaylistView();
        this.scanner = scanner;
    }

    public void run() {
        int choice;
        do {
            playlistView.showMenu();
            choice = InputValidate.readInt(scanner, "");

            switch (choice) {
                case 1:
                    createPlaylist();
                    break;
                case 2:
                    playlistService.printAllPlaylists();
                    break;
                case 3:
                    String delId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist can xoa: ");
                    playlistService.deletePlaylist(delId);
                    break;
                case 4:
                    String viewId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    playlistService.viewPlaylistDetails(viewId);
                    break;
                case 5:
                    addSongToPlaylist();
                    break;
                case 6:
                    removeSongFromPlaylist();
                    break;
                case 7:
                    String shuffleId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    playlistService.shufflePlay(shuffleId, songService, scanner);
                    break;
                case 8:
                    setRepeatModeFromInput();
                    break;
                case 9:
                    String pid = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    int steps = InputValidate.readIntInRange(scanner, "Nhap so buoc mo phong (VD: 5): ", 1, 1000);
                    playlistService.playWithRepeat(pid, steps, songService);
                    break;
                case 10:
                    String searchPid = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    String keyword = InputValidate.readNonEmptyString(scanner, "Nhap tu khoa bai hat: ");
                    List<Song> found = playlistService.searchInPlaylist(searchPid, keyword);
                    if (found.isEmpty()) {
                        System.out.println("Khong tim thay.");
                    } else {
                        for (Song s : found) System.out.println(s);
                    }
                    break;
                case 11:
                    String undoId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    Playlist undoP = playlistService.findPlaylistById(undoId);
                    if (undoP != null) undoP.undo();
                    else System.out.println("Khong tim thay playlist.");
                    break;
                case 12:
                    String redoId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
                    Playlist redoP = playlistService.findPlaylistById(redoId);
                    if (redoP != null) redoP.redo();
                    else System.out.println("Khong tim thay playlist.");
                    break;
                case 13:
                    generatePlaylistFromInput();
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }

    private void createPlaylist() {
        String id = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
        String name = InputValidate.readNonEmptyString(scanner, "Nhap ten playlist: ");
        playlistService.addPlaylist(new Playlist(id, name));
    }

    private void addSongToPlaylist() {
        String playlistId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
        Playlist playlist = playlistService.findPlaylistById(playlistId);
        if (playlist == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }

        String songId = InputValidate.readNonEmptyString(scanner, "Nhap id bai hat can them: ");
        Song song = songService.findSongById(songId);
        if (song == null) {
            System.out.println("Khong tim thay bai hat.");
            return;
        }

        if (playlist.addSong(song)) {
            System.out.println("Da them bai hat vao playlist.");
        }
    }

    private void removeSongFromPlaylist() {
        String playlistId = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
        Playlist playlist = playlistService.findPlaylistById(playlistId);
        if (playlist == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }

        String songId = InputValidate.readNonEmptyString(scanner, "Nhap id bai hat can xoa: ");
        if (playlist.removeSong(songId)) {
            System.out.println("Da xoa bai hat khoi playlist.");
        } else {
            System.out.println("Khong tim thay bai hat trong playlist.");
        }
    }
    
    public void loadPlaylists() {
        playlistService.loadFromFile(songService);
    }

    public void savePlaylists() {
        playlistService.saveToFile();
    }
    
    private void setRepeatModeFromInput() {
        String id = InputValidate.readNonEmptyString(scanner, "Nhap id playlist: ");
        Playlist p = playlistService.findPlaylistById(id);
        if (p == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }
        System.out.println("Chon che do: 1-OFF, 2-REPEAT_ONE, 3-REPEAT_ALL");
        int choice = InputValidate.readIntInRange(scanner, "", 1, 3);
        if (choice == 1) p.setRepeatMode(RepeatMode.OFF);
        else if (choice == 2) p.setRepeatMode(RepeatMode.REPEAT_ONE);
        else p.setRepeatMode(RepeatMode.REPEAT_ALL);
        System.out.println("Da dat che do: " + p.getRepeatMode());
    }
    
    private void generatePlaylistFromInput() {
        String id = InputValidate.readNonEmptyString(scanner, "Nhap id playlist moi: ");
        String name = InputValidate.readNonEmptyString(scanner, "Nhap ten playlist moi: ");
        System.out.print("Loc theo genre (de trong neu khong loc): ");
        String genre = scanner.nextLine();
        System.out.print("Loc theo nghe si (de trong neu khong loc): ");
        String artist = scanner.nextLine();
        int maxDuration = InputValidate.readIntInRange(scanner, "Loc theo duration toi da, giay (nhap 0 neu khong loc): ", 0, 10000);

        playlistService.generatePlaylistByRule(id, name, songService, genre, artist, maxDuration);
    }
}