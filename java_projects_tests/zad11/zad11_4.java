public class zad11_4 {
    public static void main(String[] args) {
        Rectangle[] figurs = new Rectangle[5];

        figurs[0] = new Rectangle(5.0, 3.0);
        figurs[1] = new Rectangle(10.0, 4.0);
        figurs[2] = new Rectangle(7.5, 2.5);
        figurs[3] = new Rectangle(8.0, 6.0);
        figurs[4] = new Rectangle(12.0, 5.0);

        Rectangle bigAss = figurs[0];

        for (int i = 0; i < figurs.length; i++) {
            if (figurs[i].s > bigAss.s) {
                bigAss = figurs[i];
            }
        }

        bigAss.information();
    }
}

class Rectangle {
    double length;
    double wength;
    double s; // Объявляем поле, но НЕ инициализируем здесь

    public Rectangle(double length, double wength) {
        this.length = length;
        this.wength = wength;
        this.s = length * wength; // ✅ Вычисляем ПОСЛЕ присваивания length и width
    }

    public void information() {
        System.out.println(length);
        System.out.println(wength);
        System.out.println(s);
    }
}
