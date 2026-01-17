public class zad13_5 {
    public static void main(String[] args) {
        Phone iphone = new Phone();
        Laptop mac = new Laptop();
        iphone.on();
        mac.on();
        System.out.println(iphone.power);
        System.out.println(mac.power);
    }
}

class Gadget {
    int power = 0;
    public void on() {
        power = 1;
        System.out.println("Устройство включено");
    }
}

class Phone extends Gadget {
    @Override
    public void on() {
        System.out.println("Телефон включен");
    }
}

class Laptop extends Gadget {
    @Override
    public void on() {
        power = 1;
        System.out.println("Ноутбук включен");
    }
}
