public class Main {
    public static void main(String[] args) {
        Student Den = new Student();
        Den.name = "Daniil";
        Den.group = "11a";
        Den.avg_ball = 3.45;
        Den.information();
    }
}

public class Student {
    String name;
    String group;
    double avg_ball;

    public void information() {
        System.out.println(name);
        System.out.println(group);
        System.out.println(avg_ball);
    }
}
