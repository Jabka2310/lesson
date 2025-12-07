public class zad3_3 {
    public static void main(String[] args) {
        int score = 55;
        if (score >= 90) {
            System.out.println("Great");
        } else if (score >= 70 && score < 90) {
            System.out.println("Good");
        } else if (score >= 50 && score < 70) {
            System.out.println("Poydet");
        }
        if (score < 50) {
            System.out.println("XynR");
        }
    }
}
