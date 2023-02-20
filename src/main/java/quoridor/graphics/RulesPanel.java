package quoridor.graphics;

import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class RulesPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static final int BUTTON_POSITION_Y = 200;
    public static final int BUTTON_POSITION_X = 115;
    private final JFrame jFrame;
    private final Color backgroundColor;
    private Font lowerArial;

    private static final int WIDTH_WINDOW = 700;
    private static final int HEIGHT_WINDOW = 700;
    private static final int BUTTON_HEIGHT = 58;
    private static final int BUTTON_WIDTH = 230;
    private transient AudioPlayer[] buttonAudio;

    private transient BufferedImage[] goBackImages;
    private transient BufferedImage backgroundTitle;
    private transient Rectangle2D rectGoBackB;
    private int xButtons;
    private int yButtons;
    private boolean changeB1;


    public RulesPanel(JFrame jFrame, Color backgroundColor) throws IOException, FontFormatException {

        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() throws IOException, FontFormatException {
        addMouseListener(this);
        addMouseMotionListener(this);


        InputStream is1 = getClass().getResourceAsStream("/drawable/font/arlrdbd.ttf");
        lowerArial = Font.createFont(Font.TRUETYPE_FONT, is1);

        setPreferredSize(new Dimension(WIDTH_WINDOW, HEIGHT_WINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        BufferedImageLoader loader = new BufferedImageLoader();

        goBackImages = new BufferedImage[2];

        goBackImages[0] = loader.loadImage("src/main/resources/drawable/images/goBackButton/go_back_button.png");
        goBackImages[1] = loader.loadImage("src/main/resources/drawable/images/goBackButton/go_back_button_hover.png");

        backgroundTitle = loader.loadImage("src/main/resources/drawable/images/background_rules_title/how_to_play_title.png");

        yButtons = HEIGHT_WINDOW / 2 + BUTTON_POSITION_Y;
        xButtons = WIDTH_WINDOW / 2 - BUTTON_POSITION_X;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, BUTTON_WIDTH, BUTTON_HEIGHT);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        JTextPane jTextPane = new JTextPane();
        setJTextPaneParameters(jTextPane);

        JTextPane jTextPaneGoodLuck = new JTextPane();
        setJTextPaneParametersGoodLuck(jTextPaneGoodLuck);

    }

    private void initTimer() {
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
        Graphics2D graphics2D = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.setRenderingHints(rh);


        graphics2D.drawImage(backgroundTitle, WIDTH_WINDOW / 2 - backgroundTitle.getWidth() / 2, 40, null);

        yButtons = HEIGHT_WINDOW / 2 + BUTTON_POSITION_Y;
        xButtons = WIDTH_WINDOW / 2 - BUTTON_POSITION_X;

        if (changeB1) graphics2D.drawImage(goBackImages[1], xButtons, yButtons, null);
        else graphics2D.drawImage(goBackImages[0], xButtons, yButtons, null);


    }

    private void setJTextPaneParameters(JTextPane jTextPane) {
        jTextPane.setBounds(110, 150, 480, 300);
        jTextPane.setText("The object of the game is to advance your pawn to the opposite side of the board. \n\nOn your turn, you may either move your pawn (one square forward, backward, left or right) or place one wall. You may jump over another pawn if it is directly next to you, but you cannot jump over walls! \nYou can obstacle your opponent's path by placing a wall, but you are not allowed to completely block him off."); // showing off
        jTextPane.setBackground(backgroundColor);
        jTextPane.setForeground(Color.decode("#FFFFE1"));
        jTextPane.setFont(lowerArial.deriveFont(Font.BOLD, 21));
        jTextPane.setEditable(false);

        StyledDocument doc = jTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(jTextPane);

    }

    private void setJTextPaneParametersGoodLuck(JTextPane jTextPane) {
        jTextPane.setBounds(xButtons + 40, 460, 500, 50);
        jTextPane.setText("GOOD LUCK!"); // showing off
        jTextPane.setBackground(backgroundColor);
        jTextPane.setForeground(Color.decode("#FFFFE1"));
        jTextPane.setFont(lowerArial.deriveFont(Font.BOLD, 21));
        jTextPane.setEditable(false);
        add(jTextPane);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectGoBackB.contains(x, y)) {
            playSound(1);
            MainPagePanel mainPagePanel = new MainPagePanel(jFrame, backgroundColor);
            jFrame.setContentPane(mainPagePanel);
            jFrame.revalidate();
        }
    }

    private void playSound(int x) {
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

        if (rectGoBackB.contains(x, y)) {
            if (!changeB1) {
                playSound(0);
            }
            changeB1 = true;
        } else changeB1 = false;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        //not needed to use
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //not needed to use
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //not needed to use
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //not needed to use
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        //not needed to use
    }
}
