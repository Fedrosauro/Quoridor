import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Tile;
import quoridor.components.Wall;
import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class WallPlacementTests {

    @ParameterizedTest
    @CsvSource({"1,1"})
    void placementSingleWallNorth(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;

        board.wallPlacement(wallCoordinates, orientation);

        assertNotNull(board.getMatrix()[1][1].getNorthWall());
    }

    @ParameterizedTest
    @CsvSource({"1,1"})
    void placementSingleWallEast(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;

        board.wallPlacement(wallCoordinates, orientation);

        assertNotNull(board.getMatrix()[1][1].getEastWall());
    }


}
