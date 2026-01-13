public class Main {
    public static void main(String[] args) {
        Book metro2033 = new Book();
        Book metro2034 = new Book();
        Book metro2035 = new Book();

        metro2033.name = "Metro 2033";
        metro2034.name = "Metro 2034";
        metro2035.name = "Metro 2035";

        metro2033.autor = "Gluhovsky";
        metro2034.autor = "Gluhovsky";
        metro2035.autor = "Gluhovsky";

        metro2033.date = "12-09-2009";
        metro2034.date = "12-09-2019";
        metro2035.date = "12-09-2023";

        System.out.println(metro2033.name + " " + metro2033.autor + " " + metro2033.date);
    }
}

public class Book {
    String name;
    String autor;
    String date;
}
