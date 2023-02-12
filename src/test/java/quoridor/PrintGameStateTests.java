package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.game.GameEngine;
import quoridor.game.Player;
import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;
import quoridor.utils.PositionException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrintGameStateTests {

    @Test
    void printTileTest() throws PositionException {
        Board board = new Board(3, 3);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 1), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(2, 2);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("    ");
        expectedResult.add(" 1  ");

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        assertEquals(expectedResult,board.printTile(1, 1, playersPositions));
    }

    @Test
    void printTileRowTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 0), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Coordinates wallCoordinates2 = new Coordinates(1, 3);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);
        board.placeWall(wallCoordinates2, or2, 1);

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        System.out.println(board.printTileRow(1,playersPositions));

        assertEquals("\n" +
                "      __          __          \n" +
                " 1     O |   O     O     O    ",board.printTileRow(1, playersPositions));
    }

    @Test
    void printEntireBoardTest() throws PositionException {
        Board board = new Board(4, 4);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 2), Color.GREEN), 10);
        Player player2 = new Player("ludov",new Meeple(board.getPosition(0, 3), Color.BLUE), 10);

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 2);

        Coordinates wallCoordinates2 = new Coordinates(1, 2);
        Orientation or2 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates2, or2, 2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);

        System.out.println(board.printEntireBoard(players));

        assertEquals("\n                        \n" +
                " O     O     O     O    \n" +
                "                        \n" +
                " O     O     O     O    \n" +
                "__    __                \n" +
                " O     O     1 |   O    \n" +
                "                        \n" +
                " O     O     O |   2    ",board.printEntireBoard(players));
    }

    /*all the methods used above have already been developed in the NonBlockCondition branch
    since the developer had to check and see how the results were achieved*/

    @Test
    void printMeepleInfoTest() throws PositionException {
        Board board = new Board(3, 3);
        Meeple meeple = new Meeple(board.getPosition(1, 1), Color.GREEN);

        String result = meeple.printMeepleInfo();

        assertEquals("meeple: GREEN", result);
    }

    @Test
    void printPlayerInfoTest() throws PositionException {
        Board board = new Board(3, 3);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 1), Color.GREEN), 10);

        String result = player.printPlayerInfo();

        assertEquals("\ngiec\n" +
                "   meeple: GREEN\n" +
                "   usable walls: 10", result);
    }

    @Test
    void printPlayersInfoTest() throws PositionException {
        ArrayList<Player> players = new ArrayList<>();
        Board board = new Board(3, 3);
        players.add(new Player("giec",new Meeple(board.getPosition(1, 1), Color.BLUE), 10));
        players.add(new Player("ludo", new Meeple(board.getPosition(0, 0), Color.GREEN),7));

        GameEngine gameEngine = new GameEngine(players, board);

        String result = gameEngine.printPlayersInfo();

        assertEquals("\n" +
                "=====================\n" +
                "PLAYERS\n" +
                "giec\n" +
                "   meeple: BLUE\n" +
                "   usable walls: 10\n" +
                "ludo\n" +
                "   meeple: GREEN\n" +
                "   usable walls: 7", result);
    }
}
