package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public void initUI() {
        MainPage mainPage = new MainPage(this);
        setContentPane(mainPage);

        setTitle("Quoridor");
        setResizable(false);
        int width = 800;
        int height = 800;
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/main/resources/images/logo.png").getImage());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) { //added just to see how the actual game would look like
        MyFrame myFrame = new MyFrame();
        myFrame.initUI();
    }

}
