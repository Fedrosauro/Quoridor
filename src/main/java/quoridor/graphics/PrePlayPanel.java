package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class PrePlayPanel extends JPanel {
    private JFrame jFrame;
    private Color backgroundColor;

    public PrePlayPanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
    }
}
