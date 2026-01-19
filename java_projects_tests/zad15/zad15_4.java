public class zad15_4 {
    public static void main(String[] args) {
        Bird popugay = new Bird();
        popugay.flyte();
        Fish dori = new Fish();
        dori.to_swim();
    }
}

interface Fly {
    public void flyte();
}

interface Swim {
    public void to_swim();
}

class Bird implements Fly {
    @Override
    public void flyte() {
        System.out.println("Птица летит в небе");
    }
}

class Fish implements Swim {
    @Override
    public void to_swim() {
        System.out.println("Рыба плавает в воде");
    }
}
