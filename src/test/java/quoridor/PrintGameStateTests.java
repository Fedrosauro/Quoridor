package quoridor;

import org.junit.jupiter.api.Test;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.exceptions.PositionException;
import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintGameStateTests {

    @Test
    void printTileTest() throws PositionException {
        Board board = new Board(3, 3);
        Player player = new Player("giec", new Meeple(board.getPosition(1, 1), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(2, 2);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("    ");
        expectedResult.add(" G  ");

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        assertEquals(expectedResult, board.printTile(1, 1, playersPositions, players));
    }

    @Test
    void printTileRowTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec", new Meeple(board.getPosition(1, 0), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Coordinates wallCoordinates2 = new Coordinates(1, 3);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);
        board.placeWall(wallCoordinates2, or2, 1);

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printTileRow(1, playersPositions, players));

        assertEquals("\n" + "      __          __          \n" + " G     O |   O     O     O    ", board.printTileRow(1, playersPositions, players));
    }

    @Test
    void printEntireBoardTest() throws PositionException {
        Board board = new Board(4, 4);
        Player player = new Player("giec", new Meeple(board.getPosition(1, 2), Color.GREEN), 10);
        Player player2 = new Player("ludov", new Meeple(board.getPosition(0, 3), Color.BLUE), 10);

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

        assertEquals("\n                        \n" + " O     O     O     O    \n" + "                        \n" + " O     O     O     O    \n" + "__    __                \n" + " O     O     G |   O    \n" + "                        \n" + " O     O     O |   B    ", board.printEntireBoard(players));
    }

    /*all the methods used above have already been developed in the NonBlockCondition branch
    since the developer had to check and see how the results were achieved*/

    @Test
    void printMeepleInfoTest() throws PositionException {
        Board board = new Board(3, 3);
        Meeple meeple = new Meeple(board.getPosition(1, 1), Color.GREEN);

        String result = meeple.toString();

        assertEquals("Meeple: GREEN", result);
    }

}
