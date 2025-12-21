public class zad7_4 {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        int c = mmin(a, b);
        System.out.println("Минимальное число: " + c);
    }

    public static int mmin(int a, int b) {
        int c;
        if (a <= b) {
            c = a;
        } else {
            c = b;
        }
        return c;
    }
}
