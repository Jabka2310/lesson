public class Main {
    public static void main(String[] args) {
        Student Oleg = new Student("Oleg", "24-KC-9");
        Oleg.information();
    }
}

public class Student {
    String name;
    String group;
    double avg_ball = 0.0;

    public Student(String name, String group) {
        this.name = name;
        this.group = group;
        this.avg_ball = avg_ball;
    }

    public void information() {
        System.out.println(name);
        System.out.println(group);
        System.out.println(avg_ball);
    }
}
