public class zad17_4 {
    public static void main(String[] args) {
        double a = 10.0;
        double b = 0;
        System.out.println("Результат деления: " + safeDel(a, b));
    }
    public static double safeDel(double a, double b) {
        try {
            if (b == 0) {
                throw new ArithmeticException("деление на ноль!");
            }
            return a / b;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return 0;
        }
    }
}
