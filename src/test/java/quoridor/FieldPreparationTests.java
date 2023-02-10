package quoridor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Tile;
import quoridor.game.GameEngine;
import quoridor.utils.*;
import quoridor.game.Player;

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
        assertSame(board.centreOfLine(column), (column -1)/2);
    }

    @Test
    void checkPositionMeepleInLeftMargin() throws PositionException {
        Board board = new Board(7,7);
        Meeple meepleToCheck = new Meeple(new Tile(), Color.RED);
        Meeple meeple = new Meeple(board.getPosition(3,0),Color.RED);
        assertSame(meeple.getPosition(), board.setMeeplePositionGivenMargin(meepleToCheck, Margin.LEFT));
    }
    @Test
    void checkPositionMeepleInRightMargin() throws PositionException {
        Board board = new Board(7,7);
        Meeple meepleToCheck = new Meeple(new Tile(), Color.RED);
        Meeple meeple = new Meeple(board.getPosition(3,6),Color.RED);
        assertSame(meeple.getPosition(), board.setMeeplePositionGivenMargin(meepleToCheck, Margin.RIGHT));
    }

    @Test
    void checkPositionMeepleInTopMargin() throws PositionException {
        Board board = new Board(7,7);
        Meeple meepleToCheck = new Meeple(new Tile(), Color.RED);
        Meeple meeple = new Meeple(board.getPosition(0,3),Color.RED);
        assertSame(meeple.getPosition(), board.setMeeplePositionGivenMargin(meepleToCheck, Margin.TOP));
    }

    @Test
    void checkPositionMeepleInBottomMargin() throws PositionException {
        Board board = new Board(7,7);
        Meeple meepleToCheck = new Meeple(new Tile(), Color.RED);
        Meeple meeple = new Meeple(board.getPosition(6,3),Color.RED);
        assertSame(meeple.getPosition(), board.setMeeplePositionGivenMargin(meepleToCheck, Margin.BOTTOM));
    }

    @Test
    void check2MeeplesCreation() throws PositionException, NumberOfPlayerException {
        Board board = new Board(7,7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec",new Meeple(board.getPosition(0, 0), Color.GREEN), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED),10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.setInitialMeepleDependingOnPlayers();

        assertSame(board.getPosition(3,0), players.get(0).getMeeple().getPosition());
        assertSame(board.getPosition(3,6), players.get(1).getMeeple().getPosition());

    }

    @Test
    void check4MeeplesCreation() throws PositionException, NumberOfPlayerException {
        Board board = new Board(7,7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec",new Meeple(board.getPosition(0, 0), Color.GREEN), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED),10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.YELLOW),10));
        players.add(new Player("giova", new Meeple(board.getPosition(0, 0), Color.BLUE),10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.setInitialMeepleDependingOnPlayers();

        assertSame(board.getPosition(3,0), players.get(0).getMeeple().getPosition());
        assertSame(board.getPosition(3,6), players.get(1).getMeeple().getPosition());
        assertSame(board.getPosition(0,3), players.get(2).getMeeple().getPosition());
        assertSame(board.getPosition(6,3), players.get(3).getMeeple().getPosition());

    }

    @ParameterizedTest
    @CsvSource({"13,4"})
    void checkDivisionWallWith4Players(int totalWalls, int totalPlayer) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        for (int i = 0; i < totalPlayer; i++) {
            players.add(i, new Player("ludo",new Meeple(board.getPosition(0, 0), Color.GREEN),0));
            players.get(i).setWalls(totalWalls / totalPlayer);
        }
        GameEngine gameEngine = new GameEngine(players, board);

        assertSame(gameEngine.divideWallPerPlayer(totalWalls), gameEngine.getPlayers());

    }

    @ParameterizedTest
    @CsvSource({"22,2"})
    void checkDivisionWallWith2Players(int totalWalls, int totalPlayer) throws NumberOfPlayerException, PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        for (int i = 0; i < totalPlayer; i++) {
            players.add(i, new Player("ludo", new Meeple(board.getPosition(0, 0), Color.GREEN),0));
            players.get(i).setWalls(totalWalls / totalPlayer);
        }
        GameEngine gameEngine = new GameEngine(players, board);

        assertSame(gameEngine.divideWallPerPlayer(totalWalls), gameEngine.getPlayers());

    }

    @ParameterizedTest
    @CsvSource({"4045" +"45" + "2" })
    void checkValidWallNumber(int totalWalls)  {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        GameEngine gameEngine = new GameEngine(players, board);

        assertTrue(gameEngine.illegalWall(totalWalls));
    }

    @ParameterizedTest
    @CsvSource({"-1" + "0" + "1"})
    void checkInvalidWallNumber(int totalWalls) {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(9, 9);
        GameEngine gameEngine = new GameEngine(players, board);

        assertFalse(gameEngine.illegalWall(totalWalls));
    }


}






