public class zad26_1 {
    public static void main(String[] args) {
        int n1 = Integer.parseInt(args[0]);
        int n2 = Integer.parseInt(args[1]);
        if (args[2].equals("+")) {
            System.out.println(n1 + n2);
        }
        if (args[2].equals("-")) {
            System.out.println(n1 - n2);
        }
        if (args[2].equals("/")) {
            System.out.println(n1 / n2);
        }
        if (args[2].equals("*")) {
            System.out.println(n1 * n2);
        }
    }
}
