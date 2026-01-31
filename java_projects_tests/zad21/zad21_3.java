public class zad21_3 {
    public static void main(String[] args) {
        String name = "Гость";
        if (args.length > 0 && args[0] != null) {
            name = args[0];
        }
        System.out.println("Привет " + name);
    }
}
