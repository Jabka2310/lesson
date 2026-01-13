public class Main {
    public static void main(String[] args) {
        Human Nikita = new Human();
        Nikita.name = "Nikita";
        Nikita.age = 23;
        Nikita.say();
    }
}

public class Human {
    String name;
    int age;
    public void say() {
        System.out.println("Меня зовут " + name + " I'm " + age + " old");
    }
}
