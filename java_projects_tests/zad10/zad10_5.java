public class zad10_5 {
    public static void main(String[] args) {
        Tovar apple = new Tovar("apple", 3, 100);
        apple.information();
        System.out.println(apple.BigPrice());
    }
}

class Tovar {
    String name;
    double price;
    int kolVo;

    public Tovar(String name, double price, int kolVo) {
        this.name = name;
        this.price = price;
        this.kolVo = kolVo;
    }

    public void information() {
        System.out.println(name);
        System.out.println(price);
        System.out.println(kolVo);
    }

    public double BigPrice() {
        return price * kolVo;
    }
}
