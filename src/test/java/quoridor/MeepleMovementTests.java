package quoridor;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Tile;
import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

import static org.junit.jupiter.api.Assertions.*;

public class MeepleMovementTests {

    @ParameterizedTest
    @CsvSource({"0,0", "0,2", "9,9", "12,45", "-1,9", "5,-9", "-1,-4"})
    public void checkMeeplePosition(int row, int column) {
        Board board = new Board(9, 9);

        try {
            Tile position = board.getPosition(row, column);
            Meeple meeple = new Meeple(position, Color.BLUE);

            assertSame(meeple.getPosition(), position);

        } catch (PositionException e) {
            if (row >= 9 || row < 0 || column >= 9 || column < 0) {
                assert true;
            } else assert false;
        }

    }

    @ParameterizedTest
    @CsvSource({"0,0", "2,5", "8,8"})
    public void findPositionsGivenATile(int row, int column) {
        Board board = new Board(9, 9);
        Tile tile = new Tile();
        Tile tileToCheck = new Tile();
        try {
            tile = board.getPosition(row, column);
        } catch (PositionException e) {
            e.printStackTrace();
        }

        Coordinates coordinates = board.findPosition(tile);
        try {
            tileToCheck = board.getPosition(coordinates.getRow(), coordinates.getColumn());
        } catch (PositionException e) {
            e.printStackTrace();
        }


        assertSame(tile, tileToCheck);
    }

    @Test
    public void checkIfNullIsReturnedUsingGenericTile() {
        Board board = new Board(9, 9);
        Tile tile = new Tile();

        assertNull(board.findPosition(tile));
    }

    @Test
    public void checkRightMovement() {
        Board board = new Board(9, 9);

        try {

            Meeple meeple = new Meeple(board.getPosition(1, 4), Color.BLUE);
            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(1, 5), meeple.getPosition());

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkLeftMovement() {
        Board board = new Board(9, 9);

        try {

            Meeple meeple = new Meeple(board.getPosition(1, 4), Color.BLUE);
            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(1, 3), meeple.getPosition());

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkUpMovement() {
        Board board = new Board(9, 9);

        try {

            Meeple meeple = new Meeple(board.getPosition(1, 4), Color.BLUE);
            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(2, 4), meeple.getPosition());

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkDownMovement() {
        Board board = new Board(9, 9);

        try {

            Meeple meeple = new Meeple(board.getPosition(1, 4), Color.BLUE);
            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(0, 4), meeple.getPosition());

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void checkSingleMovement(Direction direction) {
        Board board = new Board(9, 9);

        try {

            Meeple meeple = new Meeple(board.getPosition(1, 4), Color.BLUE);
            board.move(meeple, direction);

            switch (direction) {
                case RIGHT -> assertSame(board.getPosition(1, 5), meeple.getPosition());
                case LEFT -> assertSame(board.getPosition(1, 3), meeple.getPosition());
                case DOWN -> assertSame(board.getPosition(0, 4), meeple.getPosition());
                case UP -> assertSame(board.getPosition(2, 4), meeple.getPosition());
            }

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    //TODO: try a path and see if the destination is right
    @Test
    @Disabled
    public void testPath() {


    }

    //TODO: meeple should not move if the player is trying to move it out of bounds, this is done in the move method
    @Test
    @Disabled
    public void checkOutOfBoundMovement() {

    }


}
