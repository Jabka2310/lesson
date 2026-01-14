public class Main {
    public static void main(String[] args) {
        point gg = new point();
        gg.x = 0;
        gg.y = 0;

        System.out.println(gg.lenngth(3, 4));
    }
}

public class point {
    double x;
    double y;

    public double lenngth(double x1, double y1) {
        return Math.sqrt(Math.pow((x1 - x), 2) + Math.pow((y1 - y), 2));
    }
}
