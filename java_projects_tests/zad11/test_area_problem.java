// Демонстрация проблемы с вычислением поля при объявлении

class RectangleWrong {
    double length;
    double width;
    double s = length * width; // ❌ Вычисляется ДО конструктора!

    public RectangleWrong(double length, double width) {
        this.length = length;
        this.width = width;
        // s уже вычислен как 0.0 * 0.0 = 0.0
    }
}

class RectangleRight {
    double length;
    double width;

    public RectangleRight(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // ✅ Метод вычисляет площадь КАЖДЫЙ РАЗ при вызове
    public double getArea() {
        return length * width;
    }
}

public class test_area_problem {
    public static void main(String[] args) {
        // ❌ Неправильный способ
        RectangleWrong wrong = new RectangleWrong(5.0, 3.0);
        System.out.println("Неправильно: площадь = " + wrong.s); // Выведет 0.0!

        // ✅ Правильный способ
        RectangleRight right = new RectangleRight(5.0, 3.0);
        System.out.println("Правильно: площадь = " + right.getArea()); // Выведет 15.0
    }
}
