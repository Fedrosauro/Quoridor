package quoridor.graphics;

import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OptionsPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color backgroundColor;
    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;

    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] menuB_images;
    private BufferedImage backgroundTitle;

    private Rectangle2D rectGoBackB;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private boolean changeB1;

    private  JList colorList;
    private String[] colors;
    private Map<Color, String> colorMap;

    public OptionsPanel(JFrame jFrame, Color backgroundColor){
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

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

        menuB_images = new BufferedImage[2];

        menuB_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        menuB_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        backgroundTitle = loader.loadImage("src/main/resources/images/background_chose_title/background_chose_title.png");

        yButtons = height/2 + 200;
        xButtons = width/2 - 115;
        heightB = 58;
        widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        colors = new String[]{"black", "white", "red", "yellow", "cyan", "gray", "green", "pink"};

        colorMap = new HashMap<>();
        colorMap.put(Color.BLACK, "black");
        colorMap.put(Color.WHITE, "white");
        colorMap.put(Color.RED, "red");
        colorMap.put(Color.YELLOW, "yellow");
        colorMap.put(Color.CYAN, "cyan");
        colorMap.put(Color.GRAY, "gray");
        colorMap.put(Color.GREEN, "green");
        colorMap.put(Color.PINK, "pink");

        colorList = new JList(colors);
        setJListParameters(colorMap);

        this.add(colorList);
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

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        try {
            backgroundColor = (Color) Color.class.getField("" + colorList.getSelectedValue()).get(null);
            setBackground(backgroundColor);
            colorList.setBackground(backgroundColor);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        g2d.drawImage(backgroundTitle, width/2 - backgroundTitle.getWidth()/2, 20, null);

        yButtons = height/2 + 200;
        xButtons = width/2 - 115;

        if(changeB1) g2d.drawImage(menuB_images[1], xButtons, yButtons, null);
        else g2d.drawImage(menuB_images[0], xButtons, yButtons, null);
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
    }

    private void setJListParameters(Map<Color, String> colorMap){
        colorList.setBounds(width/2 - 70,160,135,320);
        colorList.setSelectedIndex(Arrays.asList(colors).indexOf(colorMap.get(backgroundColor)));
        colorList.setBackground(backgroundColor);
        colorList.setForeground(Color.white);
        colorList.setSelectionBackground(Color.white);
        colorList.setSelectionForeground(Color.BLACK);
        colorList.setFont(new Font("Calibri", Font.BOLD, 30));
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
