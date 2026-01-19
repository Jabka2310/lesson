public class zad15_2 {
    public static void main(String[] args) {
        Dog antony = new Dog();
        Cat barsik = new Cat();
        antony.say();
        barsik.say();
    }
}

interface Sound {
    public void say();
}

class Dog implements Sound {
    @Override
    public void say() {
        System.out.println("Gav!");
    }
}

class Cat implements Sound {
    @Override
    public void say() {
        System.out.println("MYAU");
    }
}
