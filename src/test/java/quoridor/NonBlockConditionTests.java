package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NonBlockConditionTests {

    @Test
    void printPathSolutionTest(){
        Board board = new Board(5, 5);
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates c1 = new Coordinates(2,3);
        Coordinates c2 = new Coordinates(4,5);
        path.add(c1);
        path.add(c2);

        String output = board.printPathSolution(path);

        assertEquals("[ 2, 3 ] [ 4, 5 ] ", output);
    }

    @ParameterizedTest
    @CsvSource({"4,3", "2,1", "1,4"})
    void InBoardCoordtinatesTest(int row, int column) {
        Board board = new Board(5, 5);

        assertTrue(board.insideBoard(row, column));
    }

    @ParameterizedTest
    @CsvSource({"0,6", "-1,1", "5,6"})
    void OutOfBoardCoordtinatesTest(int row, int column) {
        Board board = new Board(5, 5);

        assertFalse(board.insideBoard(row, column));
    }

    @Test
    void printTileTest() throws PositionException {
        Board board = new Board(3, 3);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 1), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(1, 1);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("__  ");
        expectedResult.add(" 1 |");

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        assertEquals(expectedResult,board.printTile(1, 1, playersPositions));
    }

    @Test
    void printTileRowTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(1, 1), Color.GREEN), 10);
        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Coordinates wallCoordinates2 = new Coordinates(1, 3);

        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 1);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or2, 1);
        board.placeWall(wallCoordinates2, or2, 2);

        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        playersPositions.add(board.findPosition(player.getMeeple().getPosition()));

        System.out.println(board.printTileRow(1,playersPositions));

        assertEquals("\n" +
                "      __    __    __          \n" +
                " O     1 |   O     O     O    ",board.printTileRow(1, playersPositions));
    }

    @Test
    void printEntireBoardTest() throws PositionException {
        Board board = new Board(4, 4);
        Player player = new Player("giec",new Meeple(board.getPosition(2, 2), Color.GREEN), 10);
        Player player2 = new Player("ludov",new Meeple(board.getPosition(0, 0), Color.BLUE), 10);

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
                " O     O     1     O    \n" +
                "__    __                \n" +
                " O     O     O |   O    \n" +
                "                        \n" +
                " 2     O     O |   O    ",board.printEntireBoard(players));
    }

    @ParameterizedTest
    @CsvSource({"0,3", "3,4", "4,1", "2,0", "3,4", "1,0", "2,2", "3,1", "1,3"})
    void pathExistanceTrue(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates wallCoordinates1 = new Coordinates(1, 2);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 3);

        Coordinates wallCoordinates2 = new Coordinates(1, 1);
        Orientation or2 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates2, or2, 1);

        Coordinates wallCoordinates3 = new Coordinates(3,4);
        board.placeWall(wallCoordinates3, or1, 3);

        Coordinates wallCoordinates4 = new Coordinates(4,1);
        board.placeWall(wallCoordinates4, or2, 2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        assertTrue(board.pathExistance(path, board.findPosition(player.getMeeple().getPosition()), player.getMeeple()));

        System.out.println(board.printPathSolution(path));
        System.out.println(board.printEntireBoard(players));
    }

    @ParameterizedTest
    @CsvSource({"3,0", "0,0", "1,3", "2,4", "4,0", "2,1"})
    void pathExistanceFalse(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates wallCoordinates1 = new Coordinates(4, 2);
        board.placeWall(wallCoordinates1, Orientation.VERTICAL, 3);

        Coordinates wallCoordinates2 = new Coordinates(1, 4);
        board.placeWall(wallCoordinates2, Orientation.HORIZONTAL, 5);

        Coordinates wallCoordinates3 = new Coordinates(1, 1);
        board.placeWall(wallCoordinates3, Orientation.VERTICAL, 2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        assertFalse(board.pathExistance(path, board.findPosition(player.getMeeple().getPosition()),  player.getMeeple()));

        System.out.println(board.printPathSolution(path));
        System.out.println(board.printEntireBoard(players));
    }

    @ParameterizedTest
    @CsvSource({"4,3", "2,1", "0,2", "3,0", "1,4", "3,2"})
    void winningPathExistsTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 3), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates1 = new Coordinates(1, 2);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 3);

        Coordinates wallCoordinates2 = new Coordinates(2, 4);
        board.placeWall(wallCoordinates2, or1, 4);
        //already placed walls

        Coordinates wallCoordinates3 = new Coordinates(3, 4);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));

        assertTrue(board.winningPathCheck(wallCoordinates3, or1, 2, player));

    }

    @ParameterizedTest
    @CsvSource({"4,4", "4,0", "3,1", "1,1", "0,3", "2,4", "0,4"})
    void winningPathNotExistsTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates1 = new Coordinates(4, 2);
        board.placeWall(wallCoordinates1, Orientation.VERTICAL, 3);

        Coordinates wallCoordinates3 = new Coordinates(1, 1);
        board.placeWall(wallCoordinates3, Orientation.VERTICAL, 2);

        Coordinates wallCoordinates2 = new Coordinates(1, 4);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));

        assertFalse(board.winningPathCheck(wallCoordinates2, Orientation.HORIZONTAL, 5, player));
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,1", "3,3", "0,3", "3,1"})
    void meepleNonBlockedHorizontalTest(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallPlaceableAdvanced(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));

        assertNotNull(board.getMatrix()[wallCoordinates.getRow()][wallCoordinates.getColumn()].getNorthWall());
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,1", "3,3", "3,0", "3,1"})
    void meepleNonBlockedVerticalTest(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallPlaceableAdvanced(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));

        assertNotNull(board.getMatrix()[wallCoordinates.getRow()][wallCoordinates.getColumn()].getEastWall());
    }
}
