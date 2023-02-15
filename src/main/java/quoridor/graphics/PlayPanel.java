package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private JFrame jFrame;
    private Color backgroundColor;

    public PlayPanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
    }
}
