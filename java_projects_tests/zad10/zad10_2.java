public class Main {
    public static void main(String[] args) {
        Book war = new Book("War", "Dostoevsky", 1898);
        Book world = new Book("World", "Dostoevsky", 1900);
        Book and = new Book("And", "Ne Dostoevsky", 1743);

        war.information();
        System.out.println();
        world.information();
        System.out.println();
        and.information();
    }
}

public class Book {
    String name;
    String autor;
    int year;

    public Book(String name, String autor, int year) {
        this.name = name;
        this.autor = autor;
        this.year = year;
    }

    public void information() {
        System.out.println(name);
        System.out.println(autor);
        System.out.println(year);
    }
}
