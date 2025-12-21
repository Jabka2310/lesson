public class zad6_10 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 5) {
                count += arr[i];
            }
        }
        System.out.println("Сумма больше 5 - " + count);
    }
}