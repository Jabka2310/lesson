import java.util.Locale;
import java.util.Scanner;

public class zad26_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        String pause = "1";
        while (!pause.trim().toLowerCase().equals("стоп")) {
            pause = scanner.nextLine();
        }
    }
}
