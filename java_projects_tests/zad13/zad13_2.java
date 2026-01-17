public class zad13_2 {
    public static void main(String[] args) {
        Car toyota = new Car(30);
        toyota.go();
        Bycecle bmw = new Bycecle(11);
        bmw.go();
    }
}

class Venchil {
    int speed;
    public Venchil(int speed) {
        this.speed = speed;
    }
    public void go() {
        System.out.println("Что-то надвигается со скоростью" + speed);
    }
}

class Car extends Venchil {
    public Car(int speed) {
        super(speed);
    }
    @Override
    public void go() {
        System.out.println("Машина едет со скоростью " + speed);
    }
}

class Bycecle extends Venchil {
    public Bycecle(int speed) {
        super(speed);
    }
    @Override
    public void go() {
        System.out.println("Велосипед едет со скоростью " + speed);
    }
}
