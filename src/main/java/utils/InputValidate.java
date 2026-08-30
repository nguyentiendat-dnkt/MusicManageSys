package utils;
import java.util.Scanner;
public class InputValidate{

    // Doc 1 so nguyen hop le, hoi lai neu nhap sai dinh dang
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Loi: vui long nhap mot so nguyen hop le!");
            }
        }
    }

    // Doc 1 so nguyen trong khoang [min, max], hoi lai neu ngoai khoang hoac sai dinh dang
    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value < min || value > max) {
                System.out.println("Loi: vui long nhap so trong khoang " + min + " - " + max + "!");
            } else {
                return value;
            }
        }
    }

    // Doc 1 chuoi khong duoc de trong, hoi lai neu de trong
    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); // trim() de bo khoang trang thua 2 dau
            if (input.isEmpty()) {
                System.out.println("Loi: du lieu khong duoc de trong!");
            } else {
                return input;
            }
        }
    }
}