package quoridor;

import org.junit.jupiter.api.Test;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.exceptions.PositionException;
import quoridor.game.GameEngine;
import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GameFlowTest {


    @Test
    public void moveMeeple() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.LEFT), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.RIGHT), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.move(players.get(0), Direction.UP);

        assertSame(board.getPosition(1, 0), players.get(0).getMeeple().getPosition());

    }

    @Test
    public void placeWall() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/

        gameEngine.placeWall(players.get(0), new Coordinates(2, 2), Orientation.HORIZONTAL, 2);

        assertFalse(board.isWallNotPresent(new Coordinates(2, 2), Orientation.HORIZONTAL, 2));
    }

    //non funziona fixa
    @Test
    public void moveMeepleUpWhenWallIsPlaced() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 1), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        gameEngine.placeWall(players.get(0), new Coordinates(1,1), Orientation.HORIZONTAL,1);
        gameEngine.move(players.get(0), Direction.UP);

        assertSame(board.getPosition(1, 1), players.get(0).getMeeple().getPosition());

    }

    @Test
    public void moveMeepleLeftWhenWallIsPlaced() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 1), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        gameEngine.placeWall(players.get(0), new Coordinates(1,0), Orientation.VERTICAL,1);
        gameEngine.move(players.get(0), Direction.LEFT);

        assertSame(board.getPosition(1, 1), players.get(0).getMeeple().getPosition());
    }

    @Test
    public void moveMeepleRightWhenWallIsPlaced() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 1), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        gameEngine.placeWall(players.get(0), new Coordinates(1,1), Orientation.VERTICAL,1);
        gameEngine.move(players.get(0), Direction.RIGHT);

        assertSame(board.getPosition(1, 1), players.get(0).getMeeple().getPosition());
    }

    @Test
    public void moveMeepleDownWhenWallIsPlaced() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 1), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        gameEngine.placeWall(players.get(0), new Coordinates(0,1), Orientation.HORIZONTAL,1);
        gameEngine.move(players.get(0), Direction.DOWN);

        assertSame(board.getPosition(1, 1), players.get(0).getMeeple().getPosition());
    }


    @Test
    public void moveMeepleWhenFinalMargin1() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 5), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(6, 1), Color.RED, Margin.TOP), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(6, 0), Color.BLUE, Margin.TOP), 10));
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        GameEngine gameEngine = new GameEngine(players, board);

        gameEngine.move(players.get(0), Direction.RIGHT);
        gameEngine.move(players.get(0), Direction.RIGHT);

        assertSame(board.getPosition(1, 6), players.get(0).getMeeple().getPosition());
    }


    @Test
    public void moveMeepleWhenFinalMargin2() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 6), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(6, 1), Color.RED, Margin.TOP), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(6, 0), Color.BLUE, Margin.TOP), 10));
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        GameEngine gameEngine = new GameEngine(players, board);

        gameEngine.move(players.get(1), Direction.UP);

        assertSame(board.getPosition(6, 1), players.get(1).getMeeple().getPosition());

    }
    @Test
    public void moveMeepleWhenFinalMargin3() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 6), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(6, 1), Color.RED, Margin.TOP), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(6, 0), Color.BLUE, Margin.TOP), 10));
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        GameEngine gameEngine = new GameEngine(players, board);

        gameEngine.move(players.get(2), Direction.DOWN);

        assertSame(board.getPosition(0, 0), players.get(2).getMeeple().getPosition());

    }

    @Test
    public void moveMeepleWhenFinalMargin4() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(1, 6), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(6, 1), Color.RED, Margin.TOP), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(6, 0), Color.BLUE, Margin.TOP), 10));
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        GameEngine gameEngine = new GameEngine(players, board);

        gameEngine.move(players.get(3), Direction.UP);

        assertSame(board.getPosition(6, 0), players.get(3).getMeeple().getPosition());

    }
    @Test
    public void placeWallWhenAlreadyPlaced() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(4, 1), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(3, 1), Color.RED, Margin.TOP), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        /*for (int i = 0; i < players.size() && i < Margin.values().length; i++) {
            players.get(i).getMeeple().setFinalMarginGivenInitial(Margin.values()[i]);
        }*/
        gameEngine.placeWall(players.get(0), new Coordinates(1,1),  Orientation.HORIZONTAL, 1);
        gameEngine.placeWall(players.get(0), new Coordinates(1,2),  Orientation.HORIZONTAL, 2);

    }





}



