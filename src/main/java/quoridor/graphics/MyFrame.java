package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(){
        initUI();
    }

    private void initUI() {
        setTitle("Quoridor");
        setResizable(false);
        int width = 1100;
        int height = 625;
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("/src/main/resources/images/logo.png").getImage());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
