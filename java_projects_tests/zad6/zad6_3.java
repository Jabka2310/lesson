public class zad6_3 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int max = arr[0];
        for (int i = 0; i < 5; i++) {
            if (arr[i] >= max) {
                max = arr[i];
            }
        }
        System.out.println("Максимальный элемент: " + max);
    }
}