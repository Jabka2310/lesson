public class zad15_1 {
    public static void main(String[] args) {
        Okr o = new Okr(5);
        Pr aabb = new Pr(5, 3);
        o.info();
        aabb.info();
    }
}

abstract class Figure {
    public abstract double s();
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
    public void info() {
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
    public void info() {
        System.out.println(s());
    }
}
