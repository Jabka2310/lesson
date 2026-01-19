import java.util.ArrayList;

public class zad16_8 {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Nikita", "23-KC", 4.34));
        students.add(new Student("Sofia", "23-KC", 4.8));
        students.add(new Student("Alex", "24-KC-8", 4.2));
        students.add(new Student("Maria", "23-KC", 4.9));
        students.add(new Student("Dmitry", "24-KC-8", 4.3));
        double avgMax = students.get(0).avg;
        int index = 0;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).avg > avgMax) {
                avgMax = students.get(i).avg;
                index = i;
            }
        }
        students.get(index).information();
    }
}

class Student {
    String name;
    String group;
    double avg;
    public Student(String name, String group, double avg) {
        this.name = name;
        this.group = group;
        this.avg = avg;
    }
    public void information() {
        System.out.println(name);
        System.out.println(group);
        System.out.println(avg);
    }
}
