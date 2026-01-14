public class Main {
    public static void main(String[] args) {
        car opel = new car();
        opel.brend = "Opel";
        opel.model = "Astra";
        opel.year = 2005;
        opel.probeg = 30000;
        opel.information();
    }
}

public class car {
    String brend;
    String model;
    int year;
    int probeg;

    public void information() {
        System.out.println(brend);
        System.out.println(model);
        System.out.println(year);
        System.out.println(probeg);
    }
}
