import java.util.ArrayList;

public class zad16_4 {
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Война и мир", "Толстой", 1869));
        books.add(new Book("Преступление и наказание", "Достоевский", 1866));
        books.add(new Book("Мастер и Маргарита", "Булгаков", 1967));
        books.add(new Book("1984", "Оруэлл", 1949));
        books.add(new Book("Гарри Поттер и философский камень", "Роулинг", 1997));
        for (Book book : books) {
            book.information();
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
    public void information() {
        System.out.println(name + " - " + autor + " (" + year + ")");
    }
}
