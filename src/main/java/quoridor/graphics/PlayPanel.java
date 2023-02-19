package quoridor.graphics;

import quoridor.utils.BufferedImageLoader;
import quoridor.utils.OpponentType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color backgroundColor;
    private int size1, size2, numberPlayers, wallDimension, numberWalls;
    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;

    private BufferedImageLoader loader;

    //merged master into PlayPanel for game engine objects

    public PlayPanel(JFrame jFrame, Color backgroundColor, int size1, int size2, int numberPlayers, int wallDimension, int numberWalls){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
        this.size1 = size1;
        this.size2 = size2;
        this.numberPlayers = numberPlayers;
        this.wallDimension = wallDimension;
        this.numberWalls = numberWalls;

        setup();
        initTimer();
    }

    private void setup(){
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);



        loader = new BufferedImageLoader();
    }

    private void initTimer(){
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    private void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
