import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class zad18_5 {
    public static void main(String[] args) {
        Book tolstoy = new Book("Война и мир", "Толстой", 1869);
        Book dostoyvsky = new Book("Преступление и наказание", "Достоевский", 1866);
        Book carenina = new Book("Анна Каренина", "Толстой", 1877);

        Book[] books = {tolstoy, dostoyvsky, carenina};

        try {
            FileWriter writer = new FileWriter("Books.txt");
            for (Book i : books) {
                writer.write(i.info() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
        }
    }
}

class Book {
    String name;
    String autor;
    int year;

    public Book(String name, String autor, int year) {
        this.name = name;
        this.autor = autor;
        this.year = year;
    }

    public String info() {
        return name + " " + autor + " " + year;
    }
}
