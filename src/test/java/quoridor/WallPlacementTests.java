package quoridor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.game.Player;
import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;
import quoridor.exceptions.PositionException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WallPlacementTests {

    @ParameterizedTest
    @CsvSource({"1,1", "2,2"})
    void singlePlacementWallHorizontal(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.HORIZONTAL;

        board.singleTileWallPlacement(wallCoordinates, orientation);

        assertNotNull(board.getMatrix()[row][column].getNorthWall());
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,0"})
    void singlePlacementWallVertical(int row, int column) {
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;

        board.singleTileWallPlacement(wallCoordinates, orientation);

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
    @CsvSource({"1,1,2", "2,1,3", "1,0,2"})
    void verticalWallPlacement(int row, int column, int dimension){
        Board board = new Board(3, 3);

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation orientation = Orientation.VERTICAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        for(int i = 0; i < dimension; i++){
            assertNotNull(board.getMatrix()[row - i][column].getEastWall());
        }
    }

    @ParameterizedTest
    @CsvSource({"2,2,2"})
    void checkWallTruePresenceHorizontalTest(int row, int column, int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates = new Coordinates(row, column); //first wall placed
        Orientation orientation = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(row, column + 1); //another same wall placed
        Orientation orientation2 = Orientation.HORIZONTAL;

        boolean wallNotPresent = board.isWallNotPresent(wallCoordinates2, orientation2, dimension);

        assertFalse(wallNotPresent);
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

        boolean wallNotPresent = board.isWallNotPresent(wallCoordinates2, orientation2, dimension);

        assertTrue(wallNotPresent);
    }

    @ParameterizedTest
    @CsvSource({"2,2,2"})
    void checkWallFalsePresenceVerticalTest(int row, int column, int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates = new Coordinates(row, column); //first wall placed
        Orientation orientation = Orientation.VERTICAL;

        board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(row - 1, column); //different wall placed
        Orientation orientation2 = Orientation.VERTICAL;

        boolean wallNotPresent = board.isWallNotPresent(wallCoordinates2, orientation2, dimension);

        assertFalse(wallNotPresent);
    }

    @Test
    void checkTrueCross2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(4, 0);
        Coordinates wallCoordinates2 = new Coordinates(3, 0);
        Coordinates wallCoordinates3 = new Coordinates(3, 1);

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

        Coordinates wallCoordinates1 = new Coordinates(4, 0);
        Coordinates wallCoordinates2 = new Coordinates(3, 0);
        Coordinates wallCoordinates3 = new Coordinates(3, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates3, or2, 1);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates3);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates1);

        boolean cross = board.checkCross(coordinatesArrayList);

        assertFalse(cross);
    }

    @Test
    void checkIllegalCombinationWallsID2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(4, 0);
        Coordinates wallCoordinates2 = new Coordinates(3, 0);
        Coordinates wallCoordinates3 = new Coordinates(3, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates3, or2, 2);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean illegalWallCombination = board.checkIllegalWallIdsCombination(coordinatesArrayList);

        assertTrue(illegalWallCombination);
    }

    @Test
    void checkLegalCombinationWallsID2x2Test(){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(4, 0);
        Coordinates wallCoordinates2 = new Coordinates(3, 0);
        Coordinates wallCoordinates3 = new Coordinates(3, 1);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);

        board.placeWall(wallCoordinates2, or2, 1);
        board.placeWall(wallCoordinates3, or2, 1);

        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        coordinatesArrayList.add(wallCoordinates1);
        coordinatesArrayList.add(wallCoordinates2);
        coordinatesArrayList.add(wallCoordinates3);

        boolean illegalWallCombination = board.checkIllegalWallIdsCombination(coordinatesArrayList);

        assertFalse(illegalWallCombination);
    }

    @ParameterizedTest
    @CsvSource({"1", "2"})
    void horizontalWallOutOfBoundCheck(int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(0, 1);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean northWallOutOfBounds = board.checkWallOutOfBounds(wallCoordinates1, or1, dimension);

        assertFalse(northWallOutOfBounds);
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4"})
    void verticalWallOutOfBoundCheck(int dimension){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(3, 1);
        Orientation or1 = Orientation.VERTICAL;

        boolean northWallOutOfBounds = board.checkWallOutOfBounds(wallCoordinates1, or1, dimension);

        assertFalse(northWallOutOfBounds);
    }

    @ParameterizedTest
    @CsvSource({"4,1", "4,3"})
    void illegalHorizontalWallPlacementFirstRow(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean horizontalWallOnFirstRow = board.checkWallOnFirstRowOrLastColumn(wallCoordinates1, or1);

        assertTrue(horizontalWallOnFirstRow);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,3"})
    void legalHorizontalWallPlacementNotFirstRow(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.HORIZONTAL;

        boolean horizontalWallOnFirtsRow = board.checkWallOnFirstRowOrLastColumn(wallCoordinates1, or1);

        assertFalse(horizontalWallOnFirtsRow);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "3,4"})
    void illegalVerticalWallPlacementLastColumn(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        boolean verticalWallOnLastColumn = board.checkWallOnFirstRowOrLastColumn(wallCoordinates1, or1);

        assertTrue(verticalWallOnLastColumn);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,3"})
    void legalVerticalWallPlacementNotLastColumn(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        boolean verticalWallOnLastColumn = board.checkWallOnFirstRowOrLastColumn(wallCoordinates1, or1);

        assertFalse(verticalWallOnLastColumn);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,1", "3,1", "2,2"})
    void trueSameMatrixOfCopyBoardObjectTest(int row, int column){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneBoard();

        Coordinates wallCoordinates = new Coordinates(row, column);
        Orientation or1 = Orientation.VERTICAL;

        board.placeWall(wallCoordinates, or1, 1);
        copyBoard.placeWall(wallCoordinates, or1, 1);

        boolean sameBoard = board.isMatrixEqual(copyBoard);

        assertTrue(sameBoard);
    }

    @Test
    void falseSameMatrixOfCopyBoardObjectTest(){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneBoard();

        Coordinates wallCoordinates = new Coordinates(1, 1);
        Orientation or1 = Orientation.VERTICAL;

        copyBoard.placeWall(wallCoordinates, or1, 2);

        boolean sameBoard = board.isMatrixEqual(copyBoard);

        assertFalse(sameBoard);
    }

    @Test
    void checkWallAdiacenciesTestHorizontal(){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneBoard();

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.HORIZONTAL;

        copyBoard.placeWall(wallCoordinates1, or1, 2);

        List<Coordinates[]> adiacencies = copyBoard.getAdjacenciesOfLastWallPlaced(wallCoordinates1, or1, 2);

        boolean correctAdiacencies = adiacencies.get(0)[0].getRow() == 1
                && adiacencies.get(0)[0].getColumn() == 1

                && adiacencies.get(0)[1].getRow() == 2
                && adiacencies.get(0)[1].getColumn() == 1

                && adiacencies.get(1)[0].getRow() == 1
                && adiacencies.get(1)[0].getColumn() == 0

                && adiacencies.get(1)[1].getRow() == 2
                && adiacencies.get(1)[1].getColumn() == 0;

        assertTrue(correctAdiacencies);
    }

    @Test
    void checkWallAdiacenciesTestVertical(){
        Board board = new Board(5, 5);

        Board copyBoard = board.cloneBoard();

        Coordinates wallCoordinates1 = new Coordinates(1, 1);
        Orientation or1 = Orientation.VERTICAL;

        copyBoard.placeWall(wallCoordinates1, or1, 2);

        List<Coordinates[]> adiacencies = copyBoard.getAdjacenciesOfLastWallPlaced(wallCoordinates1, or1, 2);

        boolean correctAdiacencies = adiacencies.get(0)[0].getRow() == 1
                && adiacencies.get(0)[0].getColumn() == 1

                && adiacencies.get(0)[1].getRow() == 1
                && adiacencies.get(0)[1].getColumn() == 2

                && adiacencies.get(1)[0].getRow() == 0
                && adiacencies.get(1)[0].getColumn() == 1

                && adiacencies.get(1)[1].getRow() == 0
                && adiacencies.get(1)[1].getColumn() == 2;

        assertTrue(correctAdiacencies);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "0,4", "2,2", "2,1", "2,4", "2,3"})
    void testWallPlacedWithConflictHorizontal(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(2, 2);
        Coordinates wallCoordinates2 = new Coordinates(2, 3);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 3);
        board.placeWall(wallCoordinates2, or2, 3);

        Coordinates wallCoordinates3 = new Coordinates(row, column); //the wall we want to place

        boolean placeable = board.isWallPlaceable(wallCoordinates3, or2, 3);

        assertFalse(placeable);
    }

    @ParameterizedTest
    @CsvSource({"0,2", "2,1", "0,1"})
    void testWallPlacedWithNoConflictHorizontal(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(2, 2);
        Coordinates wallCoordinates2 = new Coordinates(2, 3);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates2, or2, 2);

        Coordinates wallCoordinates3 = new Coordinates(row, column); //the wall we want to place

        boolean placeable = board.isWallPlaceable(wallCoordinates3, or2, 2);

        assertTrue(placeable);
    }

    @ParameterizedTest
    @CsvSource({"1,0", "2,1", "3,3", "2,3"})
    void testWallPlacedWithNoConflictVertical(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(2, 2);
        Coordinates wallCoordinates2 = new Coordinates(2, 3);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 3);
        board.placeWall(wallCoordinates2, or2, 3);

        Coordinates wallCoordinates3 = new Coordinates(row, column); //the wall we want to place

        boolean placeable = board.isWallPlaceable(wallCoordinates3, or1, 2);

        assertTrue(placeable);
    }

    @ParameterizedTest
    @CsvSource({"0,4", "3,2", "1,2", "2,2", "0,0"})
    void testWallPlacedWithConflictVertical(int row, int column){
        Board board = new Board(5, 5);

        Coordinates wallCoordinates1 = new Coordinates(2, 2);
        Coordinates wallCoordinates2 = new Coordinates(2, 3);

        Orientation or1 = Orientation.VERTICAL;
        Orientation or2 = Orientation.HORIZONTAL;

        board.placeWall(wallCoordinates1, or1, 2);
        board.placeWall(wallCoordinates2, or2, 2);

        Coordinates wallCoordinates3 = new Coordinates(row, column); //the wall we want to place

        boolean placeable = board.isWallPlaceable(wallCoordinates3, or1, 2);

        assertFalse(placeable);
    }

    @Test
    void testCheckingCrossWalls() throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(3, 3), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates = new Coordinates(3, 1);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallEventuallyPlaceable(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(4, 0);
        Orientation orientation2 = Orientation.VERTICAL;
        int dimension2 = 2;

        boolean wallCanBePlaced2 = board.isWallEventuallyPlaceable(wallCoordinates2, orientation2, dimension2, player);

        if(wallCanBePlaced2) board.placeWall(wallCoordinates2, orientation2, dimension2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));

        assertNull(board.getMatrix()[wallCoordinates2.getRow()][wallCoordinates2.getColumn()].getEastWall());
    }

    @ParameterizedTest
    @CsvSource({"4,0", "2,2", "2,1", "3,1", "3,2", "2,3", "4,2", "4,4", "0,4"})
    void testGameIstanceInWhichWallsCantBePlacedDueToConflict(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(3, 3), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates = new Coordinates(3, 1);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallEventuallyPlaceable(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(3, 1);
        Orientation orientation2 = Orientation.VERTICAL;
        int dimension2 = 2;

        boolean wallCanBePlaced2 = board.isWallEventuallyPlaceable(wallCoordinates2, orientation2, dimension2, player);

        if(wallCanBePlaced2) board.placeWall(wallCoordinates2, orientation2, dimension2);

        Coordinates wallCoordinates3 = new Coordinates(1, 3);
        Orientation orientation3 = Orientation.HORIZONTAL;
        int dimension3 = 4;

        boolean wallCanBePlaced3 = board.isWallEventuallyPlaceable(wallCoordinates3, orientation3, dimension3, player);

        if(wallCanBePlaced3) board.placeWall(wallCoordinates3, orientation3, dimension3);

        Coordinates wallCoordinates4 = new Coordinates(row, column);
        Orientation orientation4 = Orientation.VERTICAL;
        int dimension4 = 4;

        boolean wallCanBePlaced4 = board.isWallEventuallyPlaceable(wallCoordinates4, orientation4, dimension4, player);

        if(wallCanBePlaced4) board.placeWall(wallCoordinates4, orientation4, dimension4);


        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));
    }

    @ParameterizedTest
    @CsvSource({"1,2", "1,3", "1,1", "3,3", "4,3", "4,2", "3,0", "3,2"})
    void testGameIstanceInWhichWallsCanBePlacedDueToConflict(int row, int column) throws PositionException {
        Board board = new Board(5, 5);
        Player player = new Player("giec",new Meeple(board.getPosition(3, 3), Color.GREEN), 10);
        board.findFinalMargin(player.getMeeple());

        Coordinates wallCoordinates = new Coordinates(3, 1);
        Orientation orientation = Orientation.HORIZONTAL;
        int dimension = 2;

        boolean wallCanBePlaced = board.isWallEventuallyPlaceable(wallCoordinates, orientation, dimension, player);

        if(wallCanBePlaced) board.placeWall(wallCoordinates, orientation, dimension);

        Coordinates wallCoordinates2 = new Coordinates(3, 1);
        Orientation orientation2 = Orientation.VERTICAL;
        int dimension2 = 2;

        boolean wallCanBePlaced2 = board.isWallEventuallyPlaceable(wallCoordinates2, orientation2, dimension2, player);

        if(wallCanBePlaced2) board.placeWall(wallCoordinates2, orientation2, dimension2);

        Coordinates wallCoordinates3 = new Coordinates(1, 3);
        Orientation orientation3 = Orientation.HORIZONTAL;
        int dimension3 = 4;

        boolean wallCanBePlaced3 = board.isWallEventuallyPlaceable(wallCoordinates3, orientation3, dimension3, player);

        if(wallCanBePlaced3) board.placeWall(wallCoordinates3, orientation3, dimension3);

        Coordinates wallCoordinates4 = new Coordinates(row, column);
        Orientation orientation4 = Orientation.VERTICAL;
        int dimension4 = 2;

        boolean wallCanBePlaced4 = board.isWallEventuallyPlaceable(wallCoordinates4, orientation4, dimension4, player);

        if(wallCanBePlaced4) board.placeWall(wallCoordinates4, orientation4, dimension4);


        ArrayList<Player> players = new ArrayList<>();
        players.add(player);

        System.out.println(board.printEntireBoard(players));
    }


}
