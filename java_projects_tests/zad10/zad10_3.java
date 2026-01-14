public class Main {
    public static void main(String[] args) {
        Pr aaBB = new Pr(5, 3);
        System.out.println(aaBB.S());
    }
}

public class Pr {
    double length;
    double wength;

    public Pr(double length, double wength) {
        this.length = length;
        this.wength = wength;
    }

    public double S() {
        return length * wength;
    }
}
