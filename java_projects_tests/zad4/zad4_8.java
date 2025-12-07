public class zad4_5 {
    public static void main(String[] args) {
        int counter = 0;
        int i = 0;
        while (i <= 50) {
            counter += i;
            i++;
        }
        System.out.println("Сумма чисел от 1 до 50: " + counter);
    }
}