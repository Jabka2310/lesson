import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class zad18_0_2 {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("Numbers.txt");
            for (int i = 1; i <= 5; i++) {
                writer.write(i + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error 404");
        }
    }
}
