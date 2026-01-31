import java.util.Locale;
import java.util.Scanner;

public class zad22_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        int x = scanner.nextInt();
        int y = 10;
        System.out.println(x * y);
    }
}
