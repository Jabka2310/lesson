public class Main {
    public static void main(String[] args) {
        Human Oleg = new Human("Oleg", 24);
        Oleg.information();
    }
}

public class Human {
    String name;
    int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void information() {
        System.out.println(name + "\n" + age);
    }
}
