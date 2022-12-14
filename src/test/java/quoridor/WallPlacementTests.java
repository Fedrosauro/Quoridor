package quoridor;

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
    @CsvSource({"1,1", "2,2"})
    void singlePlacementWallHorizontal(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;

        board.singletPlacement(wallCoordinates, orientation);

        assertNotNull(board.getMatrix()[row][column].getNorthWall());
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,0"})
    void singlePlacementWallVertical(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;

        board.singletPlacement(wallCoordinates, orientation);

        assertNotNull(board.getMatrix()[row][column].getEastWall());
    }

    @ParameterizedTest
    @CsvSource({"1,1,2", "2,1,1", "2,2,2"})
    void horizontalWallPlacement(int row, int column, int dimension){
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        for(int i = 0; i < dimension; i++){
            assertNotNull(board.getMatrix()[row][column - i].getNorthWall());
        }
    }

    @ParameterizedTest
    @CsvSource({"1,1,2", "2,1,1", "1,0,2"})
    void verticalWallPlacement(int row, int column, int dimension){
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        for(int i = 0; i < dimension; i++){
            assertNotNull(board.getMatrix()[row + i][column].getEastWall());
        }
    }

    @ParameterizedTest
    @CsvSource({"2,2,2"})
    void checkWallTruePresenceTest(int row, int column, int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates = new Coordinates(row, column); //first wall placed
        Orientation orientation = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(row, column); //another same wall placed
        Orientation orientation2 = Orientation.HORIZONTAL;

        boolean freePlace = board.checkWallPresence(wallCoordinates2, orientation2, dimension);

        assertFalse(freePlace);

        /*
        tipical usage: if(!board.checkWallPresence(...){
                            board.placeWall
                       else
                            //pick another coordinate or another orientation
         */
    }

    @ParameterizedTest
    @CsvSource({"1,1,2"})
    void checkWallFalsePresenceTest(int row, int column, int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates = new Coordinates(row, column); //first wall placed
        Orientation orientation = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(row, column); //different wall placed
        Orientation orientation2 = Orientation.VERTICAL;

        boolean freePlace = board.checkWallPresence(wallCoordinates2, orientation2, dimension);

        assertTrue(freePlace);
    }

    @Test
    void checkTrueCross2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(0, 0);
        Coordinates wallCoordinates2 = new Coordinates(1, 0);
        Coordinates wallCoordinates3 = new Coordinates(1, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates3, or2, 2);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean cross = board.checkCross(coordinatesArrayList);

        assertTrue(cross);
    }

    @Test
    void checkNoCross2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(0, 0);
        Coordinates wallCoordinates2 = new Coordinates(1, 0);
        Coordinates wallCoordinates3 = new Coordinates(1, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates3, or2, 1);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean cross = board.checkCross(coordinatesArrayList);

        assertFalse(cross);
    }

    @Test
    void checkIllegalCombinationWallsID2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(0, 0);
        Coordinates wallCoordinates2 = new Coordinates(1, 0);
        Coordinates wallCoordinates3 = new Coordinates(1, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates3, or2, 2);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean illegalWallCombination = board.illegalWallIDsCombinationChecker(coordinatesArrayList);

        assertTrue(illegalWallCombination);
    }

    @Test
    void checkLegalCombinationWallsID2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(0, 0);
        Coordinates wallCoordinates2 = new Coordinates(1, 0);
        Coordinates wallCoordinates3 = new Coordinates(1, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);

        board.placeWall(wallCoordinates2, or2, 1);
        board.placeWall(wallCoordinates3, or2, 1);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean illegalWallCombination = board.illegalWallIDsCombinationChecker(coordinatesArrayList);

        assertFalse(illegalWallCombination);
    }

    @ParameterizedTest
    @CsvSource({"1", "2"})
    void horizontalWallOutOfBoundCheck(int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean northWallOutOfBounds = board.wallOutOfBoundChecker(wallCoordinates1, or1, dimension);

        assertFalse(northWallOutOfBounds);
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4"})
    void verticalWallOutOfBoundCheck(int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.VERTICAL;

        boolean northWallOutOfBounds = board.wallOutOfBoundChecker(wallCoordinates1, or1, dimension);

        assertFalse(northWallOutOfBounds);
    }

    @ParameterizedTest
    @CsvSource({"0,1", "0,3"})
    void illegalHorizontalWallPlacementFirstRow(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean horizontalWallOnFirtsRow = board.wallOnFirstRowOrLastColumnChecker(wallCoordinates1, or1);

        assertTrue(horizontalWallOnFirtsRow);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,3"})
    void legalHorizontalWallPlacementNotFirstRow(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean horizontalWallOnFirtsRow = board.wallOnFirstRowOrLastColumnChecker(wallCoordinates1, or1);

        assertFalse(horizontalWallOnFirtsRow);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "3,4"})
    void illegalVerticalWallPlacementLastColumn(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        boolean verticalWallOnLastColumn = board.wallOnFirstRowOrLastColumnChecker(wallCoordinates1, or1);

        assertTrue(verticalWallOnLastColumn);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,3"})
    void legalVerticalWallPlacementNotLastColumn(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        boolean verticalWallOnFirtsRow = board.wallOnFirstRowOrLastColumnChecker(wallCoordinates1, or1);

        assertFalse(verticalWallOnFirtsRow);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,1", "3,1", "2,2"})
    void trueSameMatrixOfCopyBoardObjectTest(int row, int column){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneObject();

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        board.placeWall(wallCoordinates, or1, 1);
        copyBoard.placeWall(wallCoordinates, or1, 1);

        boolean sameBoard = board.equalMatrix(copyBoard);

        assertTrue(sameBoard);
    }

    @Test
    void falseSameMatrixOfCopyBoardObjectTest(){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneObject();

        Coordinates wallCoordinates = new Coordinates(1, 1);
        Orientation or1 = Orientation.VERTICAL;

        copyBoard.placeWall(wallCoordinates, or1, 2);

        boolean sameBoard = board.equalMatrix(copyBoard);

        assertFalse(sameBoard);
    }

    @Test
    void checkWallAdiacenciesTest(){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneObject();

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;

        copyBoard.placeWall(wallCoordinates1, or1, 2);

        ArrayList<Tile[]> adiacencies = copyBoard.getAdiacenciesOfLastWallPlaced(wallCoordinates1, or1, 2);

        boolean correctTiles = true;

        for(int i = 0; i < adiacencies.size(); i++){
            correctTiles = adiacencies.get(i)[0].getNorthWall() != null
                    && adiacencies.get(i)[0].getEastWall() == null
                    && adiacencies.get(i)[1].getNorthWall() == null
                    && adiacencies.get(i)[1].getEastWall() == null;
        }

        assertTrue(correctTiles);
    }


}
