public class zad21_2 {
    public static void main(String[] args) {
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        if (args[2].equals("+")) {
            System.out.println(a + b);
        }
        if (args[2].equals("-")) {
            System.out.println(a - b);
        }
        if (args[2].equals("*")) {
            System.out.println(a * b);
        }
        if (args[2].equals("/")) {
            System.out.println(a / b);
        }
    }
}
