
public class zad17_1 {
    public static void main(String[] args) {
        int a = 11;
        int b = 0;
        System.out.println(dev(a, b));
    }

    public static int dev(int a, int b) {
        int c = -1;
        try {
            c = a / b;
        } catch (ArithmeticException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        return c;
    }
}
