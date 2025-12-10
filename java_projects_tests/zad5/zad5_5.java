// public class zad4_5 {
//     public static void main(String[] args) {
//         for (int i = 1; i <= 5; i++){
//             for (int j = 1; j <= 5; j++) {
//                 if (j % 2 == 0) {
//                     System.out.print("_");
//                 } else {
//                     System.out.print("*");
//                 }
//             } 
//             System.out.println();
//         }
//     }
// }

public class zad5_5 {
    public static void main(String[] args) {
        int size = 5;
        int center = size / 2;
        
        for (int i = 0; i < size; i++) {
            // Определяем расстояние от центра
            int distance = (i <= center) ? (center - i) : (i - center);
            
            // Пробелы
            for (int j = 0; j < distance; j++) {
                System.out.print(" ");
            }
            
            // Звездочки
            int stars = size - 2 * distance;
            for (int j = 0; j < stars; j++) {
                System.out.print("*");
            }
            
            System.out.println();
        }
    }
}