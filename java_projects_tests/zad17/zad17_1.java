public class zad17_1 {
    public static void main(String[] args) {
        int a = 10;
        int b = 2;
        System.out.println(dev(a, b));
    }

    public static int dev(int a, int b) {
        try {
            b == 0;
        } catch (NullDev e) {
            System.out.println("На ноль делить нельзя! Дурачок");
        }
        return a / b;
    }
}
