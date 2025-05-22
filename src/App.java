import javax.swing.SwingUtilities;
import view.MainFrame;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeLater(MainFrame :: new);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}

