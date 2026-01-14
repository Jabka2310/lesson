public class Main {
    public static void main(String[] args) {
        double ww = 300;
        Bank bb = new Bank();
        bb.balance = 200;
        System.out.println(bb.Ssum(ww));
        System.out.println(bb.Mminus(ww));
        bb.information();
    }
}

public class Bank {
    double balance;

    public double Ssum(double hh) {
        return balance + hh;
    }

    public double Mminus(double hh) {
        if (hh > balance) {
            System.out.println("Недостаточно средств на балансе");
        } else {
            balance -= hh;
        }
        return balance;
    }

    public void information() {
        System.out.println(balance);
    }
}
