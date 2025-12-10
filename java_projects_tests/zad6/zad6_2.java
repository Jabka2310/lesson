public class zad6_2 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            counter += arr[i];
        }
        System.out.println("Сумма элементов: " + counter);
    }
}