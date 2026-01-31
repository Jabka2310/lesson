public class PrimeNumbers {
    public static void main(String[] args) {
        System.out.println("Простые числа от 1 до 100:");

        // Перебираем все числа от 2 до 100 (1 не является простым числом)
        for (int number = 2; number <= 100; number++) {
            boolean isPrime = true;

            // Проверяем делители от 2 до number-1
            for (int divisor = 2; divisor < number; divisor++) {
                if (number % divisor == 0) {
                    // Найден делитель, число не простое
                    isPrime = false;
                    break; // Прерываем внутренний цикл, т.к. уже понятно, что число составное
                }
            }

            // Если не нашли делителей - число простое
            if (isPrime) {
                System.out.print(number + " ");
            }
        }
        System.out.println();
    }
}
