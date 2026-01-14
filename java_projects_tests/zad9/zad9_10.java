public class Main {
    public static void main(String[] args) {
        krug O = new krug();

        O.R = 5;
        System.out.println(O.S());
        System.out.println(O.P());
    }
}

public class krug {
    double R;

    public double S() {
        return Math.PI * Math.pow(R, 2);
    }

    public double P() {
        return 2 * Math.PI * R;
    }
}
