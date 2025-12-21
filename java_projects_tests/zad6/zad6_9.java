public class zad6_9 {
    public static void main(String[] args) {
        int arr[] = {1, 8, 14, 23, 11, 10, 4, -45, 11};
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 10) {
                count++;
            }
        }
        System.out.println("Количество больше 10: " + count);
    }
}