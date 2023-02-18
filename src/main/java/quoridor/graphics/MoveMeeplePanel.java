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
import java.util.ArrayList;

public class MoveMeeplePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
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

    private BufferedImage[] moveButtonImage, placeWallImage,
            upArrowImage, downArrowImage, leftArrowImage, rightArrowImage;

    private Rectangle2D rectMoveB, rectPlaceWallB,
            rectUpArrow, rectDownArrow, rectLeftArrow, rectRightArrow;
    private int xButtons, yButtons;
    private int widthB, heightB;
    private int distance;
    private boolean changeBMove, changeBPlaceWall,
            changeBUpArrow, changeBDownArrow, changeBLeftArrow, changeBRightArrow;



    private AudioPlayer[] buttonAudio;



    private GameEngine gameEngine;
    private Player activePlayer;

    private boolean phaseSelectWhatToDo,
            phaseMoveMeeple, phasePlaceWall;

    private boolean thereIsAWinner;


    public MoveMeeplePanel(JFrame jFrame, GameEngine gameEngine, Color backgroundColor) throws PositionException, NumberOfPlayerException {
        this.jFrame = jFrame;
        this.gameEngine = gameEngine;
        this.backgroundColor = backgroundColor;

        setup();
        initTimer();
    }

    private void setup() throws PositionException, NumberOfPlayerException {
        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setBackground(backgroundColor);

        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");

        thereIsAWinner = false;

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
        upArrowImage = new BufferedImage[2];
        downArrowImage = new BufferedImage[2];
        leftArrowImage = new BufferedImage[2];
        rightArrowImage = new BufferedImage[2];

        upArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/uparrow_button.png");
        upArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/uparrow_button_hover.png");
        downArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/downarrow_button.png");
        downArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/downarrow_button_hover.png");
        leftArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/leftarrow_button.png");
        leftArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/leftarrow_button_hover.png");
        rightArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/rightarrow_button.png");
        rightArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/rightarrow_button_hover.png");

        yButtons = 580;
        xButtons = width/2 - upArrowImage[0].getWidth() * 2 - 23 - 50;
        heightB = 68;
        widthB = 68;
        distance = 50;

        rectUpArrow = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBUpArrow = false;

        xButtons += upArrowImage[0].getWidth() + 50;
        rectDownArrow = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBDownArrow = false;

        xButtons += downArrowImage[0].getWidth() + 50;
        rectLeftArrow = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBLeftArrow = false;

        xButtons += leftArrowImage[0].getWidth() + 52;
        rectRightArrow = new Rectangle2D.Float(xButtons, yButtons, widthB, heightB);
        changeBRightArrow = false;
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

        g2d.setColor(Color.white);
        int y = 20;

        for (int i = board.getRows() - 1; i >= 0; i--) {
            int startX = width/2 - (board.getRows() * (tile.getWidth() + 4))/2;
            for(int j = 0; j < board.getColumns(); j++){
                g2d.drawImage(tile, startX, y, null);
                g2d.drawString(i + "," + j, startX + 10, y + 10);
                if(board.getMatrix()[i][j].getNorthWall() != null)
                    g2d.drawImage(wallH, startX, y - 4, null);
                if(board.getMatrix()[i][j].getEastWall() != null)
                    g2d.drawImage(wallV, startX + 4, y, null);
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
        ///////////////////////////////////////////////////////////
        int xImages = width/2 - upArrowImage[0].getWidth() * 2 - 25 - 50;
        int yImages = 577;

        g2d.draw(rectUpArrow);
        if(changeBUpArrow) g2d.drawImage(upArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(upArrowImage[0], xImages, yImages, null);

        g2d.draw(rectDownArrow);
        xImages += upArrowImage[0].getWidth() + 50;
        if(changeBDownArrow) g2d.drawImage(downArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(downArrowImage[0], xImages, yImages, null);

        g2d.draw(rectLeftArrow);
        xImages += downArrowImage[0].getWidth() + 50;
        if(changeBLeftArrow) g2d.drawImage(leftArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(leftArrowImage[0], xImages, yImages, null);

        g2d.draw(rectRightArrow);
        xImages += leftArrowImage[0].getWidth() + 50;
        if(changeBRightArrow) g2d.drawImage(rightArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(rightArrowImage[0], xImages, yImages, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        ///////////CLICKS FOR MOVE MEEPLE PHASE////////////////
        if (rectUpArrow.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (gameEngine.moveIsAllowed(activePlayer, Direction.UP)) {
                gameEngine.doMove(activePlayer, Direction.UP);
                if (gameEngine.didActivePlayerWin()) {
                    thereIsAWinner = true;
                } else {
                    //gameEngine.nextActivePlayer();
                }
            }
        }
        if (rectDownArrow.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (gameEngine.moveIsAllowed(activePlayer, Direction.DOWN)) {
                gameEngine.doMove(activePlayer, Direction.DOWN);
                if (gameEngine.didActivePlayerWin()) {
                    thereIsAWinner = true;
                } else {
                    //gameEngine.nextActivePlayer();
                }
            }
        }
        if (rectLeftArrow.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (gameEngine.moveIsAllowed(activePlayer, Direction.LEFT)) {
                gameEngine.doMove(activePlayer, Direction.LEFT);
                if (gameEngine.didActivePlayerWin()) {
                    thereIsAWinner = true;
                } else {
                    //gameEngine.nextActivePlayer();
                }
            }
        }
        if (rectRightArrow.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (gameEngine.moveIsAllowed(activePlayer, Direction.RIGHT)) {
                gameEngine.doMove(activePlayer, Direction.RIGHT);
                if (gameEngine.didActivePlayerWin()) {
                    thereIsAWinner = true;
                } else {
                    //gameEngine.nextActivePlayer();
                }
            }
        }
        ///////////////////////////////////////////////////////////
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        ///////////////////////////////////////////////////////////
        if (rectUpArrow.contains(x, y)) {
            if (!changeBUpArrow) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBUpArrow = true;
        } else changeBUpArrow = false;

        if (rectDownArrow.contains(x, y)) {
            if (!changeBDownArrow) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBDownArrow = true;
        } else changeBDownArrow = false;

        if (rectLeftArrow.contains(x, y)) {
            if (!changeBLeftArrow) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBLeftArrow = true;
        } else changeBLeftArrow = false;

        if (rectRightArrow.contains(x, y)) {
            if (!changeBRightArrow) {
                try {
                    buttonAudio[0].createAudio();
                    buttonAudio[0].playAudio();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            changeBRightArrow = true;
        } else changeBRightArrow = false;
        ///////////////////////////////////////////////////////////
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
