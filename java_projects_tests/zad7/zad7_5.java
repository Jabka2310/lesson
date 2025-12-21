public class zad7_5 {
    public static void main(String[] args) {
        int n = 5;
        int fac = ffac(n);
        System.out.println("Факториал 3: " + fac);
    }

    public static int ffac(int n) {
        int f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        return f;
    }
}
