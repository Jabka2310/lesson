public class zad11_2 {
    public static void main(String[] args) {
        Book[] book = new Book[10];

        book[0] = new Book("Война и мир", "Толстой", 1869);
        book[1] = new Book("Преступление и наказание", "Достоевский", 1866);
        book[2] = new Book("Мастер и Маргарита", "Булгаков", 1967);
        book[3] = new Book("1984", "Оруэлл", 1949);
        book[4] = new Book("Гарри Поттер и философский камень", "Роулинг", 1997);
        book[5] = new Book("Властелин колец", "Толкин", 1954);
        book[6] = new Book("Анна Каренина", "Толстой", 1877);
        book[7] = new Book("Идиот", "Достоевский", 1869);
        book[8] = new Book("Собачье сердце", "Булгаков", 1925);
        book[9] = new Book("Отцы и дети", "Тургенев", 1862);

        Book young = book[0];

        for (int i = 0; i < book.length; i++) {
            if (book[i].year > young.year) {
                young = book[i];
            }
        }
        young.say();
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

    public void say() {
        System.out.println(name);
        System.out.println(autor);
        System.out.println(year);
    }
}
