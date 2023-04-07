package quoridor.graphics;

import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OptionsPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    public static final int Y_DELAY_BUTTON = 200;
    public static final int X_DELAY_BUTTON = 115;
    private final JFrame jFrame;
    private Color backgroundColor;
    private static final int WIDTH_WINDOW = 700;
    private static final int HEIGHT_WINDOW = 700;

    private transient AudioPlayer[] buttonAudio;

    private transient BufferedImage[] menuBImages;
    private transient BufferedImage backgroundTitle;

    private transient Rectangle2D rectGoBackB;
    private int xButtons;
    private int yButtons;
    private boolean changeB1;

    private JList<String> colorList;
    private String[] colors;

    private Font insanIb;

    public OptionsPanel(JFrame jFrame, Color backgroundColor) {
        this.jFrame = jFrame;
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

        backgroundTitle = loader.loadImage("src/main/resources/drawable/images/background_chose_title/background_chose_title.png");

        yButtons = HEIGHT_WINDOW / 2 + Y_DELAY_BUTTON;
        xButtons = WIDTH_WINDOW / 2 - X_DELAY_BUTTON;
        int heightB = 58;
        int widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        colors = new String[]{"black", "red", "yellow", "cyan", "gray", "green", "pink"};

        Map<Color, String> colorMap = new HashMap<>();
        colorMap.put(Color.BLACK, "black");
        colorMap.put(Color.RED, "red");
        colorMap.put(Color.YELLOW, "yellow");
        colorMap.put(Color.CYAN, "cyan");
        colorMap.put(Color.GRAY, "gray");
        colorMap.put(Color.GREEN, "green");
        colorMap.put(Color.PINK, "pink");

        colorList = new JList<>(colors);
        setJListParameters(colorMap);

        this.add(colorList);
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

        try {
            backgroundColor = (Color) Color.class.getField("" + colorList.getSelectedValue()).get(null);
            setBackground(backgroundColor);
            colorList.setBackground(backgroundColor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        g2d.drawImage(backgroundTitle, WIDTH_WINDOW / 2 - backgroundTitle.getWidth() / 2, 20, null);

        yButtons = HEIGHT_WINDOW / 2 + Y_DELAY_BUTTON;
        xButtons = WIDTH_WINDOW / 2 - X_DELAY_BUTTON;

        if (changeB1) g2d.drawImage(menuBImages[1], xButtons, yButtons, null);
        else g2d.drawImage(menuBImages[0], xButtons, yButtons, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectGoBackB.contains(x, y)) {

            playAudio(1);
            MainPagePanel mainPagePanel = new MainPagePanel(jFrame, backgroundColor);
            jFrame.setContentPane(mainPagePanel);
            jFrame.revalidate();
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

        if (rectGoBackB.contains(x, y)) {
            if (!changeB1) {
                playAudio(0);
            }
            changeB1 = true;
        } else changeB1 = false;
    }

    private void setJListParameters(Map<Color, String> colorMap) {
        colorList.setBounds(WIDTH_WINDOW / 2 - 70, 160, 130, 320);
        colorList.setSelectedIndex(Arrays.asList(colors).indexOf(colorMap.get(backgroundColor)));
        colorList.setBackground(backgroundColor);
        colorList.setForeground(Color.white);
        colorList.setSelectionBackground(Color.white);
        colorList.setSelectionForeground(Color.BLACK);
        colorList.setFont(insanIb.deriveFont(Font.PLAIN, 30));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) colorList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
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
