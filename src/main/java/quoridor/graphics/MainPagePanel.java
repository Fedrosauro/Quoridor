package quoridor.graphics;

import quoridor.media.BufferedImageLoader;
import quoridor.media.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainPagePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final JFrame jFrame;
    private final Color backgroundColor;
    private static final int WIDTHWINDOW = 700;
    private static final int HEIGHTWINDOW = 700;
    private static final int DELAY = 1;

    private transient AudioPlayer[] buttonAudio;

    private transient BufferedImage[] htpBImages;
    private transient BufferedImage[] playBImages;
    private transient BufferedImage[] optionsBImages;
    private transient BufferedImage[] exitBImages;
    private transient BufferedImage title;
    private transient Rectangle2D rectHtpB;
    private transient Rectangle2D rectPlayB;
    private transient Rectangle2D rectOptionsB;
    private transient Rectangle2D rectExitB;
    private int xButtons;
    private int yButtons;
    private int distance;
    private boolean changeB1;
    private boolean changeB2;
    private boolean changeB3;
    private boolean changeB4;


    public MainPagePanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup(){ //used to setup the Panel
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        BufferedImageLoader bufferedImageLoader = new BufferedImageLoader();

        title = bufferedImageLoader.loadImage("src/main/resources/images/title/title.png");

        htpBImages = new BufferedImage[2];
        playBImages = new BufferedImage[2];
        optionsBImages = new BufferedImage[2];
        exitBImages = new BufferedImage[2];

        htpBImages[0] = bufferedImageLoader.loadImage("src/main/resources/images/htpButton/how_to_play_button.png");
        htpBImages[1] = bufferedImageLoader.loadImage("src/main/resources/images/htpButton/how_to_play_button_hover.png");

        playBImages[0] = bufferedImageLoader.loadImage("src/main/resources/images/playButton/play_button.png");
        playBImages[1] = bufferedImageLoader.loadImage("src/main/resources/images/playButton/play_button_hover.png");

        optionsBImages[0] = bufferedImageLoader.loadImage("src/main/resources/images/optionsButton/options_button.png");
        optionsBImages[1] = bufferedImageLoader.loadImage("src/main/resources/images/optionsButton/options_button_hover.png");

        exitBImages[0] = bufferedImageLoader.loadImage("src/main/resources/images/exitButton/exit_button.png");
        exitBImages[1] = bufferedImageLoader.loadImage("src/main/resources/images/exitButton/exit_button_hover.png");

        yButtons = HEIGHTWINDOW /2 - 150;
        xButtons = WIDTHWINDOW /2 - 85;
        int heightB = 100;
        int widthB = 170;
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
        Timer timer = new Timer(DELAY, this);
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

        g2d.drawImage(title, WIDTHWINDOW /2 - title.getWidth()/2, 50, null);

        yButtons = HEIGHTWINDOW /2 - 150;
        xButtons = WIDTHWINDOW /2 - 85;
        distance = 110;

        if(changeB1) g2d.drawImage(htpBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(htpBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB2) g2d.drawImage(playBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(playBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB3) g2d.drawImage(optionsBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(optionsBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if(changeB4) g2d.drawImage(exitBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(exitBImages[0], xButtons, yButtons, null);
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
            RulesPanel rulesPanel = new RulesPanel(jFrame, backgroundColor);
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
            PrePlayPanel prePlayPanel = null;
            try {
                prePlayPanel = new PrePlayPanel(jFrame, backgroundColor);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            }
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
            OptionsPanel optionsPanel = new OptionsPanel(jFrame, backgroundColor);

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
        //not used because not needed
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //not used because not needed
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //not used because not needed
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //not used because not needed
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        //not used because not needed
    }
}
