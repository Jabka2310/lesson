import java.util.ArrayList;

public class zad16_6 {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Aaaaa");
        strings.add("baaaa");
        strings.add("Aaaaa");
        strings.add("baaaa");
        strings.add("baaaa");
        int count = 0;
        for (String string : strings) {
            if (string.charAt(0) == 'A' || string.charAt(0) == 'a') {
                count++;
            }
        }
        System.out.println(count);
    }
}
