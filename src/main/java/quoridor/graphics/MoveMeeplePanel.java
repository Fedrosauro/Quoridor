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

public class MoveMeeplePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
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

    private transient BufferedImage[] upArrowImage;
    private transient BufferedImage[] downArrowImage;
    private transient BufferedImage[] leftArrowImage;
    private transient BufferedImage[] rightArrowImage;
    private transient BufferedImage[] smallGoBackButton;

    private transient Rectangle2D rectUpArrow;
    private transient Rectangle2D rectDownArrow;
    private transient Rectangle2D rectLeftArrow;
    private transient Rectangle2D rectRightArrow;
    private transient Rectangle2D rectSmallButton;
    private boolean changeBUpArrow;
    private boolean changeBDownArrow;
    private boolean changeBLeftArrow;
    private boolean changeBRightArrow;
    private boolean changeSmallButton;


    private transient AudioPlayer[] buttonAudio;


    private final transient GameEngine gameEngine;
    private transient Player activePlayer;

    private Font insanIb;


    public MoveMeeplePanel(JFrame jFrame, GameEngine gameEngine, Color backgroundColor, int wallDimension) {
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

        InputStream is = getClass().getResourceAsStream("/drawable/font/Insanibu.ttf");
        try {
            assert is != null;
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
        upArrowImage = new BufferedImage[2];
        downArrowImage = new BufferedImage[2];
        leftArrowImage = new BufferedImage[2];
        rightArrowImage = new BufferedImage[2];
        smallGoBackButton = new BufferedImage[2];

        upArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/uparrow_button.png");
        upArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/uparrow_button_hover.png");
        downArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/downarrow_button.png");
        downArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/downarrow_button_hover.png");
        leftArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/leftarrow_button.png");
        leftArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/leftarrow_button_hover.png");
        rightArrowImage[0] = loader.loadImage("src/main/resources/images/arrowsButtonImages/rightarrow_button.png");
        rightArrowImage[1] = loader.loadImage("src/main/resources/images/arrowsButtonImages/rightarrow_button_hover.png");
        smallGoBackButton[0] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button.png");
        smallGoBackButton[1] = loader.loadImage("src/main/resources/images/goBackButtonSmall/gobackHomeSmall_button_hover.png");

        int yButtons = 580;
        int xButtons = WIDTHWINDOW / 2 - upArrowImage[0].getWidth() * 2 - 23 - 50;
        int heightB = 68;
        int widthB = 68;

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

        setFont(insanIb.deriveFont(Font.PLAIN, 15));
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
        activePlayer = gameEngine.getActivePlayer();
        repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        g2d.setColor(Color.WHITE);

        if (activePlayer != null) {
            printBoard(g2d);

            printPlayerUI(g2d);
        }
    }

    private void printBoard(Graphics2D g2d) {
        Board board = gameEngine.getBoard();

        int sizeBoard = board.getMatrix().length;

        g2d.setColor(new Color(68, 6, 6));
        g2d.fillRoundRect(WIDTHWINDOW / 2 - (board.getRows() * (tile.getWidth() + 4)) / 2 - 10, 10, ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15, 20, 20);

        Stroke defaultStroke = g2d.getStroke();
        BasicStroke strokeForBoardBorder = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, null, 2f);
        g2d.setColor(new Color(96, 10, 10));
        g2d.drawRoundRect(WIDTHWINDOW / 2 - (board.getRows() * (tile.getWidth() + 4)) / 2 - 10, 10, ((pawn1.getWidth() + 4) * sizeBoard) + 15, ((pawn1.getHeight() + 4) * sizeBoard) + 15, 20, 20);

        g2d.setStroke(strokeForBoardBorder);
        g2d.setStroke(defaultStroke);

        g2d.setColor(Color.white);
        int y = 20;

        for (int i = board.getRows() - 1; i >= 0; i--) {
            int startX = WIDTHWINDOW / 2 - (board.getRows() * (tile.getWidth() + 4)) / 2;
            for (int j = 0; j < board.getColumns(); j++) {
                g2d.drawImage(tile, startX, y, null);
                g2d.setColor(new Color(68, 6, 6, 168));
                g2d.drawString(i + "," + j, startX + 10, y + 25);
                if (board.getMatrix()[i][j].getNorthWall() != null) g2d.drawImage(wallH, startX - 2, y - 4, null);
                if (board.getMatrix()[i][j].getEastWall() != null) g2d.drawImage(wallV, startX + 4, y - 2, null);
                for (int k = 0; k < gameEngine.getPlayers().size(); k++) {
                    Coordinates playerCoords = gameEngine.getBoard().getPlayersPositions(gameEngine.getPlayers()).get(k);
                    if (playerCoords.getRow() == i && playerCoords.getColumn() == j) {
                        switch (gameEngine.getPlayers().get(k).getMeeple().getColor()) {
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

    private void printPlayerUI(Graphics2D g2d) {
        switch (activePlayer.getMeeple().getColor()) {
            case RED -> g2d.drawImage(pawn1Turn, 0, 510, null);
            case BLUE -> g2d.drawImage(pawn2Turn, 0, 510, null);
            case GREEN -> g2d.drawImage(pawn3Turn, 0, 510, null);
            case YELLOW -> g2d.drawImage(pawn4Turn, 0, 510, null);
        }

        int xImages = WIDTHWINDOW / 2 - upArrowImage[0].getWidth() * 2 - 25 - 50;
        int yImages = 577;

        if (changeBUpArrow) g2d.drawImage(upArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(upArrowImage[0], xImages, yImages, null);

        xImages += upArrowImage[0].getWidth() + 50;
        if (changeBDownArrow) g2d.drawImage(downArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(downArrowImage[0], xImages, yImages, null);

        xImages += downArrowImage[0].getWidth() + 50;
        if (changeBLeftArrow) g2d.drawImage(leftArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(leftArrowImage[0], xImages, yImages, null);

        xImages += leftArrowImage[0].getWidth() + 50;
        if (changeBRightArrow) g2d.drawImage(rightArrowImage[1], xImages, yImages, null);
        else g2d.drawImage(rightArrowImage[0], xImages, yImages, null);

        if (changeSmallButton) g2d.drawImage(smallGoBackButton[1], 650, 10, null);
        else g2d.drawImage(smallGoBackButton[0], 650, 10, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (rectUpArrow.contains(x, y)) {
            try {
                buttonAudio[1].createAudio();
                buttonAudio[1].playAudio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (gameEngine.moveIsAllowed(activePlayer, Direction.UP)) {
                gameEngine.move(activePlayer, Direction.UP);
                if (gameEngine.didActivePlayerWin()) {
                    WinningPanel winningPanel = new WinningPanel(jFrame, activePlayer, backgroundColor);
                    jFrame.setContentPane(winningPanel);
                    jFrame.revalidate();
                } else {
                    gameEngine.nextActivePlayer();

                    ChooseActionPanel chooseActionPanel;
                    chooseActionPanel = new ChooseActionPanel(jFrame, gameEngine, backgroundColor, wallDimension);
                    jFrame.setContentPane(chooseActionPanel);
                    jFrame.revalidate();
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
                gameEngine.move(activePlayer, Direction.DOWN);
                if (gameEngine.didActivePlayerWin()) {
                    WinningPanel winningPanel = new WinningPanel(jFrame, activePlayer, backgroundColor);
                    jFrame.setContentPane(winningPanel);
                    jFrame.revalidate();
                } else {
                    gameEngine.nextActivePlayer();

                    ChooseActionPanel chooseActionPanel;
                    chooseActionPanel = new ChooseActionPanel(jFrame, gameEngine, backgroundColor, wallDimension);
                    jFrame.setContentPane(chooseActionPanel);
                    jFrame.revalidate();
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
                gameEngine.move(activePlayer, Direction.LEFT);
                if (gameEngine.didActivePlayerWin()) {
                    WinningPanel winningPanel = new WinningPanel(jFrame, activePlayer, backgroundColor);
                    jFrame.setContentPane(winningPanel);
                    jFrame.revalidate();
                } else {
                    gameEngine.nextActivePlayer();

                    ChooseActionPanel chooseActionPanel;
                    chooseActionPanel = new ChooseActionPanel(jFrame, gameEngine, backgroundColor, wallDimension);
                    jFrame.setContentPane(chooseActionPanel);
                    jFrame.revalidate();
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
                gameEngine.move(activePlayer, Direction.RIGHT);
                if (gameEngine.didActivePlayerWin()) {
                    WinningPanel winningPanel = new WinningPanel(jFrame, activePlayer, backgroundColor);
                    jFrame.setContentPane(winningPanel);
                    jFrame.revalidate();
                } else {
                    gameEngine.nextActivePlayer();

                    ChooseActionPanel chooseActionPanel;
                    chooseActionPanel = new ChooseActionPanel(jFrame, gameEngine, backgroundColor, wallDimension);
                    jFrame.setContentPane(chooseActionPanel);
                    jFrame.revalidate();
                }
            }
        }

        if (rectSmallButton.contains(x, y)) {
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

        if (rectSmallButton.contains(x, y)) {
            if (!changeSmallButton) {
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
