public class zad13_4 {
    public static void main(String[] args) {
        Unit oleg = new Unit("Oleg", 45);
        Manager cc = new Manager("cc", 48, 2);
        oleg.say();
        System.out.println();
        cc.say();
    }
}

class Unit {
    String name;
    int cash;
    public Unit(String name, int cash) {
        this.name = name;
        this.cash = cash;
    }
    public void say() {
        System.out.println(name);
        System.out.println(cash);
    }
}

class Manager extends Unit {
    int bonus;
    public Manager(String name, int cash, int bonus) {
        super(name, cash);
        this.bonus = bonus;
    }
    @Override
    public void say() {
        System.out.println(name);
        System.out.println(cash);
        System.out.println(bonus);
    }
}
