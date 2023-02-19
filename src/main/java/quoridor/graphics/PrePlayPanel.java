package quoridor.graphics;

import quoridor.exceptions.NumberOfPlayerException;
import quoridor.exceptions.PositionException;
import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;


public class PrePlayPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final JFrame jFrame;
    private final Color backgroundColor;
    private Font insanIb;

    private static final int WIDTHWINDOW = 700;
    private static final int HEIGHTWINDOW = 700;
    private transient AudioPlayer[] buttonAudio;

    private transient BufferedImage[] goBackImages;
    private transient BufferedImage[] playImages;

    private transient BufferedImage backgroundTitle;

    private JSpinner jSpinner1;
    private JSpinner jSpinner2;
    private JSpinner jSpinner3;

    private ButtonGroup buttonGroup;

    private transient Rectangle2D rectGoBackB;
    private transient Rectangle2D rectPlayB;

    private int xButtonGoBack;
    private int yButtonGoBack;
    private int xButtonPlay;
    private int yButtonPlay;
    private boolean changeB1;
    private boolean changeB2;


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
        insanIb = Font.createFont(Font.TRUETYPE_FONT, is);


        setPreferredSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        BufferedImageLoader loader = new BufferedImageLoader();

        backgroundTitle = loader.loadImage("src/main/resources/images/game_settings_title/game_settings_title.png");

        goBackImages = new BufferedImage[2];

        goBackImages[0] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button.png");
        goBackImages[1] = loader.loadImage("src/main/resources/images/goBackButton/go_back_button_hover.png");

        playImages = new BufferedImage[2];

        playImages[0] = loader.loadImage("src/main/resources/images/prePlayButton/prePlay_button.png");
        playImages[1] = loader.loadImage("src/main/resources/images/prePlayButton/prePlay_button_hover.png");


        JLabel jLabelPlayers = new JLabel("Enter number of players: ");
        setJLabelParameters1(jLabelPlayers);

        JRadioButton jRadioButton1 = new JRadioButton();
        setJRadioButton1Parameters(jRadioButton1);

        JRadioButton jRadioButton2 = new JRadioButton();
        setJRadioButton2Parameters(jRadioButton2);

        buttonGroup = new ButtonGroup(); //for setting button exclusive
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);


        JLabel jLabelWalls = new JLabel("Enter number of total walls:");
        setJLabelParameters2(jLabelWalls);

        JLabel jLabelDimWalls = new JLabel("Enter dimension of wall: ");
        setJLabelParameters3(jLabelDimWalls);

        JLabel jLabelDimBoard = new JLabel("Enter board dimension: ");
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


        yButtonGoBack = HEIGHTWINDOW / 2 + 200;
        xButtonGoBack = WIDTHWINDOW / 2 - 300;

        yButtonPlay = HEIGHTWINDOW / 2 + 200;
        xButtonPlay = WIDTHWINDOW / 2 + 60;

        int heightB = 58;
        int widthB = 230;

        rectGoBackB = new Rectangle2D.Float(xButtonGoBack, yButtonGoBack, widthB, heightB);
        changeB1 = false;

        rectPlayB = new Rectangle2D.Float(xButtonPlay, yButtonPlay, widthB, heightB);
        changeB2 = false;

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
        Graphics2D graphics2D = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.setRenderingHints(rh);


        graphics2D.drawImage(backgroundTitle, WIDTHWINDOW / 2 - backgroundTitle.getWidth() / 2, 40, null);

        yButtonGoBack = HEIGHTWINDOW / 2 + 200;
        xButtonGoBack = WIDTHWINDOW / 2 - 300;

        yButtonPlay = HEIGHTWINDOW / 2 + 200;
        xButtonPlay = WIDTHWINDOW / 2 + 60;

        if (changeB1) graphics2D.drawImage(goBackImages[1], xButtonGoBack, yButtonGoBack, null);
        else graphics2D.drawImage(goBackImages[0], xButtonGoBack, yButtonGoBack, null);

        if (changeB2) graphics2D.drawImage(playImages[1], xButtonPlay, yButtonPlay, null);
        else graphics2D.drawImage(playImages[0], xButtonPlay, yButtonPlay, null);


    }

    private void setJRadioButton1Parameters(JRadioButton jRadioButton) {
        jRadioButton.setBounds(510, 193, 50, 50);
        jRadioButton.setText("2");
        jRadioButton.setSelected(true);
        jRadioButton.setBackground(backgroundColor);
        jRadioButton.setForeground(Color.decode("#FFFFE1"));
        jRadioButton.setFont(insanIb.deriveFont(Font.PLAIN, 28));
        add(jRadioButton);
    }

    private void setJRadioButton2Parameters(JRadioButton jRadioButton) {
        jRadioButton.setBounds(560, 193, 50, 50);
        jRadioButton.setText("4");
        jRadioButton.setBackground(backgroundColor);
        jRadioButton.setForeground(Color.decode("#FFFFE1"));
        jRadioButton.setFont(insanIb.deriveFont(Font.PLAIN, 28));
        add(jRadioButton);
    }

    private void setJLabelParameters1(JLabel jLabel) {
        jLabel.setBounds(80, 120, 350, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(insanIb.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters2(JLabel jLabel) {
        jLabel.setBounds(80, 180, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(insanIb.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters3(JLabel jLabel) {
        jLabel.setBounds(80, 240, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(insanIb.deriveFont(Font.PLAIN, 28));
        add(jLabel);
    }

    private void setJLabelParameters4(JLabel jLabel) {
        jLabel.setBounds(80, 300, 500, 200);
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(insanIb.deriveFont(Font.PLAIN, 28));
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
            if (button.isSelected() && button.getText().equals("4")) {
                    return 4;
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

                ChooseActionPanel chooseActionPanel = null;
                try {
                    chooseActionPanel = new ChooseActionPanel(jFrame, backgroundColor, (Integer) jSpinner3.getValue(), (Integer) jSpinner3.getValue(),
                                                        radioButtonSelection(buttonGroup), (Integer) jSpinner2.getValue(), (Integer) jSpinner1.getValue());
                } catch (PositionException | NumberOfPlayerException ex) {
                    throw new RuntimeException(ex);
                }

                jFrame.setContentPane(chooseActionPanel);
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
            //not needed to use
        }

        @Override
        public void mouseReleased (MouseEvent mouseEvent){
            //not needed to use
        }

        @Override
        public void mouseEntered (MouseEvent mouseEvent){
            //not needed to use
        }

        @Override
        public void mouseExited (MouseEvent mouseEvent){
            //not needed to use
        }

        @Override
        public void mouseDragged (MouseEvent mouseEvent){
            //not needed to use
        }
    }
