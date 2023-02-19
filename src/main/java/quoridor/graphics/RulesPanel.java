package quoridor.graphics;

import quoridor.utils.AudioPlayer;
import quoridor.utils.BufferedImageLoader;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RulesPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color backgroundColor;
    Font lowerArial, Insanib;

    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;
    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] goBack_images;
    private BufferedImage backgroundTitle;
    private JTextField jTextField;
    private JTextArea jTextArea;
    private JTextPane jTextPane;
    private JTextPane jTextPaneGoodLuck;

    private Image image;

    private Rectangle2D rectGoBackB;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private boolean changeB1;


    public RulesPanel(JFrame jFrame, Color backgroundColor) throws IOException, FontFormatException {

        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() throws IOException, FontFormatException {
        addMouseListener((MouseListener) this);
        addMouseMotionListener((MouseMotionListener) this);

        InputStream is = getClass().getResourceAsStream("/font/Insanibu.ttf");
        Insanib = Font.createFont(Font.TRUETYPE_FONT, is);

        InputStream is1 = getClass().getResourceAsStream("/font/arlrdbd.ttf");
        lowerArial = Font.createFont(Font.TRUETYPE_FONT, is1);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        loader = new BufferedImageLoader();

        goBack_images = new BufferedImage[2];

        goBack_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        goBack_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        backgroundTitle = loader.loadImage("src/main/resources/images/background_rules_title/how_to_play_title.png");

        yButtons = height / 2 + 200;
        xButtons = width / 2 - 115;
        heightB = 58;
        widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        jTextPane = new JTextPane();
        setJTextPaneParameters(jTextPane);

        jTextPaneGoodLuck = new JTextPane();
        setJTextPaneParametersGoodLuck(jTextPaneGoodLuck);

    }

    private void initTimer() {
        timer = new Timer(delay, (ActionListener) this);
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

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.setRenderingHints(rh);


        graphics2D.drawImage(backgroundTitle, width / 2 - backgroundTitle.getWidth() / 2, 40, null);

        yButtons = height / 2 + 200;
        xButtons = width / 2 - 115;

        if (changeB1) graphics2D.drawImage(goBack_images[1], xButtons, yButtons, null);
        else graphics2D.drawImage(goBack_images[0], xButtons, yButtons, null);



    }
    private void setJTextPaneParameters(JTextPane jTextPane){
        jTextPane.setBounds(110, 150, 480, 300);
        jTextPane.setText("The object of the game is to advance your pawn to the opposite side of the board. \n\nOn your turn, you may either move your pawn (one square forward, backward, left or right) or place one wall. You may jump over another pawn if it is directly next to you, but you cannot jump over walls! \nYou can obstacle your opponent's path by placing a wall, but you are not allowed to completely block him off.") ; // showing off
        jTextPane.setBackground(backgroundColor);
        jTextPane.setForeground(Color.decode("#FFFFE1"));
        jTextPane.setFont(lowerArial.deriveFont( Font.BOLD,21));
        jTextPane.setEditable(false);

        StyledDocument doc = jTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(jTextPane);

    }

    private void setJTextPaneParametersGoodLuck(JTextPane jTextPane){
        jTextPane.setBounds(xButtons+40 , 460 , 500, 50);
        jTextPane.setText("GOOD LUCK!") ; // showing off
        jTextPane.setBackground(backgroundColor);
        jTextPane.setForeground(Color.decode("#FFFFE1"));
        jTextPane.setFont(lowerArial.deriveFont( Font.BOLD,21));
        jTextPane.setEditable(false);

        //StyledDocument doc = jTextPane.getStyledDocument();
        //SimpleAttributeSet center = new SimpleAttributeSet();
        //StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        //doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(jTextPane);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectGoBackB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            MainPagePanel mainPagePanel = new MainPagePanel(jFrame, backgroundColor);
            jFrame.setContentPane(mainPagePanel);
            jFrame.revalidate();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectGoBackB.contains(x, y)) {
            if (!changeB1) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB1 = true;
        } else changeB1 = false;
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
