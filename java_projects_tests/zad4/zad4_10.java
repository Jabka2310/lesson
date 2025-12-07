public class zad4_5 {
    public static void main(String[] args) {
        int counter = 0;
        for (int i = 1; i <= 99; i++) {
            if (i % 2 != 0) {
                counter += i;
            }
        }
        System.out.println("Сумма всех нечетных чисел от 1 до 99: " + counter);
    }
}