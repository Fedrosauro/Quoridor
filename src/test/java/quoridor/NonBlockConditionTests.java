package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Wall;
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
        expectedResult.add(" X |");

        assertEquals(expectedResult,board.printTile(1, 1, player));
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

        System.out.println(board.printTileRow(1, player));
    }

    @Test
    void printEntireBoardTest() throws PositionException {
        Board board = new Board(4, 4);
        Player player = new Player("giec",new Meeple(board.getPosition(2, 2), Color.GREEN), 10);

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 2);

        Coordinates wallCoordinates2 = new Coordinates(1, 2);
        Orientation or2 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates2, or2, 3);

        System.out.println(board.printEntireBoard(player));
    }
/*
    @Test
    void pathExistanceTrue() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10, Direction.UP);
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates wallCoordinates1 = new Coordinates(1, 2);
        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 2);

        Coordinates wallCoordinates2 = new Coordinates(1, 4);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates2, or2, 3);

        assertTrue(board.pathExistance(path, board.findPosition(player.getMeeple().getPosition()), player));

        System.out.println(board.printPathSolution(path));
        System.out.println(board.printEntireBoard(player));
    }

    @Test
    void pathExistanceFalse() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10, Direction.UP);
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates wallCoordinates1 = new Coordinates(1, 2);
        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 4);

        Coordinates wallCoordinates2 = new Coordinates(1, 4);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates2, or2, 3);

        assertFalse(board.pathExistance(path, board.findPosition(player.getMeeple().getPosition()), player));

        System.out.println(board.printPathSolution(path));
        System.out.println(board.printEntireBoard(player));
    }

    @Test
    void pathExistanceFalse2() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10, Direction.UP);
        ArrayList<Coordinates> path = new ArrayList<>();

        Coordinates wallCoordinates1 = new Coordinates(1, 2);
        Orientation or1 = Orientation.VERTICAL;
        board.placeWall(wallCoordinates1, or1, 3);

        Coordinates wallCoordinates2 = new Coordinates(1, 4);
        Orientation or2 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates2, or2, 3);

        Coordinates wallCoordinates3 = new Coordinates(4,4);
        board.placeWall(wallCoordinates3, or2, 5);

        assertTrue(board.pathExistance(path, board.findPosition(player.getMeeple().getPosition()), player));

        System.out.println(board.printPathSolution(path));
        System.out.println(board.printEntireBoard(player));
    }

    @Test
    void winningPathExistsTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10, Direction.UP);

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 2);

        Coordinates wallCoordinates2 = new Coordinates(2, 2);
        board.placeWall(wallCoordinates2, or1, 3);
        //already placed walls

        Coordinates wallCoordinates3 = new Coordinates(3, 4);

        assertTrue(board.winningPathCheck(wallCoordinates3, or1, 3, player));
    }

    @Test
    void winningPathNotExistsTest() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(4, 4), Color.GREEN), 10, Direction.UP);

        Coordinates wallCoordinates1 = new Coordinates(1, 3);
        Orientation or1 = Orientation.HORIZONTAL;
        board.placeWall(wallCoordinates1, or1, 4);

        Coordinates wallCoordinates3 = new Coordinates(1, 4);

        assertFalse(board.winningPathCheck(wallCoordinates3, or1, 1, player));
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,1", "1,4", "3,3", "4,4"})
    void meepleNonBlockedTest1(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10, Direction.UP);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallPlaceableAdvanced(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        assertNotNull(board.getMatrix()[wallCoordinates.getRow()][wallCoordinates.getColumn()].getNorthWall());
    }
*/

}
