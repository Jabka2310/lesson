public class zad7_7 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int c = ssArr(arr);
        System.out.println(c);
    }
    static int ssArr(int[] arr) {
        int c = 0;
        for (int i = 0; i < arr.length; i++) {
            c += arr[i];
        }
        return c;
    }
}
