package quoridor.graphics;

import quoridor.components.Board;
import quoridor.game.GameEngine;
import quoridor.game.Player;
import quoridor.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChooseActionPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private final JFrame jFrame;
    private final Color backgroundColor;

    private final int wallDimension;

    private static final int WIDTHWINDOW = 700;
    private static final int HEIGHTWINDOW = 700;

    private BufferedImage tile;
    private BufferedImage wallV;
    private BufferedImage wallH;
    private BufferedImage pawn1;
    private BufferedImage pawn2;
    private BufferedImage pawn3;
    private BufferedImage pawn4;
    private BufferedImage pawn1Turn;
    private BufferedImage pawn2Turn;
    private BufferedImage pawn3Turn;
    private BufferedImage pawn4Turn;
    private BufferedImage[] moveButtonImage;
    private BufferedImage[] placeWallImage;
    private BufferedImage[] smallGoBackButton;

    private Rectangle2D rectMoveB;
    private Rectangle2D rectPlaceWallB;
    private Rectangle2D rectSmallButton;
    private boolean changeBMove;
    private boolean changeBPlaceWall;
    private boolean changeSmallButton;

    private AudioPlayer[] buttonAudio;

    private final GameEngine gameEngine;
    private Player activePlayer;

    public ChooseActionPanel(JFrame jFrame, Color backgroundColor, int size1, int size2, int numberPlayers, int wallDimension, int numberWalls) throws PositionException, NumberOfPlayerException {
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
        this.wallDimension = wallDimension;

        ArrayList<String> names = new ArrayList<>(); //names just added due to the GameEngine Constructor
        names.add("Player 1");
        names.add("Player 2");
        names.add("Player 3");
        names.add("Player 4");

        gameEngine = new GameEngine(numberPlayers, names, size1, size2, numberWalls, GameType.GRAPHIC_GAME, OpponentType.HUMAN);

        setup();
        initTimer();
    }

    public ChooseActionPanel(JFrame jFrame, GameEngine gameEngine, Color backgroundColor, int wallDimension) {
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
        Font insanib;
        try {
            insanib = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        setFont(insanib.deriveFont(Font.PLAIN, 15));

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
        moveButtonImage = new BufferedImage[2];
        placeWallImage = new BufferedImage[2];
        smallGoBackButton = new BufferedImage[2];

        moveButtonImage[0] = loader.loadImage("src/main/resources/images/moveButtonImages/move_button.png");
        moveButtonImage[1] = loader.loadImage("src/main/resources/images/moveButtonImages/move_button_hover.png");
        placeWallImage[0] = loader.loadImage("src/main/resources/images/placeWallButtonImages/place_wall_button.png");
        placeWallImage[1] = loader.loadImage("src/main/resources/images/placeWallButtonImages/place_wall_button_hover.png");
        smallGoBackButton[0] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button.png");
        smallGoBackButton[1] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button_hover.png");

        int yButtons = 588;
        int xButtons = WIDTHWINDOW / 2 - moveButtonImage[0].getWidth() - 48;
        int heightB = 63;
        int widthB = 202;
        int distance = 100;

        rectMoveB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBMove = false;

        xButtons = WIDTHWINDOW /2 + distance /2;
        rectPlaceWallB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBPlaceWall = false;

        int xSmallButton = 650;
        int ySmallButton = 10;
        int smallHeight = 34;
        int smallWidth = 32;

        rectSmallButton = new Rectangle2D.Float(xSmallButton, ySmallButton, smallWidth, smallHeight);
        changeSmallButton = false;
        ///////////////////////////////////////////////////////////

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        gameEngine.getBoard().placeWall(new Coordinates(3,1), Orientation.HORIZONTAL, 2);
        gameEngine.getBoard().placeWall(new Coordinates(1,1), Orientation.VERTICAL, 2);
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

        g2d.setColor(Color.WHITE);

        if(activePlayer != null) {
            printBoard(g2d);
            printPlayerUI(g2d);
        }
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

        int y = 20;

        for (int i = board.getRows() - 1; i >= 0; i--) {
            int startX = WIDTHWINDOW /2 - (board.getRows() * (tile.getWidth() + 4))/2;
            for(int j = 0; j < board.getColumns(); j++){
                g2d.drawImage(tile, startX, y, null);
                g2d.setColor(new Color(44, 4, 4));
                if(i == 0 && j == 0){
                    g2d.drawString(i + "," + j, startX + 10, y + 25);
                }
                if(i == 0 && j == 1){
                    g2d.drawString(i + "," + j, startX + 10, y + 25);
                }
                if(i == 1 && j == 0){
                    g2d.drawString(i + "," + j, startX + 10, y + 25);
                }
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

    private void printPlayerUI(Graphics2D g2d){
        switch(activePlayer.getMeeple().getColor()){
            case RED -> g2d.drawImage(pawn1Turn, 0, 510, null);
            case BLUE -> g2d.drawImage(pawn2Turn, 0, 510, null);
            case GREEN -> g2d.drawImage(pawn3Turn, 0, 510, null);
            case YELLOW -> g2d.drawImage(pawn4Turn, 0, 510, null);
        }

        int xImages = WIDTHWINDOW /2 - moveButtonImage[0].getWidth() - 50;
        int yImages = 585;

        if(changeBMove) g2d.drawImage(moveButtonImage[1], xImages, yImages, null);
        else g2d.drawImage(moveButtonImage[0], xImages, yImages, null);

        xImages += moveButtonImage[0].getWidth() + 50 + 50;
        if(changeBPlaceWall) g2d.drawImage(placeWallImage[1], xImages, yImages, null);
        else g2d.drawImage(placeWallImage[0], xImages, yImages, null);

        if(changeSmallButton) g2d.drawImage(smallGoBackButton[1], 650, 10, null);
        else g2d.drawImage(smallGoBackButton[0], 650, 10, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectMoveB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            MoveMeeplePanel moveMeeplePanel;
            try {
                moveMeeplePanel = new MoveMeeplePanel(jFrame, gameEngine, backgroundColor, wallDimension);
            } catch (PositionException | NumberOfPlayerException ex) {
                throw new RuntimeException(ex);
            }
            jFrame.setContentPane(moveMeeplePanel);
            jFrame.revalidate();
        }

        if (rectPlaceWallB.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            PlaceWallPanel placeWallPanel;
            placeWallPanel = new PlaceWallPanel(jFrame, gameEngine, backgroundColor, wallDimension);
            jFrame.setContentPane(placeWallPanel);
            jFrame.revalidate();
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

        if (rectMoveB.contains(x, y)) {
            if (!changeBMove) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBMove = true;
        } else changeBMove = false;

        if (rectPlaceWallB.contains(x, y)) {
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
