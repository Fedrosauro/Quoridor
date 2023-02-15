package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {
    private JFrame jFrame;
    private Color backgroundColor;

    public RulesPanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
    }
}
