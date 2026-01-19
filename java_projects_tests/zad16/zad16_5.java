import java.util.ArrayList;

public class zad16_5 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(14);
        numbers.add(4);
        numbers.add(47);
        numbers.add(10);
        numbers.add(-54);
        numbers.add(0);
        numbers.add(16);
        numbers.add(3);
        numbers.add(154);
        numbers.add(14);
        int min = numbers.get(0);
        int max = numbers.get(0);
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
            if (number < min) {
                min = number;
            }
        }
        System.out.println(min);
        System.out.println(max);
    }
}
