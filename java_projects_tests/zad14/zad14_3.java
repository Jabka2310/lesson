public class zad14_3 {
    public static void main(String[] args) {
        Car toyota = new Car();
        Bycecle bmx = new Bycecle();
        goVenchil(toyota);
        goVenchil(bmx);
    }

    public static void goVenchil(Venchel x) {
        x.go(); // Вызываем метод объекта, а не класса
    }
}

class Venchel {
    public void go() { // Убрали static для полиморфизма
        System.out.print("Едет по ");
    }
}

class Car extends Venchel {
    @Override
    public void go() {
        System.out.println("Автомобиль едет по дороге");
    }
}

class Bycecle extends Venchel {
    @Override
    public void go() {
        System.out.println("Велосипед едет по тротуару");
    }
}
