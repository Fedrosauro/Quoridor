package quoridor.graphics;

import quoridor.utils.BufferedImageLoader;
import quoridor.utils.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MainPagePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color color;
    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;

    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] htpB_images, playB_images, optionsB_images, exitB_images;
    private BufferedImage title;
    private Rectangle2D rectHtpB, rectPlayB, rectOptionsB, rectExitB;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private int distance;
    private boolean changeB1, changeB2, changeB3, changeB4;


    public MainPagePanel(JFrame jFrame, Color color){
        this.jFrame = jFrame;
        this.color = color;

        setup();
        initTimer();
    }

    private void setup(){ //used to setup the Panel
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(color);

        loader = new BufferedImageLoader();

        title = loader.loadImage("src/main/resources/images/title/title.png");

        htpB_images = new BufferedImage[2];
        playB_images = new BufferedImage[2];
        optionsB_images = new BufferedImage[2];
        exitB_images = new BufferedImage[2];

        htpB_images[0] = loader.loadImage("src/main/resources/images/htpButton/how_to_play_button.png");
        htpB_images[1] = loader.loadImage("src/main/resources/images/htpButton/how_to_play_button_hover.png");

        playB_images[0] = loader.loadImage("src/main/resources/images/playButton/play_button.png");
        playB_images[1] = loader.loadImage("src/main/resources/images/playButton/play_button_hover.png");

        optionsB_images[0] = loader.loadImage("src/main/resources/images/optionsButton/options_button.png");
        optionsB_images[1] = loader.loadImage("src/main/resources/images/optionsButton/options_button_hover.png");

        exitB_images[0] = loader.loadImage("src/main/resources/images/exitButton/exit_button.png");
        exitB_images[1] = loader.loadImage("src/main/resources/images/exitButton/exit_button_hover.png");

        yButtons = height/2 - 150;
        xButtons = width/2 - 85;
        heightB = 100;
        widthB = 170;
        distance = 110;

        rectHtpB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        yButtons += distance;
        rectPlayB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB2 = false;

        yButtons += distance;
        rectOptionsB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB3 = false;

        yButtons += distance;
        rectExitB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB4 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");
    }

    public void initTimer(){
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

        g2d.drawImage(title, width/2 - title.getWidth()/2, 50, null);

        yButtons = height/2 - 150;
        xButtons = width/2 - 85;
        distance = 110;

        if(changeB1) g2d.drawImage(htpB_images[1], xButtons, yButtons, null);
        else g2d.drawImage(htpB_images[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB2) g2d.drawImage(playB_images[1], xButtons, yButtons, null);
        else g2d.drawImage(playB_images[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB3) g2d.drawImage(optionsB_images[1], xButtons, yButtons, null);
        else g2d.drawImage(optionsB_images[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB4) g2d.drawImage(exitB_images[1], xButtons, yButtons, null);
        else g2d.drawImage(exitB_images[0], xButtons, yButtons, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectHtpB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            RulesPanel rulesPanel = new RulesPanel(jFrame);
            jFrame.setContentPane(rulesPanel);
            jFrame.revalidate();
        }

        if (rectPlayB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            PrePlayPanel prePlayPanel = new PrePlayPanel(jFrame);
            jFrame.setContentPane(prePlayPanel);
            jFrame.revalidate();
        }

        if(rectOptionsB.contains(x, y)){
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            OptionsPanel optionsPanel = new OptionsPanel(jFrame, color);
            jFrame.setContentPane(optionsPanel);
            jFrame.revalidate();
        }

        if(rectExitB.contains(x, y)){
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)); //X with custom button
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectHtpB.contains(x, y)) {
            if(!changeB1){
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB1 = true;
        } else changeB1 = false;

        if (rectPlayB.contains(x, y)) {
            if(!changeB2){
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB2 = true;
        } else changeB2 = false;

        if (rectOptionsB.contains(x, y)) {
            if(!changeB3){
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB3 = true;
        } else changeB3 = false;

        if (rectExitB.contains(x, y)) {
            if(!changeB4){
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB4 = true;
        } else changeB4 = false;
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
}
