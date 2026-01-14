public class zad11_5 {
    public static void main(String[] args) {
        Tovar[] vegetables = new Tovar[5];

        vegetables[0] = new Tovar("Помидоры", 150.0);
        vegetables[1] = new Tovar("Огурцы", 120.0);
        vegetables[2] = new Tovar("Морковь", 80.0);
        vegetables[3] = new Tovar("Картофель", 60.0);
        vegetables[4] = new Tovar("Капуста", 90.0);

        double count = 0;
        for (int i = 0; i < vegetables.length; i++) {
            count += vegetables[i].price;
        }
        System.out.println(count);
    }
}

class Tovar {
    String name;
    double price;
    public Tovar(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
