package quoridor.graphics;

import quoridor.utils.AudioPlayer;
import quoridor.utils.BufferedImageLoader;
import quoridor.utils.OpponentType;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;


public class PrePlayPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private JFrame jFrame;
    private Color backgroundColor;
    Font Insanibc, Insanib;

    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;
    private AudioPlayer[] buttonAudio;

    private BufferedImageLoader loader;
    private BufferedImage[] goBack_images;
    private BufferedImage[] play_images;

    private BufferedImage backgroundTitle;
    private JTextField jTextField;

    private JLabel jLabelPlayers;
    private JLabel jLabelWalls;
    private JLabel jLabelDimWalls;
    private JLabel jLabelDimBoard;

    private JLabel jLabelPlayAgainst;
    private JSpinner jSpinner1;
    private JSpinner jSpinner2;
    private JSpinner jSpinner3;

    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JRadioButton jRadioButton3;
    private JRadioButton jRadioButton4;
    private ButtonGroup buttonGroup;
    private ButtonGroup buttonGroup1;


    private int size1;
    private int size2;
    private int numberPlayers;
    private int wallDimension;
    private int numberWalls;


    private Rectangle2D rectGoBackB;
    private Rectangle2D rectPlayB;

    private int xButtonGoBack, yButtonGoBack, xButtonPlay, yButtonPlay;
    private int widthB, heightB;
    private boolean changeB1, changeB2;


    public PrePlayPanel(JFrame jFrame, Color backgroundColor) throws IOException, FontFormatException {
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() throws IOException, FontFormatException {
        addMouseListener(this);
        addMouseMotionListener(this);

        InputStream is = getClass().getResourceAsStream("/font/Insanibu.ttf");
        Insanib = Font.createFont(Font.TRUETYPE_FONT, is);


        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        loader = new BufferedImageLoader();

        backgroundTitle = loader.loadImage("src/main/resources/images/game_settings_title/game_settings_title.png");

        goBack_images = new BufferedImage[2];

        goBack_images[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        goBack_images[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        play_images = new BufferedImage[2];

        play_images[0] = loader.loadImage("src/main/resources/images/prePlayButton/prePlay_button.png");
        play_images[1] = loader.loadImage("src/main/resources/images/prePlayButton/prePlay_button_hover.png");


        jLabelPlayers = new JLabel("Enter number of players: ");
        setJLabelParameters1(jLabelPlayers);

        jRadioButton1 = new JRadioButton();
        setJRadioButton1Parameters(jRadioButton1);

        jRadioButton2 = new JRadioButton();
        setJRadioButton2Parameters(jRadioButton2);

        buttonGroup = new ButtonGroup(); //for setting button exclusive
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);


        jLabelWalls = new JLabel("Enter number of total walls:");
        setJLabelParameters2(jLabelWalls);

        jLabelDimWalls = new JLabel("Enter dimension of wall: ");
        setJLabelParameters3(jLabelDimWalls);

        jLabelDimBoard = new JLabel("Enter board dimension: ");
        setJLabelParameters4(jLabelDimBoard);

        SpinnerModel value1 = new SpinnerNumberModel(20, 6, 20, 1);
        jSpinner1 = new JSpinner(value1);
        jSpinner1.setEditor(new JSpinner.DefaultEditor(jSpinner1));
        setJSpinnerParameters1(jSpinner1);

        SpinnerModel value2 = new SpinnerNumberModel(2, 1, 4, 1);
        jSpinner2 = new JSpinner(value2);
        jSpinner2.setEditor(new JSpinner.DefaultEditor(jSpinner2));
        setJSpinnerParameters2(jSpinner2);

        SpinnerModel value4 = new SpinnerNumberModel(9, 5, 11, 1);
        jSpinner3 = new JSpinner(value4);
        jSpinner3.setEditor(new JSpinner.DefaultEditor(jSpinner3));
        setJSpinnerParameters3(jSpinner3);

        //jSpinner.addChangeListener(new ChangeListener() )


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


        graphics2D.drawImage(backgroundTitle, width / 2 - backgroundTitle.getWidth() / 2, 40, null);

        yButtonGoBack = height / 2 + 200;
        xButtonGoBack = width / 2 - 300;

        yButtonPlay = height / 2 + 200;
        xButtonPlay = width / 2 + 60;

        if (changeB1) graphics2D.drawImage(goBack_images[1], xButtonGoBack, yButtonGoBack, null);
        else graphics2D.drawImage(goBack_images[0], xButtonGoBack, yButtonGoBack, null);

        if (changeB2) graphics2D.drawImage(play_images[1], xButtonPlay, yButtonPlay, null);
        else graphics2D.drawImage(play_images[0], xButtonPlay, yButtonPlay, null);


    }

    private void setJRadioButton1Parameters(JRadioButton jRadioButton) {
        jRadioButton.setBounds(510, 193, 50, 50);
        jRadioButton.setText("2");
        jRadioButton.setSelected(true);
        jRadioButton.setBackground(backgroundColor);
        jRadioButton.setForeground(Color.decode("#FFFFE1"));
        jRadioButton.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jRadioButton);
    }

    private void setJRadioButton2Parameters(JRadioButton jRadioButton) {
        jRadioButton.setBounds(560, 193, 50, 50);
        jRadioButton.setText("4");
        jRadioButton.setBackground(backgroundColor);
        jRadioButton.setForeground(Color.decode("#FFFFE1"));
        jRadioButton.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jRadioButton);
    }

    private void setJLabelParameters1(JLabel jLabel) {
        jLabel.setBounds(80, 120, 350, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters2(JLabel jLabel) {
        jLabel.setBounds(80, 180, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters3(JLabel jLabel) {
        jLabel.setBounds(80, 240, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters4(JLabel jLabel) {
        jLabel.setBounds(80, 300, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(Insanib.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }



    private void setJSpinnerParameters1(JSpinner jSpinner) {
        jSpinner.setBackground(backgroundColor);
        jSpinner.setForeground(Color.white);
        jSpinner.setBounds(525, 265, 40, 30);
        add(jSpinner);
    }

    private void setJSpinnerParameters2(JSpinner jSpinner) {
        jSpinner.setBackground(backgroundColor);
        jSpinner.setForeground(Color.white);
        jSpinner.setBounds(525, 325, 40, 30);
        setBackground(backgroundColor);

        add(jSpinner);
    }

    private void setJSpinnerParameters3(JSpinner jSpinner) {
        jSpinner.setBackground(backgroundColor);
        jSpinner.setForeground(Color.white);
        jSpinner.setBounds(525, 385, 40, 30);
        add(jSpinner);
    }


    private int radioButtonSelection(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                if (button.getText() == "4") {
                    return 4;
                }
            }
        }return 2;
    }


        @Override
        public void mouseClicked (MouseEvent e){
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
                PlayPanel playPanel = new PlayPanel(jFrame, backgroundColor, (Integer) jSpinner3.getValue(), (Integer) jSpinner3.getValue(),
                                                    radioButtonSelection(buttonGroup), (Integer) jSpinner2.getValue(),
                                                    (Integer) jSpinner1.getValue() );

                jFrame.setContentPane(playPanel);
                jFrame.revalidate();
            }
        }

        @Override
        public void mouseMoved (MouseEvent e){
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
        public void mousePressed (MouseEvent mouseEvent){

        }

        @Override
        public void mouseReleased (MouseEvent mouseEvent){

        }

        @Override
        public void mouseEntered (MouseEvent mouseEvent){

        }

        @Override
        public void mouseExited (MouseEvent mouseEvent){

        }

        @Override
        public void mouseDragged (MouseEvent mouseEvent){

        }


    }
