package view;

public class PlaylistView {
    public void showMenu() {
        System.out.println("\n===== QUAN LY PLAYLIST =====");
        System.out.println("1. Tao playlist moi");
        System.out.println("2. Xem tat ca playlist");
        System.out.println("3. Xoa playlist");
        System.out.println("4. Xem chi tiet playlist");
        System.out.println("5. Them bai hat vao playlist");
        System.out.println("6. Xoa bai hat khoi playlist");
        System.out.println("7. Phat ngau nhien");
        System.out.println("8. Dat che do lap");
        System.out.println("9. Phat co lap");
        System.out.println("10. Tim bai hat trong playlist");
        System.out.println("11. Undo");
        System.out.println("12. Redo");
        System.out.println("13. Tao playlist tu dieu kien loc");
        System.out.println("0. Quay lai menu chinh");
        System.out.print("Chon chuc nang: ");
    }
    
    public void showRepeatModeMenu() {
        System.out.println("\n===== CHON CHE DO LAP =====");
        System.out.println("1. Khong lap");
        System.out.println("2. Lap 1 bai");
        System.out.println("3. Lap ca playlist");
    }
}