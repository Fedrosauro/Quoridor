package quoridor.graphics;

import quoridor.utils.AudioPlayer;
import quoridor.utils.BufferedImageLoader;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RulesPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
    private JFrame jFrame;
    private Color backgroundColor;
    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;
    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] goBack_images;
    private BufferedImage backgroundTitle;
    private Image image;

    private Rectangle2D rectGoBackB;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private boolean changeB1;

    private  JList colorList;
    private String[] colors;
    private Map<Color, String> colorMap;


    public RulesPanel(JFrame jFrame, Color backgroundColor){

        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup(){
        addMouseListener((MouseListener) this);
        addMouseMotionListener((MouseMotionListener) this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        loader = new BufferedImageLoader();

        goBack_images = new BufferedImage[2];

        goBack_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        goBack_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        backgroundTitle = loader.loadImage("src/main/resources/images/howToPlayText/how_to_play_text.png");
        image = backgroundTitle.getScaledInstance(400, 350, Image.SCALE_DEFAULT);

        yButtons = height/2 + 200;
        xButtons = width/2 - 115;
        heightB = 58;
        widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeB1 = false;

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

    }

    private void initTimer(){
        timer = new Timer(delay, (ActionListener) this);
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
        Graphics2D graphics2D = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.setRenderingHints(rh);


        graphics2D.drawImage(image, width/2 - backgroundTitle.getWidth()/2, 100, null);

        yButtons = height/2 + 200;
        xButtons = width/2 - 115;

        if(changeB1) graphics2D.drawImage(goBack_images[1], xButtons, yButtons, null);
        else graphics2D.drawImage(goBack_images[0], xButtons, yButtons, null);

    }

    @Override
    public void mouseClicked(MouseEvent e){
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
