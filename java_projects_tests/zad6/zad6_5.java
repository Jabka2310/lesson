public class zad6_5 {
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            counter += arr[i];
        }
        double argv = (double) counter / arr.length;
        System.out.println("Среднее арифметическое: " + argv);
    }
}