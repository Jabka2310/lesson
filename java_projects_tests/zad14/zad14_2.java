public class zad14_2 {
    public static void main(String[] args) {
        Figure[] figurse = new Figure[3];
        figurse[0] = new Okr(5);
        figurse[1] = new Pr(3, 2);
        figurse[2] = new Okr(6);
        double count = 0.0;
        for (Figure figur : figurse) {
            count += figur.s();
        }
        System.out.println(count);
        for (Figure figur : figurse) {
            figur.say();
        }
    }
}

class Figure {
    public double s() {
        return 0.0;
    }
    public void say() {
        System.out.println(s()); // Выводит площадь фигуры
    }
}

class Okr extends Figure {
    double r;
    public Okr(double r) {
        this.r = r;
    }
    @Override
    public double s() {
        return Math.PI * Math.pow(r, 2);
    }
    public void say() {
        System.out.println(s());
    }
}

class Pr extends Figure {
    double length;
    double wength;
    public Pr(double length, double wength) {
        this.length = length;
        this.wength = wength;
    }
    @Override
    public double s() {
        return length * wength;
    }
    public void say() {
        System.out.println(s());
    }
}
