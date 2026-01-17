public class zad13_3 {
    public static void main(String[] args) {
        Okr o = new Okr(5);
        Pr aabb = new Pr(3, 5);
        System.out.println(o.s());
        System.out.println(aabb.s());
    }
}

class Figure {
    public double s() {
        return 0.0;
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
}
