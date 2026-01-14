public class zad12_3 {
  public static void main (String[] args) {
    
  }
}

class Counter {
  static int count = 0;
  public Counter() {
    count++;
    System.out.println("Создано объектов");
  }
  public static int pol_kol_va() {
    return count;
  }
}
