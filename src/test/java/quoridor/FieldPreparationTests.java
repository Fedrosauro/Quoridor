package quoridor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Tile;
import quoridor.game.*;
import quoridor.utils.Margin;
import quoridor.utils.NumberOfPlayerException;
import quoridor.game.Player;
import quoridor.utils.Color;
import quoridor.utils.PositionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FieldPreparationTests {

    @ParameterizedTest
    @CsvSource({"23,23", "2,2"})
    void checkIfRowAndColumnsAreOdd(int rows, int columns) {
        Board board = new Board(rows, columns);
        assertSame(rows % 2 != 0 && columns % 2 != 0, board.isOdd());
    }


    @ParameterizedTest
    @CsvSource({"23", "2"})
    void checkIfCenter(int column) {
        Board board = new Board(9, column);
        assertSame(board.centreOfLine(column), (column - 1) / 2);
    }

    @Test
    void checkPositionMeepleLeftMargin() throws PositionException {
        Board board = new Board(8, 8);
        Meeple meeple = new Meeple(new Tile(), Color.RED, Margin.LEFT);
        board.setMeeplePosition(meeple);

        assertSame(meeple.getPosition(), board.getPosition(3, 0));
    }

    @Test
    void checkPositionMeepleRightMargin() throws PositionException {
        Board board = new Board(8, 8);
        Meeple meeple = new Meeple(new Tile(), Color.RED, Margin.RIGHT);
        board.setMeeplePosition(meeple);

        assertSame(meeple.getPosition(), board.getPosition(3, 7));
    }

    @Test
    void checkPositionMeepleTopMargin() throws PositionException {
        Board board = new Board(8, 8);
        Meeple meeple = new Meeple(new Tile(), Color.RED, Margin.TOP);
        board.setMeeplePosition(meeple);

        assertSame(meeple.getPosition(), board.getPosition(0, 3));
    }

    @Test
    void checkPositionMeepleBottomMargin() throws PositionException {
        Board board = new Board(8, 8);
        Meeple meeple = new Meeple(new Tile(), Color.RED, Margin.BOTTOM);
        board.setMeeplePosition(meeple);

        assertSame(meeple.getPosition(), board.getPosition(7, 3));
    }

    @ParameterizedTest
    @EnumSource(Margin.class)
    void checkFinalMargin(Margin margin) throws PositionException {
        Board board = new Board(8, 8);
        Meeple meeple = new Meeple(new Tile(), Color.RED, margin);
        //board.setFinalMargin(meeple);
        switch (margin) {
            case TOP -> assertSame(meeple.getFinalMargin(), Margin.BOTTOM);
            case BOTTOM -> assertSame(meeple.getFinalMargin(), Margin.TOP);
            case LEFT -> assertSame(meeple.getFinalMargin(), Margin.RIGHT);
            case RIGHT -> assertSame(meeple.getFinalMargin(), Margin.LEFT);

        }
    }


    @Test
    void check2MeeplesCreation() throws PositionException, NumberOfPlayerException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.LEFT), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.RIGHT), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.setInitialMeepleDependingOnPlayers();

        assertSame(board.getPosition(3, 0), players.get(0).getMeeple().getPosition());
        assertSame(board.getPosition(3, 6), players.get(1).getMeeple().getPosition());

    }

    @Test
    void check4MeeplesCreation() throws PositionException, NumberOfPlayerException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.LEFT), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.RIGHT), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.BOTTOM), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.setInitialMeepleDependingOnPlayers();

        assertSame(board.getPosition(3, 0), players.get(0).getMeeple().getPosition());
        assertSame(board.getPosition(3, 6), players.get(1).getMeeple().getPosition());
        assertSame(board.getPosition(0, 3), players.get(2).getMeeple().getPosition());
        assertSame(board.getPosition(6, 3), players.get(3).getMeeple().getPosition());

    }


    @ParameterizedTest
    @CsvSource({"24,4", "12,2"})
    void checkDivisionWallWithEvenWall(int totalWalls, int totalPlayer) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        for (int i = 0; i < totalPlayer; i++) {
            players.add(i, new Player("ludo", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), 0));
            players.get(i).setWalls(totalWalls / totalPlayer);
        }

        assertSame(players.get(0).getWalls(), players.get(1).getWalls());

    }

    @ParameterizedTest
    @CsvSource({"23,2", "13,4"})
    void checkDivisionWallWithOddWall(int totalWalls, int totalPlayer) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        for (int i = 0; i < totalPlayer; i++) {
            players.add(i, new Player("ludo", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), 0));
            players.get(i).setWalls(totalWalls / totalPlayer);
        }
        assertSame(players.get(0).getWalls(), players.get(1).getWalls());

    }

    @ParameterizedTest
    @CsvSource({"4045", "2", "3"})
    void checkValidWallNumberWith2Player(int totalWalls) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.BOTTOM), 10));

        GameEngine gameEngine = new GameEngine(players, board);

        assertTrue(gameEngine.divideWallPerPlayer(totalWalls));
    }

    @ParameterizedTest
    @CsvSource({"23", "4"})
    void checkValidWallNumberWith4Players(int totalWalls) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.BOTTOM), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.LEFT), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.RIGHT), 10));

        GameEngine gameEngine = new GameEngine(players, board);

        assertTrue(gameEngine.divideWallPerPlayer(totalWalls));

    }

    @ParameterizedTest
    @CsvSource({"2", "3", "0", "-3"})
    void checkInvalidWallNumberWith4Players(int totalWalls) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED, Margin.BOTTOM), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.LEFT), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.RIGHT), 10));

        GameEngine gameEngine = new GameEngine(players, board);

        assertFalse(gameEngine.divideWallPerPlayer(totalWalls));

    }

    @ParameterizedTest
    @CsvSource({"-1", "0", "1"})
    void checkInvalidWallWith2Players(int totalWalls) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.TOP), 10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.BOTTOM), 10));

        GameEngine gameEngine = new GameEngine(players, board);

        assertFalse(gameEngine.divideWallPerPlayer(totalWalls));
    }


}






