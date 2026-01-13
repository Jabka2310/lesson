public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 2;
        Calculator C = new Calculator();
        System.out.println(C.ssum(a, b));
        System.out.println(C.mminus(a, b));
        System.out.println(C.Um(a, b));
        System.out.println(C.Raz(a, b));
    }
}

public class Calculator {
    public double ssum(double a, double b) {
        return a + b;
    }

    public double mminus(double a, double b) {
        return a - b;
    }

    public double Um(double a, double b) {
        return a * b;
    }

    public double Raz(double a, double b) {
        return a / b;
    }
}
