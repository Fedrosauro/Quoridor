package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        initUI();
    }

    public void initUI() {
        setContentPane(new MainPagePanel(this, Color.BLACK));
        pack();

        setTitle("Quoridor");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/main/resources/drawable/images/logo/logo.png").getImage());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
        });
    }

}
