import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class zad18_4 {
    public static void main(String[] args) {
        try {
            File file = new File("sum.txt");
            Scanner scanner = new Scanner(file);
            int count = 0;

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    count += scanner.nextInt();
                } else {
                    scanner.next();
                }
            }
            scanner.close();
            System.out.println(count);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
