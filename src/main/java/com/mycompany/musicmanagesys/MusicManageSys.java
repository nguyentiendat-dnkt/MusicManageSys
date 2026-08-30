package com.mycompany.musicmanagesys;

import controller.SongController;
import controller.PlaylistController;
import service.SongService;
import java.util.Scanner;

public class MusicManageSys {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SongService songService = new SongService();
        songService.loadFromFile();

        SongController songController = new SongController(songService, scanner);
        PlaylistController playlistController = new PlaylistController(songService, scanner);
        playlistController.loadPlaylists(); // cần thêm hàm này ở PlaylistController

        int choice;
        do {
            System.out.println("\n========= MENU CHINH =========");
            System.out.println("1. Quan ly bai hat");
            System.out.println("2. Quan ly playlist");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("Chon: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    songController.run();
                    break;
                case 2:
                    playlistController.run();
                    break;
                case 0:
                    songService.saveToFile();
                    playlistController.savePlaylists(); // cần thêm hàm này
                    System.out.println("Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }
}