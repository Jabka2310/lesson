import java.util.ArrayList;

public class zad16_1 {
    public static void main(String[] args) {
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Яблоко");
        fruits.add("Банан");
        fruits.add("Апельсин");
        fruits.add("Груша");
        fruits.add("Виноград");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }
}
