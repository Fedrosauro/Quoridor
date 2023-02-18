package quoridor.graphics;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private JFrame jFrame;
    private Color backgroundColor;

    public PlayPanel(JFrame jFrame, Color backgroundColor, int size1, int size2, int numberPlayers, int wallDimension, int numberWalls){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
    }


}
