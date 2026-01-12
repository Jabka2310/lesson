public class zad7_8 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 100, -20};
        System.out.println("Максимальное число: " + maxArr(arr));
    }
    public static int maxArr(int[] arr) {
        int mmax = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > mmax) {
                mmax = arr[i];
            }
        }
        return mmax;
    }
}
