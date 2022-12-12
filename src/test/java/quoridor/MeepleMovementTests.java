package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Tile;
import quoridor.components.Wall;
import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class MeepleMovementTests {

    @ParameterizedTest
    @CsvSource({"0,0", "0,2", "9,9", "12,45", "-1,9", "5,-9", "-1,-4"})
    void checkMeeplePosition(int row, int column) {
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
    void checkCorrectCoordinatesAreReturned(int row, int column) {
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
    void checkIfNullIsReturnedUsingGenericTile() {
        Board board = new Board(9, 9);
        Tile tile = new Tile();

        assertNull(board.findPosition(tile));
    }

    @Test
    void checkRightMovement() {
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
    void checkLeftMovement() {
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
    void checkUpMovement() {
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
    void checkDownMovement() {
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
    void checkSingleMovement(Direction direction) {
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

    @Test
    void checkPathOfMoves() {

        Board board = new Board(9, 9);

        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.UP);
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);

        try {
            Meeple meeple = new Meeple(board.getPosition(3, 5), Color.BLUE);

            board.doSequenceOfMoves(meeple, directions);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }


    }

    @Test
    void checkOutOfBoundUpMovement() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(8, 5), Color.BLUE);

            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(8, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void checkOutOfBoundDownMovement() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(0, 5), Color.BLUE);

            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(0, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void checkOutOfBoundRightMovement() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 8), Color.BLUE);

            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(5, 8), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void checkOutOfBoundLeftMovement() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 0), Color.BLUE);

            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(5, 0), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testInvalidPath() {

        Board board = new Board(9, 9);

        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.DOWN);
        directions.add(Direction.LEFT);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.UP);
        directions.add(Direction.LEFT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.RIGHT);
        directions.add(Direction.UP);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.DOWN);
        directions.add(Direction.RIGHT); //create path without of bounds moves


        try {
            Meeple meeple = new Meeple(board.getPosition(0, 0), Color.BLUE);

            board.doSequenceOfMoves(meeple, directions);

            assertSame(board.getPosition(0, 8), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUpMovementWhenThereIsAWall() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(1, 1).setNorthWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(1, 1), Color.BLUE);

            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(1, 1), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testDownMovementWhenThereIsAWall() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(0, 1).setNorthWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(1, 1), Color.BLUE);

            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(1, 1), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testLeftMovementWhenThereIsAWall() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(1, 0).setEastWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(1, 1), Color.BLUE);

            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(1, 1), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testRightMovementWhenThereIsAWall() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(1, 1).setEastWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(1, 1), Color.BLUE);

            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(1, 1), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

}
