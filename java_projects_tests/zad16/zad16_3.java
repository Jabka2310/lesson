import java.util.ArrayList;

public class zad16_3 {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Никита");
        names.add("Соня");
        names.add("Артём");
        names.add("Саша");
        names.add("Юля");
        names.remove(2);
        for (String name : names) {
            System.out.println(name);
        }
    }
}

// import java.util.ArrayList;
//
// public class zad16_3 {
//     public static void main(String[] args) {
//         ArrayList<Integer> numbers = new ArrayList<>();
//         numbers.add(1);
//         numbers.add(2);
//         numbers.add(3);
//         numbers.add(4);
//         numbers.add(5);
//         numbers.remove(2);
//         for (int number : numbers) {
//             System.out.println(number);
//         }
//     }
// }
