package quoridor.graphics;

import quoridor.utils.AudioPlayer;
import quoridor.utils.BufferedImageLoader;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PrePlayPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color backgroundColor;

    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;
    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] goBack_images;
    private BufferedImage[] play_images;

    //private BufferedImage backgroundTitle;
    private JTextField jTextField;
    //private Image image;

    private JLabel jLabel;

    private Rectangle2D rectGoBackB;
    private Rectangle2D rectPlayB;

    private int xButtonGoBack, yButtonGoBack, xButtonPlay, yButtonPlay;
    private int widthB, heightB;
    private boolean changeB1, changeB2;


    public PrePlayPanel(JFrame jFrame, Color backgroundColor) {
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() {
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        loader = new BufferedImageLoader();

        goBack_images = new BufferedImage[2];

        goBack_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        goBack_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        play_images = new BufferedImage[2];

        play_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        play_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");


        jLabel = new JLabel("How many players?");
        setJLabelParameters(jLabel);


        jTextField = new JTextField();

        yButtonGoBack = height / 2 + 200;
        xButtonGoBack = width / 2 - 300;

        yButtonPlay = height / 2 + 200;
        xButtonPlay = width / 2 + 60;

        heightB = 58;
        widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtonGoBack, yButtonGoBack, widthB, heightB);
        changeB1 = false;

        rectPlayB = new Rectangle2D.Float(xButtonPlay, yButtonPlay, widthB, heightB);
        changeB2 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

    }

    private void initTimer() {
        timer = new Timer(delay, this);
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


        //graphics2D.drawImage(backgroundTitle, width/2 - backgroundTitle.getWidth()/2, 100, null);

        yButtonGoBack = height / 2 + 200;
        xButtonGoBack = width / 2 - 300;

        yButtonPlay = height / 2 + 200;
        xButtonPlay = width / 2 + 60;

        if (changeB1) graphics2D.drawImage(goBack_images[1], xButtonGoBack, yButtonGoBack, null);
        else graphics2D.drawImage(goBack_images[0], xButtonGoBack, yButtonGoBack, null);

        if (changeB2) graphics2D.drawImage(play_images[1], xButtonPlay, yButtonPlay, null);
        else graphics2D.drawImage(play_images[0], xButtonPlay, yButtonPlay, null);

    }


    private void setJLabelParameters(JLabel jLabel) {
        jLabel.setBounds(100,80,300,200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.white);
        jLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        add(jLabel);
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
        if (rectPlayB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            PlayPanel playPanel = new PlayPanel(jFrame, backgroundColor);
            //PlayPanel  playPanel = new PlayPanel(this, backgroundColor, l1, ngiocatori, dimwall, nwall)

            jFrame.setContentPane(playPanel);
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

        if (rectPlayB.contains(x, y)) {
            if (!changeB2) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeB2 = true;
        } else changeB2 = false;


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
