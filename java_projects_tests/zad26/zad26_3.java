import java.util.*;

public class zad26_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        int count = readInt(scanner);
        List<Student> students = addList(scanner, count);
        printArray(students);
    }

    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                int x = scanner.nextInt();
                scanner.nextLine();
                if (x <= 0) {
                    System.out.println("int is <= 0");
                    continue;
                }
                return x;
            } catch (InputMismatchException e) {
                System.out.println("It's not int");
                scanner.nextLine();
            }
        }
    }

    public static String readName(Scanner scanner) {
        String s = scanner.nextLine();
        // scanner.nextLine();
        return s;
    }

    public static int readAge(Scanner scanner) {
        try {
            int age = scanner.nextInt();
            scanner.nextLine();
            return age;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    public static double readAvgBall(Scanner scanner) {
        try {
            double avg = scanner.nextDouble();
            scanner.nextLine();
            return avg;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1.0;
        }
    }

    public static List<Student> addList(Scanner scanner, int count) {
        List<Student> students = new ArrayList<>();
        int i = 0;
        while (i < count) {
            String name = readName(scanner);
            int age = readAge(scanner);
            if (age <= 0) {
                System.out.println("Incoorect input <= 0");
                i++;
                continue;
            }
            double avg = readAvgBall(scanner);
            if (avg <= 0) {
                System.out.println("Incoorect input <= 0");
                i++;
                continue;
            }
            students.add(new Student(name, age, avg));
            i++;
        }
        return students;
    }

    public static void printArray(List<Student> students) {
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
}

class Student {
    private final String name;
    private final int age;
    private final double avgBall;

    public Student(String name, int age, double avgBall) {
        this.name = name;
        this.age = age;
        this.avgBall = avgBall;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getAvgBall() {
        return avgBall;
    }

    public String toString() {
        return name + " " + age + " " + avgBall;
    }
}
