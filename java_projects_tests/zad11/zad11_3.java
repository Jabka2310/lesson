public class zad11_3 {
    public static void main(String[] args) {
        Student[] students = new Student[5];

        students[0] = new Student("Никита", "24-KC-9", 4.5);
        students[1] = new Student("София", "24-KC-9", 4.8);
        students[2] = new Student("Алексей", "24-KC-8", 4.2);
        students[3] = new Student("Мария", "24-KC-9", 4.9);
        students[4] = new Student("Дмитрий", "24-KC-8", 4.3);

        Student big_ball = students[0];

        for (int i = 0; i < students.length; i++) {
            if (students[i].avgBall > big_ball.avgBall) {
                big_ball = students[i];
            }
        }

        big_ball.say();
    }
}

class Student {
    String name;
    String group;
    double avgBall;

    public Student(String name, String group, double avgBall) {
        this.name = name;
        this.group = group;
        this.avgBall = avgBall;
    }

    public void say() {
        System.out.println(name);
        System.out.println(group);
        System.out.println(avgBall);
    }
}
