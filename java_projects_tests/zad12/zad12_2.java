public class zad12_2 {
    public static void main(String[] args) {
        int a = 2;
        int b = 4;
        System.out.println(Mathems.ssum(a, b));
        System.out.println(Mathems.yy(a, b));
        System.out.println(Mathems.step(a, b));
    }
}

class Mathems {
    public static int ssum(int a, int b) {
        return a + b;
    }
    public static int yy(int a, int b) {
        return a * b;
    }
    public static int step(int a, int b) {
        int c = a;
        for (int i = 1; i < b; i++) {
            c *= a;
        }
        return c;
    }
}
