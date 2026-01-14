public class zad11_1 {
    public static void main(String[] args) {
        human[] peopls = new human[5];

        peopls[0] = new human("Nikita", 23);
        peopls[1] = new human("Sofia", 23);
        peopls[2] = new human("Alex", 44);
        peopls[3] = new human("Artem", 32);
        peopls[4] = new human("Den", 23);

        for (int i = 0; i < peopls.length; i++) {
            peopls[i].say();
            if (i + 1 < peopls.length) {
                System.out.println();
            }
        }
    }
}

class human {
    String name;
    int age;

    public human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void say() {
        System.out.println(name);
        System.out.println(age);
    }
}
