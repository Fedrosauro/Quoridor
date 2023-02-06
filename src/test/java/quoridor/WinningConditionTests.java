package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.utils.*;

import static org.junit.jupiter.api.Assertions.*;

class WinningConditionTests {

    @Test
    void checkFinalPositionOfMeepleInTopRow() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(1, 3), Color.BLUE);

            board.findFinalMargin(meeple);

            assertSame(Margin.BOTTOM, meeple.getFinalMargin());

        } catch (PositionException e) {
            e.printStackTrace();
        }


    }

    @Test
    void checkFinalPositionOfMeepleInBottomRow() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(6, 5), Color.BLUE);

            board.findFinalMargin(meeple);

            assertSame(Margin.TOP, meeple.getFinalMargin());

        } catch (PositionException e) {
            e.printStackTrace();
        }


    }

    @Test
    void checkFinalPositionOfMeepleInRightColumn() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 7), Color.BLUE);

            board.findFinalMargin(meeple);

            assertSame(Margin.LEFT, meeple.getFinalMargin());

        } catch (PositionException e) {
            e.printStackTrace();
        }


    }

    @Test
    void checkFinalPositionOfMeepleInLeftColumn() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(3, 1), Color.BLUE);

            board.findFinalMargin(meeple);

            assertSame(Margin.RIGHT, meeple.getFinalMargin());

        } catch (PositionException e) {
            e.printStackTrace();
        }


    }
    @ParameterizedTest
    @CsvSource({"0,4,UP", "8,4,DOWN", "4,8,LEFT", "4,0,RIGHT"})
    void checkFinalPositionReached(int row, int column, Direction direction) {
        int moves = 0;
        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(row, column), Color.BLUE);
            board.findFinalMargin(meeple);

            while (!board.checkFinalMarginReached(meeple) && moves < 8) {
                board.move(meeple, direction);
                moves++;
            }
            Coordinates c = board.findPosition(meeple.getPosition());
            assertTrue(board.checkFinalMarginReached(meeple));

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void checkGenericMoveIsNotWinningMove(){
        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(0, 4), Color.BLUE);
            board.findFinalMargin(meeple);

            board.move(meeple, Direction.UP);
            board.move(meeple, Direction.DOWN);

            assertFalse(board.checkFinalMarginReached(meeple));

        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvSource({"0,4,UP", "8,4,DOWN", "4,8,LEFT", "4,0,RIGHT"})
    void checkFinalPositionNotReached(int row, int column, Direction direction) {
        int moves = 0;
        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(row, column), Color.BLUE);
            board.findFinalMargin(meeple);

            while (!board.checkFinalMarginReached(meeple) && moves < 4) {
                board.move(meeple, direction);
                moves++;
            }
            assertFalse(board.checkFinalMarginReached(meeple));

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

}
