public class zad14_1 {
    public static void main(String[] args) {
        Animal[] animals = new Animal[3];
        animals[0] = new Cat();
        animals[1] = new Dog();
        animals[2] = new Cat();
        for (Animal animal : animals) {
            animal.say();
        }
    }
}

class Animal {
    public void say() {
        System.out.println("Какой-то звук");
    }
}

class Cat extends Animal {
    @Override
    public void say() {
        System.out.println("Мяу");
    }
}

class Dog extends Animal {
    @Override
    public void say() {
        System.out.println("Гав");
    }
}
