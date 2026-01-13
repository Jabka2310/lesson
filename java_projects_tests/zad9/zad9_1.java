public class Main {
    public static void main(String[] args) {
        Human Nikita = new Human();
        Nikita.name = "Nikita";
        Nikita.age = 23;
        System.out.println("My name is " + Nikita.name + " IM " + Nikita.age + "old");
    }
}

public class Human {
    String name;
    int age;
}
