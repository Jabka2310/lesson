public class zad12_1 {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        System.out.println(Utils.maximum(a, b));
    }
}

class Utils {
    static int maximum(int a, int b) {
        return (a > b) ? a : b;
    }
}
