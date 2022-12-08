package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.components.Tile;
import quoridor.utils.Color;
import quoridor.utils.PositionException;

import static org.junit.jupiter.api.Assertions.*;

public class MeepleMovementTests {

    @ParameterizedTest
    @CsvSource({"0,0", "0,2", "9,9", "12,45", "-1,9", "5,-9", "-1,-4"})
    public void checkMeeplePosition(int row, int column){
        Board board = new Board(9,9);

        try{
            Tile position = board.getPosition(row,column);
            Meeple meeple = new Meeple(position, Color.BLUE);

            assertSame(meeple.getPosition(), position);

        }catch (PositionException e){
            if(row >= 9 || row < 0 || column >= 9 || column < 0) {
                assert true;
            }
            else assert false;
        }

    }

}
