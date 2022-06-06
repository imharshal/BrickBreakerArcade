package brickBreaker;
import javax.swing.JFrame;

//The main window, acts as the main entry point
public class Breakout extends JFrame implements Common {
    public Breakout() {
        add(new GamePanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set nad fix size
        setSize(Common.WIDTH + 8, Common.HEIGHT + 8);
        setLocationRelativeTo(null); // Center the game panel
        setResizable(false);

        // Since repaint() is manually called at desired rate in the GamePanel,
        //disable calls from the OS
        setIgnoreRepaint(true);

        //launch
        setTitle("Breakout Game");
        setVisible(true);
    }
    public static void main(String[] args) {
        new Breakout();
    }
}
