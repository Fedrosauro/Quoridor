package quoridor.graphics;

import quoridor.game.Player;
import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class WinningPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final JFrame jFrame;
    private final Color backgroundColor;
    private final transient Player winnerPlayer;
    private static final int WIDTH_WINDOW = 700;
    private static final int HEIGHT_WINDOW = 700;

    private transient BufferedImage[] menuBImages;
    private transient BufferedImage pawn1;
    private transient BufferedImage pawn2;
    private transient BufferedImage pawn3;
    private transient BufferedImage pawn4;

    private transient Rectangle2D rectGoBackB;
    private int xButtons;
    private int yButtons;
    private boolean changeB1;

    private transient AudioPlayer[] buttonAudio;

    private Font insanIb;


    public WinningPanel(JFrame jFrame, Player player, Color backgroundColor) {
        this.jFrame = jFrame;
        this.winnerPlayer = player;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() {
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(WIDTH_WINDOW, HEIGHT_WINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        InputStream is = getClass().getResourceAsStream("/drawable/font/Insanibu.ttf");
        try {
            insanIb = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        BufferedImageLoader loader = new BufferedImageLoader();

        menuBImages = new BufferedImage[2];

        menuBImages[0] = loader.loadImage("src/main/resources/drawable/images/goBackButton/go_back_button.png");
        menuBImages[1] = loader.loadImage("src/main/resources/drawable/images/goBackButton/go_back_button_hover.png");

        pawn1 = loader.loadImage("src/main/resources/drawable/images/meepleImages/pawn1.png");
        pawn2 = loader.loadImage("src/main/resources/drawable/images/meepleImages/pawn2.png");
        pawn3 = loader.loadImage("src/main/resources/drawable/images/meepleImages/pawn3.png");
        pawn4 = loader.loadImage("src/main/resources/drawable/images/meepleImages/pawn4.png");

        yButtons = HEIGHT_WINDOW / 2 + 200;
        xButtons = WIDTH_WINDOW / 2 - 115;
        int heightB = 58;
        int widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");
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
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        int startX = 220;
        int y = HEIGHT_WINDOW / 2 - 100;

        switch (winnerPlayer.getMeeple().getColor()) {
            case RED -> g2d.drawImage(pawn1, startX, y, null);
            case BLUE -> g2d.drawImage(pawn2, startX, y, null);
            case GREEN -> g2d.drawImage(pawn3, startX, y, null);
            case YELLOW -> g2d.drawImage(pawn4, startX, y, null);
        }

        Color yellowNaples = new Color(255, 255, 225);
        g2d.setColor(yellowNaples);
        g2d.setFont(insanIb.deriveFont(Font.PLAIN, 30));
        g2d.drawString("is the WINNER!!!", startX + pawn1.getWidth() + 13, y + pawn1.getHeight() - 10);

        yButtons = HEIGHT_WINDOW / 2 + 200;
        xButtons = WIDTH_WINDOW / 2 - 115;

        if (changeB1) g2d.drawImage(menuBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(menuBImages[0], xButtons, yButtons, null);
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
