package quoridor.graphics;

import quoridor.components.Board;
import quoridor.game.GameEngine;
import quoridor.game.Player;
import quoridor.media.AudioPlayer;
import quoridor.media.BufferedImageLoader;
import quoridor.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class PlaceWallPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private final JFrame jFrame;
    private final Color backgroundColor;

    private final int wallDimension;

    private static final int WIDTHWINDOW = 700;
    private static final int HEIGHTWINDOW = 700;

    private transient BufferedImage tile;
    private transient BufferedImage wallV;
    private transient BufferedImage wallH;
    private transient BufferedImage pawn1;
    private transient BufferedImage pawn2;
    private transient BufferedImage pawn3;
    private transient BufferedImage pawn4;
    private transient BufferedImage pawn1Turn;
    private transient BufferedImage pawn2Turn;
    private transient BufferedImage pawn3Turn;
    private transient BufferedImage pawn4Turn;

    private transient BufferedImage[] placeWallButton;
    private transient BufferedImage[] smallGoBackButton;

    private transient Rectangle2D rectPlaceWall;
    private transient Rectangle2D rectSmallButton;
    private boolean changeBPlaceWall;
    private boolean changeSmallButton;

    private transient AudioPlayer[] buttonAudio;

    private Font insanIb;

    private JSpinner jSpinner1;
    private JSpinner jSpinner2;
    private ButtonGroup buttonGroup;

    private final transient GameEngine gameEngine;
    private transient Player activePlayer;
    public PlaceWallPanel(JFrame jFrame, GameEngine gameEngine, Color backgroundColor, int wallDimension) {
        this.jFrame = jFrame;
        this.gameEngine = gameEngine;
        this.backgroundColor = backgroundColor;
        this.wallDimension = wallDimension;

        setup();
        initTimer();
    }

    private void setup() {
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(WIDTHWINDOW, HEIGHTWINDOW));
        setLayout(null);
        setBackground(backgroundColor);

        InputStream is = getClass().getResourceAsStream("/font/Insanibu.ttf");
        try {
            insanIb = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        BufferedImageLoader loader = new BufferedImageLoader();
        ///////////////////////////////////////////////////////////
        tile = loader.loadImage("src/main/resources/images/tile/tile.png");
        wallH = loader.loadImage("src/main/resources/images/wallsImages/wallH.png");
        wallV = loader.loadImage("src/main/resources/images/wallsImages/wallV.png");
        pawn1 = loader.loadImage("src/main/resources/images/meepleImages/pawn1.png");
        pawn2 = loader.loadImage("src/main/resources/images/meepleImages/pawn2.png");
        pawn3 = loader.loadImage("src/main/resources/images/meepleImages/pawn3.png");
        pawn4 = loader.loadImage("src/main/resources/images/meepleImages/pawn4.png");
        pawn1Turn = loader.loadImage("src/main/resources/images/playersTurnImages/pawn1turn.png");
        pawn2Turn = loader.loadImage("src/main/resources/images/playersTurnImages/pawn2turn.png");
        pawn3Turn = loader.loadImage("src/main/resources/images/playersTurnImages/pawn3turn.png");
        pawn4Turn = loader.loadImage("src/main/resources/images/playersTurnImages/pawn4turn.png");
        ///////////////////////////////////////////////////////////
        placeWallButton = new BufferedImage[2];
        smallGoBackButton = new BufferedImage[2];

        placeWallButton[0] = loader.loadImage("src/main/resources/images/placeActualWallButton/placeActualWall_button.png");
        placeWallButton[1] = loader.loadImage("src/main/resources/images/placeActualWallButton/placeActualWall_button_hover.png");
        smallGoBackButton[0] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button.png");
        smallGoBackButton[1] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button_hover.png");

        int yButtons = 580;
        int xButtons = 40;
        int heightB = 85;
        int widthB = 168;

        rectPlaceWall = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBPlaceWall = false;

        int xSmallButton = 650;
        int ySmallButton = 10;
        int smallHeight = 34;
        int smallWidth = 32;

        rectSmallButton = new Rectangle2D.Float(xSmallButton, ySmallButton, smallWidth, smallHeight);
        changeSmallButton = false;
        ///////////////////////////////////////////////////////////

        int yStart = 530;
        int xCoord = 240;

        JLabel jLabelXCoord = new JLabel("Select X coordinate:");
        jLabelXCoord.setBounds(xCoord, yStart, 270, 50);
        setJLabelParameters(jLabelXCoord);

        SpinnerModel value1 = new SpinnerNumberModel(0, 0, gameEngine.getBoard().getColumns()-1, 1);
        jSpinner1 = new JSpinner(value1);
        jSpinner1.setEditor(new JSpinner.DefaultEditor(jSpinner1));
        jSpinner1.setBounds(xCoord + jLabelXCoord.getWidth(), yStart + 10, 40, 30);
        setJSpinnerParameters(jSpinner1);

        JLabel jLabelYCoord = new JLabel("Select Y coordinate:");
        yStart += 45;
        jLabelYCoord.setBounds(xCoord, yStart, 270, 50);
        setJLabelParameters(jLabelYCoord);

        SpinnerModel value2 = new SpinnerNumberModel(0, 0, gameEngine.getBoard().getColumns()-1, 1);
        jSpinner2 = new JSpinner(value2);
        jSpinner2.setEditor(new JSpinner.DefaultEditor(jSpinner2));
        jSpinner2.setBounds(xCoord + jLabelYCoord.getWidth(), yStart + 10, 40, 30);
        setJSpinnerParameters(jSpinner2);

        yStart += 45;
        JLabel jLabelOrientation = new JLabel("Select Orientation: ");
        jLabelOrientation.setBounds(xCoord, yStart, 250, 50);
        setJLabelParameters(jLabelOrientation);

        JRadioButton jRadioButton1 = new JRadioButton();
        setJRadioButtonParameters(jRadioButton1);
        jRadioButton1.setBounds(xCoord + jLabelOrientation.getWidth(), yStart, 50, 50);
        jRadioButton1.setText("V");
        jRadioButton1.setForeground(Color.RED);
        jRadioButton1.setSelected(true);

        JRadioButton jRadioButton2 = new JRadioButton();
        setJRadioButtonParameters(jRadioButton2);
        jRadioButton2.setBounds(xCoord + jLabelOrientation.getWidth() + 60, yStart, 50, 50);
        jRadioButton2.setText("H");
        jRadioButton2.setForeground(Color.GREEN);


        buttonGroup = new ButtonGroup(); //for setting button exclusive
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        buttonGroup.getElements().nextElement().setEnabled(true);

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        setFont(insanIb.deriveFont(Font.PLAIN, 15));
    }

    private void initTimer(){
        int delay = 1;
        Timer timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        activePlayer = gameEngine.getActivePlayer();
        repaint();
    }

    private void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        if(activePlayer != null) {
            printBoard(g2d);

            printPlayerUI(g2d);
        }
    }

    private void printPlayerUI(Graphics2D g2d){
        switch(activePlayer.getMeeple().getColor()){
            case RED -> g2d.drawImage(pawn1Turn, 0, 510, null);
            case BLUE -> g2d.drawImage(pawn2Turn, 0, 510, null);
            case GREEN -> g2d.drawImage(pawn3Turn, 0, 510, null);
            case YELLOW -> g2d.drawImage(pawn4Turn, 0, 510, null);
        }

        int xImages = 35;
        int yImages = 577;

        if(changeBPlaceWall) g2d.drawImage(placeWallButton[1], xImages, yImages, null);
        else g2d.drawImage(placeWallButton[0], xImages, yImages, null);

        if(changeSmallButton) g2d.drawImage(smallGoBackButton[1], 650, 10, null);
        else g2d.drawImage(smallGoBackButton[0], 650, 10, null);
    }

    private void setJLabelParameters(JLabel jLabel) {
        jLabel.setBackground(backgroundColor);
        jLabel.setForeground(Color.decode("#FFFFE1"));
        jLabel.setFont(insanIb.deriveFont(Font.PLAIN, 22));
        add(jLabel);
    }

    private void setJSpinnerParameters(JSpinner jSpinner) {
        jSpinner.setBackground(backgroundColor);
        jSpinner.setForeground(Color.white);
        add(jSpinner);
    }

    private void setJRadioButtonParameters(JRadioButton jRadioButton){
        jRadioButton.setBackground(backgroundColor);
        jRadioButton.setFont(insanIb.deriveFont(Font.PLAIN, 22));
        add(jRadioButton);
    }

    private Orientation radioButtonSelection(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected() && button.getText().equals("H")) {
                    return Orientation.HORIZONTAL;
                }
        }return Orientation.VERTICAL;
    }

    private void printBoard(Graphics2D g2d){
        Board board = gameEngine.getBoard();

        int sizeBoard = board.getMatrix().length;

        g2d.setColor(new Color(68, 6, 6));
        g2d.fillRoundRect(WIDTHWINDOW /2 - (board.getRows() * (tile.getWidth() + 4))/2 - 10, 10,
                ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15,
                20, 20);

        Stroke defaultStroke = g2d.getStroke();
        BasicStroke strokeForBoardBorder = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 2f);
        g2d.setColor(new Color(96, 10, 10));
        g2d.drawRoundRect(WIDTHWINDOW /2 - (board.getRows() * (tile.getWidth() + 4))/2 - 10, 10,
                ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15,
                20, 20);

        g2d.setStroke(strokeForBoardBorder);
        g2d.setStroke(defaultStroke);

        g2d.setColor(Color.white);
        int y = 20;

        for (int i = board.getRows() - 1; i >= 0; i--) {
            int startX = WIDTHWINDOW /2 - (board.getRows() * (tile.getWidth() + 4))/2;
            for(int j = 0; j < board.getColumns(); j++){
                g2d.drawImage(tile, startX, y, null);
                g2d.setColor(new Color(68, 6, 6, 168));
                g2d.drawString(i + "," + j, startX + 10, y + 25);
                if(board.getMatrix()[i][j].getNorthWall() != null)
                    g2d.drawImage(wallH, startX - 2, y - 4, null);
                if(board.getMatrix()[i][j].getEastWall() != null)
                    g2d.drawImage(wallV, startX + 4, y - 2, null);
                for(int k = 0; k < gameEngine.getPlayers().size(); k++){
                    Coordinates playerCoords = gameEngine.getBoard().getPlayersPositions(gameEngine.getPlayers()).get(k);
                    if(playerCoords.getRow() == i && playerCoords.getColumn() == j){
                        switch(gameEngine.getPlayers().get(k).getMeeple().getColor()){
                            case RED -> g2d.drawImage(pawn1, startX, y, null);
                            case BLUE -> g2d.drawImage(pawn2, startX, y, null);
                            case GREEN -> g2d.drawImage(pawn3, startX, y, null);
                            case YELLOW -> g2d.drawImage(pawn4, startX, y, null);
                        }
                    }
                }
                startX += tile.getHeight() + 4;
            }
            y += tile.getWidth() + 4;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectPlaceWall.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Coordinates position = new Coordinates((int)jSpinner1.getValue(), (int)jSpinner2.getValue());
            Orientation orientation = radioButtonSelection(buttonGroup);

            if (gameEngine.placementIsAllowed(activePlayer, position, orientation, wallDimension)) {
                gameEngine.placeWall(activePlayer, position, orientation, wallDimension);

                gameEngine.nextActivePlayer();

                ChooseActionPanel chooseActionPanel;
                chooseActionPanel = new ChooseActionPanel(jFrame, gameEngine, backgroundColor, wallDimension);
                jFrame.setContentPane(chooseActionPanel);
                jFrame.revalidate();
            }
        }

        if(rectSmallButton.contains(x, y)){
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

        if (rectPlaceWall.contains(x, y)) {
            if (!changeBPlaceWall) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBPlaceWall = true;
        } else changeBPlaceWall = false;

        if (rectSmallButton.contains(x, y)) {
            if(!changeSmallButton){
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeSmallButton = true;
        } else changeSmallButton = false;
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
