public class zad22_2 {
    public static void main(String[] args) {
        Car toyota = new Car("Carina", 1991);
        toyota.info();
    }
}

class Car {
    String name;
    int year;
    public Car(String name, int year) {
        this.name = name;
        this.year = year;
    }
    public void info() {
        System.out.println(name);
        System.out.println(year);
    }
}
