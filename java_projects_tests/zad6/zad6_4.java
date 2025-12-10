public class zad6_4 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int min = arr[0];
        for (int i = 0; i < 5; i++) {
            if (arr[i] <= min) {
                min = arr[i];
            }
        }
        System.out.println("Минимальный элемент: " + min);
    }
}