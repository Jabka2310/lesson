import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class zad18_1 {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("hh.txt"));
            String string = reader.readLine();
            while (string != null) {
                System.out.println(string);
                string = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
    }
}
