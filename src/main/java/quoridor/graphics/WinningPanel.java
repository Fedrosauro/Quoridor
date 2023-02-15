package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class WinningPanel extends JPanel {
    private JFrame jFrame;
    private Color backgroundColor;

    public WinningPanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
    }
}
