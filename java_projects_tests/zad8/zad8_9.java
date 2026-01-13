public class zad8_9 {
    public static void main(String[] args) {
        String just = "apple, banana, oranges";
        String[] just2 = just.split(",");
        for (int i = 0; i < just2.length; i++) {
            System.out.println(just2[i]);
        }
        System.out.println(just2[0]);
        System.out.println(just2[1]);
        System.out.println(just2[2]);
    }
}
