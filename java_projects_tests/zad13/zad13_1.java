public class zad13_1 {
    public static void main(String[] args) {
        Dog Bobik = new Dog("Bobik");
        System.out.print("Собака Бобик говорит ");
        Bobik.sound();
    }
}

class Animal {
    String name;
    public Animal(String name) {
        this.name = name;
    }
    public void sound() {
        System.out.println("Not Gav");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    @Override
    public void sound() {
        System.out.println("Gav");
    }
}
