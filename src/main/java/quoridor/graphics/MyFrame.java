package quoridor.graphics;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MyFrame extends JFrame {

    public MyFrame() throws IOException, FontFormatException {
        initUI();
    }

    public void initUI() throws IOException, FontFormatException {
        setContentPane(new MainPagePanel(this, Color.BLACK));

        setResizable(false);
        pack();

        setTitle("Quoridor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/main/resources/images/logo/logo.png").getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) throws IOException, FontFormatException { ///added just to see how the actual game would look like
        MyFrame myFrame = new MyFrame();
    }

}
