public class zad21 {
    public static void main(String[] args) {
        int count = args.length;
        System.out.println("Количество элементов: " + count);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Аргумет " + i + " " + args[i]);
        }
    }
}
