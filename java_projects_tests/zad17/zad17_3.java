public class zad17_3 {
    public static void main(String[] args) {
        String a = "aab";
        if (intoInt(a) != -1) {
            System.out.println(intoInt(a));
        }
    }
    public static int intoInt(String b) {
        try {
            return Integer.parseInt(b);
        } catch (Exception e) {
            System.out.println("Это не число: " + e.getMessage());
            return -1;
        }
    }
}
