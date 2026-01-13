public class Main {
    public static void main(String[] args) {
        Pr aaBB = new Pr();
        aaBB.length = 4.2;
        aaBB.wength = 3.4;
        double s = aaBB.S();
        System.out.println(s);
    }
}

public class Pr {
    double length;
    double wength;
    public double S() {
        return length * wength;
    }
}
