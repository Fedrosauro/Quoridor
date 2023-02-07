package quoridor;

import org.junit.jupiter.api.Test;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Wall;
import quoridor.utils.Color;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SpecialMovementsTest {

    @Test
    void testUpJump() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(6, 5), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(7, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRightJump() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(5, 6), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(5, 7), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testDownJump() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(4, 5), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(3, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLeftJump() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(5, 4), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(5, 3), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUpJumpWithWallBehind() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(6, 5).setNorthWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(6, 5), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRightJumpWithWallBehind() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(5, 6).setEastWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(5, 6), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testDownJumpWithWallBehind() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(3, 5).setNorthWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(4, 5), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLeftJumpWithWallBehind() {

        Board board = new Board(9, 9);

        try {
            board.getPosition(5, 3).setEastWall(new Wall());
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple opponent = new Meeple(board.getPosition(5, 4), Color.GREEN);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(opponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testAlignCondition() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple firstOpponent = new Meeple(board.getPosition(6, 5), Color.GREEN);
            Meeple secondOpponent = new Meeple(board.getPosition(7, 5), Color.RED);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(firstOpponent);
            meeples.add(secondOpponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.UP);

            assertTrue(board.areTwoOpponentsAligned(meeple, Direction.UP));

        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testUpJumpWithOpponentsAligned() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple firstOpponent = new Meeple(board.getPosition(6, 5), Color.GREEN);
            Meeple secondOpponent = new Meeple(board.getPosition(7, 5), Color.RED);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(firstOpponent);
            meeples.add(secondOpponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.UP);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testDownJumpWithOpponentsAligned() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple firstOpponent = new Meeple(board.getPosition(4, 5), Color.GREEN);
            Meeple secondOpponent = new Meeple(board.getPosition(3, 5), Color.RED);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(firstOpponent);
            meeples.add(secondOpponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.DOWN);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testRightJumpWithOpponentsAligned() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple firstOpponent = new Meeple(board.getPosition(5, 6), Color.GREEN);
            Meeple secondOpponent = new Meeple(board.getPosition(5, 7), Color.RED);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(firstOpponent);
            meeples.add(secondOpponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.RIGHT);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testLeftJumpWithOpponentsAligned() {

        Board board = new Board(9, 9);

        try {
            Meeple meeple = new Meeple(board.getPosition(5, 5), Color.BLUE);
            Meeple firstOpponent = new Meeple(board.getPosition(5, 4), Color.GREEN);
            Meeple secondOpponent = new Meeple(board.getPosition(5, 3), Color.RED);

            ArrayList<Meeple> meeples = new ArrayList<>();
            meeples.add(meeple);
            meeples.add(firstOpponent);
            meeples.add(secondOpponent);

            board.setMeeples(meeples);

            board.move(meeple, Direction.LEFT);

            assertSame(board.getPosition(5, 5), meeple.getPosition());
        } catch (PositionException e) {
            e.printStackTrace();
        }

    }

}