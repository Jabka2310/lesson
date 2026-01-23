import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class zad18_0_0 {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("test18_0_0.txt");
            writer.write("Hello world!");
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }
}
