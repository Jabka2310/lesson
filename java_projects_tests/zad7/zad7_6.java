public class zad7_6 {
    public static void main(String[] args) {
        int a = 10;
        System.out.println("Число " + a + " четное? " + isEven(a));
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}
