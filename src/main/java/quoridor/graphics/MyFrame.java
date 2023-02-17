package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(){
        initUI();
    }

    public void initUI() {
        setContentPane(new MainPagePanel(this, Color.BLACK));

        pack();

        setTitle("Quoridor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/main/resources/images/logo/logo.png").getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) { ///added just to see how the actual game would look like
        MyFrame myFrame = new MyFrame();
    }

}
