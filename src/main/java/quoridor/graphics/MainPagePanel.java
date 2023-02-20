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
    public static final int X_START_VARIABLE = 85;
    public static final int Y_START_VARIABLE = 150;
    private final JFrame jFrame;
    private final Color backgroundColor;
    private static final int WIDTH_WINDOW = 700;
    private static final int HEIGHT_WINDOW = 700;

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


    public MainPagePanel(JFrame jFrame, Color backgroundColor) {
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() { //used to setup the Panel
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(WIDTH_WINDOW, HEIGHT_WINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        BufferedImageLoader bufferedImageLoader = new BufferedImageLoader();

        title = bufferedImageLoader.loadImage("src/main/resources/drawable/images/title/title.png");

        htpBImages = new BufferedImage[2];
        playBImages = new BufferedImage[2];
        optionsBImages = new BufferedImage[2];
        exitBImages = new BufferedImage[2];

        htpBImages[0] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/htpButton/how_to_play_button.png");
        htpBImages[1] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/htpButton/how_to_play_button_hover.png");

        playBImages[0] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/playButton/play_button.png");
        playBImages[1] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/playButton/play_button_hover.png");

        optionsBImages[0] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/optionsButton/options_button.png");
        optionsBImages[1] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/optionsButton/options_button_hover.png");

        exitBImages[0] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/exitButton/exit_button.png");
        exitBImages[1] = bufferedImageLoader.loadImage("src/main/resources/drawable/images/exitButton/exit_button_hover.png");

        yButtons = HEIGHT_WINDOW / 2 - Y_START_VARIABLE;
        xButtons = WIDTH_WINDOW / 2 - X_START_VARIABLE;
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

    public void initTimer() {
        int delay = 1;
        Timer timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        int yTitle = 50;
        g2d.drawImage(title, WIDTH_WINDOW / 2 - title.getWidth() / 2, yTitle, null);

        yButtons = HEIGHT_WINDOW / 2 - Y_START_VARIABLE;
        xButtons = WIDTH_WINDOW / 2 - X_START_VARIABLE;
        distance = 110;

        if (changeB1) g2d.drawImage(htpBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(htpBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if (changeB2) g2d.drawImage(playBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(playBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if (changeB3) g2d.drawImage(optionsBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(optionsBImages[0], xButtons, yButtons, null);

        yButtons += distance;
        if (changeB4) g2d.drawImage(exitBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(exitBImages[0], xButtons, yButtons, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectHtpB.contains(x, y)) {
            playAudio(1);
            RulesPanel rulesPanel = null;
            try {
                rulesPanel = new RulesPanel(jFrame, backgroundColor);
            } catch (IOException | FontFormatException ex) {
                ex.printStackTrace();
            }
            jFrame.setContentPane(rulesPanel);
            jFrame.revalidate();
        }

        if (rectPlayB.contains(x, y)) {
            playAudio(1);

            PrePlayPanel prePlayPanel = null;
            try {
                prePlayPanel = new PrePlayPanel(jFrame, backgroundColor);
            } catch (IOException | FontFormatException ex) {
                ex.printStackTrace();
            }
            jFrame.setContentPane(prePlayPanel);
            jFrame.revalidate();
        }

        if (rectOptionsB.contains(x, y)) {
            playAudio(1);
            OptionsPanel optionsPanel = new OptionsPanel(jFrame, backgroundColor);

            jFrame.setContentPane(optionsPanel);
            jFrame.revalidate();
        }

        if (rectExitB.contains(x, y)) {
            playAudio(1);
            jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)); //X with custom button
        }
    }

    private void playAudio(int x) {
        try {
            buttonAudio[x].createAudio();
            buttonAudio[x].playAudio();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectHtpB.contains(x, y)) {
            if (!changeB1) {
                playAudio(0);
            }
            changeB1 = true;
        } else changeB1 = false;

        if (rectPlayB.contains(x, y)) {
            if (!changeB2) {
                playAudio(0);
            }
            changeB2 = true;
        } else changeB2 = false;

        if (rectOptionsB.contains(x, y)) {
            if (!changeB3) {
                playAudio(0);
            }
            changeB3 = true;
        } else changeB3 = false;

        if (rectExitB.contains(x, y)) {
            if (!changeB4) {
                playAudio(0);
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
