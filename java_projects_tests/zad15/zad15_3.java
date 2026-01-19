public class zad15_3 {
    public static void main(String[] args) {
        Bird popugay = new Bird();
        popugay.flyte();
    }
}

interface Fly {
    public void flyte();
}

class Bird implements Fly {
    @Override
    public void flyte() {
        System.out.println("Птица летит в небе");
    }
}
