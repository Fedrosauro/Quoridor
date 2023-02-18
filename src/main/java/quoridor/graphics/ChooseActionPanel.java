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
    private JFrame jFrame;
    private Color backgroundColor;
    private int size1, size2, numberPlayers, wallDimension, numberWalls;
    private final int width = 700;
    private final int height = 700;
    private final int delay = 1;
    private Timer timer;

    private BufferedImageLoader loader;
    private BufferedImage tile, wallV, wallH,
            pawn1, pawn2, pawn3, pawn4,
            pawn1Turn, pawn2Turn, pawn3Turn, pawn4Turn;
    private BufferedImage[] moveButtonImage, placeWallImage;

    private Rectangle2D rectMoveB, rectPlaceWallB;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private int distance;
    private boolean changeBMove, changeBPlaceWall;

    private AudioPlayer[] buttonAudio;

    private GameEngine gameEngine;
    private Player activePlayer;

    private Font Insanibc, Insanib;

    public ChooseActionPanel(JFrame jFrame, Color backgroundColor, int size1, int size2, int numberPlayers, int wallDimension, int numberWalls) throws PositionException, NumberOfPlayerException {
        this.jFrame = jFrame;
        this.backgroundColor = backgroundColor;
        this.size1 = size1;
        this.size2 = size2;
        this.numberPlayers = numberPlayers;
        this.wallDimension = wallDimension;
        this.numberWalls = numberWalls;

        ArrayList<String> names = new ArrayList<>(); //names just added due to the GameEngine Constructor
        names.add("Player 1");
        names.add("Player 2");
        names.add("Player 3");
        names.add("Player 4");

        gameEngine = new GameEngine(numberPlayers, names, size1, size2, numberWalls, GameType.GRAPHIC_GAME, OpponentType.HUMAN);

        setup();
        initTimer();
    }

    public ChooseActionPanel(JFrame jFrame, GameEngine gameEngine, Color backgroundColor, int wallDimension) throws PositionException, NumberOfPlayerException {
        this.jFrame = jFrame;
        this.gameEngine = gameEngine;
        this.backgroundColor = backgroundColor;
        this.wallDimension = wallDimension;

        setup();
        initTimer();
    }

    private void setup() throws PositionException, NumberOfPlayerException {
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        InputStream is = getClass().getResourceAsStream("/font/Insanibu.ttf");
        try {
            Insanib = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setFont(Insanib.deriveFont(Font.PLAIN, 15));

        loader = new BufferedImageLoader();
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

        moveButtonImage[0] = loader.loadImage("src/main/resources/images/moveButtonImages/move_button.png");
        moveButtonImage[1] = loader.loadImage("src/main/resources/images/moveButtonImages/move_button_hover.png");
        placeWallImage[0] = loader.loadImage("src/main/resources/images/placeWallButtonImages/place_wall_button.png");
        placeWallImage[1] = loader.loadImage("src/main/resources/images/placeWallButtonImages/place_wall_button_hover.png");

        yButtons = 588;
        xButtons = width/2 - moveButtonImage[0].getWidth() - 48;
        heightB = 63;
        widthB = 202;
        distance = 100;

        rectMoveB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBMove = false;

        xButtons = width/2 + distance/2;
        rectPlaceWallB = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBPlaceWall = false;
        ///////////////////////////////////////////////////////////

        buttonAudio = new AudioPlayer[2];
        buttonAudio[0] = new AudioPlayer("src/main/resources/audio/effects/hoverSound.wav");
        buttonAudio[1] = new AudioPlayer("src/main/resources/audio/effects/menuSound.wav");

        gameEngine.getBoard().placeWall(new Coordinates(3,1), Orientation.HORIZONTAL, 2);
        gameEngine.getBoard().placeWall(new Coordinates(1,1), Orientation.VERTICAL, 2);
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
        g2d.fillRoundRect(width/2 - (board.getRows() * (tile.getWidth() + 4))/2 - 10, 10,
                ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15,
                20, 20);

        Stroke defaultStroke = g2d.getStroke();
        BasicStroke strokeForBoardBorder = new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, null, 2f);
        g2d.setColor(new Color(96, 10, 10));
        g2d.drawRoundRect(width/2 - (board.getRows() * (tile.getWidth() + 4))/2 - 10, 10,
                ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15,
                20, 20);

        g2d.setStroke(strokeForBoardBorder);
        g2d.setStroke(defaultStroke);

        int y = 20;

        for (int i = board.getRows() - 1; i >= 0; i--) {
            int startX = width/2 - (board.getRows() * (tile.getWidth() + 4))/2;
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

        int xImages = width/2 - moveButtonImage[0].getWidth() - 50;
        int yImages = 585;

        //g2d.draw(rectMoveB);
        if(changeBMove) g2d.drawImage(moveButtonImage[1], xImages, yImages, null);
        else g2d.drawImage(moveButtonImage[0], xImages, yImages, null);

        //g2d.draw(rectPlaceWallB);
        xImages += moveButtonImage[0].getWidth() + 50 + 50;
        if(changeBPlaceWall) g2d.drawImage(placeWallImage[1], xImages, yImages, null);
        else g2d.drawImage(placeWallImage[0], xImages, yImages, null);
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
            } catch (PositionException ex) {
                throw new RuntimeException(ex);
            } catch (NumberOfPlayerException ex) {
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
