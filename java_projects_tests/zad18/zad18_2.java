import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class zad18_1 {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("hh2.txt");
            writer.write("Hello java 2\n not not not\n");
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("hh2.txt"));
            String string = reader.readLine();
            while (string != null) {
                System.out.println(string);
                string = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading");
        }
    }
}
