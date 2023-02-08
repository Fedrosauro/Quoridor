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
    void realCloneMeepleTest() throws PositionException {
        Board board = new Board(5, 5);
        Meeple meeple = new Meeple(board.getPosition(3, 3), Color.BLUE);

        Meeple meepleCopy = meeple.cloneObject();

        assertSame(meeple.getPosition(), meepleCopy.getPosition());
        assertSame(meeple.getColor(), meepleCopy.getColor());
    }

    @Test
    void fakeCloneMeepleTest() throws PositionException {
        Board board = new Board(5, 5);
        Meeple meeple = new Meeple(board.getPosition(3, 3), Color.BLUE);

        Meeple meepleCopy = meeple.cloneObject();
        meepleCopy.setColor(Color.GREEN);

        assertSame(meeple.getPosition(), meepleCopy.getPosition());
        assertNotSame(meeple.getColor(), meepleCopy.getColor());
    }

    @ParameterizedTest
    @CsvSource({"4,3", "2,1", "1,4"})
    void InBoardCoordtinatesTest(int row, int column) {
        Board board = new Board(5, 5);

        assertTrue(row >= 0 && column >= 0 && row < board.getMatrix().length && column < board.getMatrix().length);
    }

    @ParameterizedTest
    @CsvSource({"0,6", "-1,1", "5,6"})
    void OutOfBoardCoordtinatesTest(int row, int column) {
        Board board = new Board(5, 5);

        assertFalse(row >= 0 && column >= 0 && row < board.getMatrix().length && column < board.getMatrix().length);
    }

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
    void meepleNonBlockedTest(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(row, column), Color.GREEN), 10, Direction.UP);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallPlaceableAdvanced(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        assertNotNull(board.getMatrix()[wallCoordinates.getRow()][wallCoordinates.getColumn()].getNorthWall());
    }
}
