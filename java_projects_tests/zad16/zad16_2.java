import java.util.ArrayList;

public class zad16_2 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);
        int count = 0;
        for (int number : numbers) {
            count += number;
        }
        System.out.println("Сумма всех элементов: " + count);
    }
}
