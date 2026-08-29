package controller;

import model.Playlist;
import model.Song;
import service.PlaylistService;
import service.SongService;
import view.PlaylistView;
import java.util.Scanner;
import model.RepeatMode;
import java.util.List;

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
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createPlaylist();
                    break;
                case 2:
                    playlistService.printAllPlaylists();
                    break;
                case 3:
                    System.out.print("Nhap id playlist can xoa: ");
                    playlistService.deletePlaylist(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap id playlist: ");
                    playlistService.viewPlaylistDetails(scanner.nextLine());
                    break;
                case 5:
                    addSongToPlaylist();
                    break;
                case 6:
                    removeSongFromPlaylist();
                    break;
                case 7:
                    System.out.print("Nhap id playlist: ");
                    playlistService.shufflePlay(scanner.nextLine(), songService, scanner);
                    break;
                case 8:
                    setRepeatModeFromInput();
                    break;
                case 9:
                    System.out.print("Nhap id playlist: ");
                    String pid = scanner.nextLine();
                    System.out.print("Nhap so buoc mo phong (VD: 5): ");
                    int steps = Integer.parseInt(scanner.nextLine());
                    playlistService.playWithRepeat(pid, steps, songService);
                    break;
                case 10:
                    System.out.print("Nhap id playlist: ");
                    String searchPid = scanner.nextLine();
                    System.out.print("Nhap id bai hat: ");
                    String keyword = scanner.nextLine();
                    playlistService.printAllPlaylists();
                    List<Song> found = playlistService.searchInPlaylist(searchPid, keyword);
                    if (found.isEmpty()) {
                        System.out.println("Khong tim thay.");
                    } else {
                        for (Song s : found) System.out.println(s);
                    }
                    break;
                case 11:
                    System.out.print("Nhap id playlist: ");
                    Playlist undoP = playlistService.findPlaylistById(scanner.nextLine());
                    if (undoP != null) undoP.undo();
                    else System.out.println("Khong tim thay playlist.");
                    break;
                case 12:
                    System.out.print("Nhap id playlist: ");
                    Playlist redoP = playlistService.findPlaylistById(scanner.nextLine());
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
        System.out.print("Nhap id playlist: ");
        String id = scanner.nextLine();
        System.out.print("Nhap ten playlist: ");
        String name = scanner.nextLine();
        playlistService.addPlaylist(new Playlist(id, name));
    }

    private void addSongToPlaylist() {
        System.out.print("Nhap id playlist: ");
        String playlistId = scanner.nextLine();
        Playlist playlist = playlistService.findPlaylistById(playlistId);
        if (playlist == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }

        System.out.print("Nhap id bai hat can them: ");
        String songId = scanner.nextLine();
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
        System.out.print("Nhap id playlist: ");
        String playlistId = scanner.nextLine();
        Playlist playlist = playlistService.findPlaylistById(playlistId);
        if (playlist == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }

        System.out.print("Nhap id bai hat can xoa: ");
        String songId = scanner.nextLine();
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
        System.out.print("Nhap id playlist: ");
        String id = scanner.nextLine();
        Playlist p = playlistService.findPlaylistById(id);
        if (p == null) {
            System.out.println("Khong tim thay playlist.");
            return;
        }
        System.out.println("Chon che do: 1-OFF, 2-REPEAT_ONE, 3-REPEAT_ALL");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) p.setRepeatMode(RepeatMode.OFF);
        else if (choice == 2) p.setRepeatMode(RepeatMode.REPEAT_ONE);
        else if (choice == 3) p.setRepeatMode(RepeatMode.REPEAT_ALL);
        else {
            System.out.println("Lua chon khong hop le.");
            return;
        }
        System.out.println("Da dat che do: " + p.getRepeatMode());
    }
    
    private void generatePlaylistFromInput() {
        System.out.print("Nhap id playlist moi: ");
        String id = scanner.nextLine();
        System.out.print("Nhap ten playlist moi: ");
        String name = scanner.nextLine();
        System.out.print("Loc theo genre (de trong neu khong loc): ");
        String genre = scanner.nextLine();
        System.out.print("Loc theo nghe si (de trong neu khong loc): ");
        String artist = scanner.nextLine();
        System.out.print("Loc theo duration toi da, giay (nhap 0 neu khong loc): ");
        int maxDuration = Integer.parseInt(scanner.nextLine());

        playlistService.generatePlaylistByRule(id, name, songService, genre, artist, maxDuration);
    }
}