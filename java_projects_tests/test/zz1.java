import java.util.*;
import java.util.stream.*;

public class zad1 {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>("Яблоко", "Банан", "Апельсин");

        List<String> upperFruits = fruits.stream().map(String::toUpperCase).collect(Collectors.toList());
        for (String string : upperFruits) {
            System.out.println(string);
        }
    }
}
