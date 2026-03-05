// Выводит простое окошко но монитор, прикол)))
import javax.swing.JFrame;

public class Z {
    public static void main(String[] args) {
        JFrame myFrame = new JFrame();
        String myTitle = "keeeeks";

        myFrame.setTitle(myTitle);
        myFrame.setSize(300, 200);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }
}
