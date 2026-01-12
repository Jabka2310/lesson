public class zad7_9 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 101};
        System.out.println("Среднее число: " + avgArr(arr));
    }
    public static double avgArr(int[] arr) {
        int ss = 0;
        for (int i = 0; i < arr.length; i++) {
            ss += arr[i];
        }
        return (double) ss / arr.length;
    }
}
