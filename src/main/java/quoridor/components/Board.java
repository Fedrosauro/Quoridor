package quoridor.components;

import quoridor.utils.*;

import java.util.ArrayList;
import java.util.List;


public class Board {

    private int rows;
    private int columns;
    private int wallID;
    private Tile[][] matrix;
    private ArrayList<Wall> walls;

    public Board(int rows, int columns) {

        this.rows = rows;
        this.columns = columns;

        matrix = new Tile[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new Tile();
            }
        }
        this.wallID = 1;
    }

    public Board(Tile[][] matrix, int wallID){
        this.matrix = new Tile[matrix.length][matrix[0].length];

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                this.matrix[i][j] = new Tile();
                this.matrix[i][j].setNorthWall(matrix[i][j].getNorthWall());
                this.matrix[i][j].setEastWall(matrix[i][j].getEastWall());
            }
        }
        this.wallID = wallID;
    }

    public Tile getPosition(int row, int column) throws PositionException {

        if (row >= rows || row < 0 || column >= columns || column < 0)
            throw new PositionException(row, column);

        else return matrix[row][column];
    }

    public Coordinates findPosition(Tile tile) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (tile.equals(matrix[i][j]))
                    return new Coordinates(i, j);
            }
        }
        return null;
    }

    public void move(Meeple meeple, Direction direction) {

        Coordinates actualCoordinates = this.findPosition(meeple.getPosition());

        if(thereIsNoWall(actualCoordinates, direction)) {

            switch (direction) {

                case RIGHT -> {
                    if (actualCoordinates.getColumn() < columns - 1)
                        meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() + 1]);
                }
                case LEFT -> {
                    if (actualCoordinates.getColumn() > 0)
                        meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() - 1]);
                }
                case UP -> {
                    if (actualCoordinates.getRow() < columns - 1)
                        meeple.setPosition(matrix[actualCoordinates.getRow() + 1][actualCoordinates.getColumn()]);
                }
                case DOWN -> {
                    if (actualCoordinates.getRow() > 0)
                        meeple.setPosition(matrix[actualCoordinates.getRow() - 1][actualCoordinates.getColumn()]);
                }

            }

        }
    }

    private boolean thereIsNoWall(Coordinates actualCoordinates, Direction direction) {

        Tile tile;

        switch (direction){
            case RIGHT -> {

                if(actualCoordinates.getColumn() == columns - 1) return true;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if(tile.getEastWall() == null) return true;

            }
            case LEFT -> {

                if(actualCoordinates.getColumn() == 0) return true;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()-1];
                if(tile.getEastWall() == null) return true;

            }
            case UP -> {

                if(actualCoordinates.getRow() == rows - 1) return true;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if(tile.getNorthWall() == null) return true;

            }
            case DOWN -> {

                if(actualCoordinates.getRow() == 0) return true;

                tile = matrix[actualCoordinates.getRow()-1][actualCoordinates.getColumn()];
                if(tile.getNorthWall() == null) return true;

            }
        }

        return false;
    }

    public void doSequenceOfMoves(Meeple meeple, List<Direction> directions) {
        for (Direction direction : directions) {
            this.move(meeple, direction);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

    public void singletPlacement(Coordinates wallC, Orientation orientation) {
        Coordinates[] coordinates = new Coordinates[2];
        coordinates[0] = new Coordinates(wallC.getRow(), wallC.getColumn());

        switch (orientation){
            case HORIZONTAL -> {
                coordinates[1] = new Coordinates(wallC.getRow() - 1, wallC.getColumn());
                matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall(this.wallID, coordinates));
            }
            case VERTICAL -> {
                coordinates[1] = new Coordinates(wallC.getRow(), wallC.getColumn() + 1);
                matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall(this.wallID, coordinates));
            }
        }
    }

    public void placeWall(Coordinates wallC, Orientation orientation, int dim) {
        Coordinates copyWallC = new Coordinates(wallC.getRow(), wallC.getColumn());
        //in this way we don't compromise the initial coordinates that may be used later
        singletPlacement(copyWallC, orientation);
        int i = 1;
        while(i < dim){
            switch (orientation) {
                case HORIZONTAL -> copyWallC.setColumn(copyWallC.getColumn() - 1);
                case VERTICAL -> copyWallC.setRow(copyWallC.getRow() + 1);
            }
            singletPlacement(copyWallC, orientation);
            i++;
        }
        wallID++;
    }

    public boolean wallNotPresent(Coordinates wallC, Orientation orientation, int dimension) {
        boolean result = true;
        switch (orientation){
            case HORIZONTAL -> {
                for(int i = 0; i < dimension && result; i++) {
                    result = matrix[wallC.getRow()][wallC.getColumn() - i].getNorthWall() == null;
                }
            }
            case VERTICAL -> {
                for(int i = 1; i < dimension && result; i++) {
                    result = matrix[wallC.getRow() + i][wallC.getColumn()].getEastWall() == null;
                }
            }
        } return result;
    }

    public boolean checkCross(ArrayList<Coordinates> arrListC){
        return matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getNorthWall() != null &&
                matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall() != null &&
                matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall() != null &&
                matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getEastWall() != null;
    }

    public boolean illegalWallIDsCombinationChecker(ArrayList<Coordinates> arrListC) {
        ArrayList<Integer> IDsCount = new ArrayList<>();

        //IDsCount at first is empty so we add the first ID of the first wall checked
        IDsCount.add(matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getNorthWall().getID());

        if(!IDsCount.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getID())){
            IDsCount.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getID());
        }

        if(!IDsCount.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getID())){
            IDsCount.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getID());
        }

        if(!IDsCount.contains(matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getEastWall().getID())){
            IDsCount.add(matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getEastWall().getID());
        }

        return IDsCount.size() == 2;
    }

    public boolean wallOutOfBoundChecker(Coordinates wallC, Orientation orientation, int dimension) {
        switch (orientation){
            case VERTICAL -> {
                return wallC.getRow() + dimension - 1 >= matrix.length;
            }
            case HORIZONTAL -> {
                return (wallC.getColumn() - dimension + 1) < 0;
            }
        } return false;
    }

    public boolean wallOnFirstRowOrLastColumnChecker(Coordinates wallC, Orientation orientation) {
        switch (orientation){
            case HORIZONTAL -> {
                if(wallC.getRow() == 0) return true;
            }
            case VERTICAL -> {
                if(wallC.getColumn() == matrix[0].length - 1) return true;
            }
        }
        return false;
    }

    public Board cloneObject() {
        return new Board(this.matrix, this.wallID);
    }

    public boolean equalMatrix(Board board){
        boolean equal = true;
        for(int i = 0; i < this.matrix.length && equal; i++){
            for(int j = 0; j < this.matrix[0].length && equal; j++){
                equal = this.matrix[i][j].equalTile(board.getMatrix()[i][j]);
            }
        }
        return equal;
    }

    public ArrayList<Coordinates[]> getAdiacenciesOfLastWallPlaced(Coordinates wallC, Orientation orientation, int dimension) {
        Coordinates copyWallC = new Coordinates(wallC.getRow(), wallC.getColumn());
        ArrayList<Coordinates[]> adiacencies = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            switch (orientation){
                case HORIZONTAL -> {
                    adiacencies.add(matrix[copyWallC.getRow()][copyWallC.getColumn()].getNorthWall().getAdiacencies());
                    copyWallC.setColumn(copyWallC.getColumn() - 1);
                }
                case VERTICAL -> {
                    adiacencies.add(matrix[copyWallC.getRow()][copyWallC.getColumn()].getEastWall().getAdiacencies());
                    copyWallC.setRow(copyWallC.getRow() + 1);
                }
            }
        }
        return adiacencies;
    }

    public boolean isWallPlaceable(Coordinates wallC, Orientation orientation, int dimension) {
        boolean placeable = wallNotPresent(wallC, orientation, dimension)
                && !wallOutOfBoundChecker(wallC, orientation, dimension)
                && !wallOnFirstRowOrLastColumnChecker(wallC, orientation);

        if(placeable){
            Board copyBoard = this.cloneObject();
            copyBoard.placeWall(wallC, orientation, dimension); //because the wall is placeable

            ArrayList<Coordinates[]> adiacencies = copyBoard.getAdiacenciesOfLastWallPlaced(wallC, orientation, dimension);
            for(int i = 0; i < adiacencies.size() - 1 && placeable; i++){
                ArrayList<Coordinates> coordinatesOf2x2Tiles = new ArrayList<>();

                coordinatesOf2x2Tiles.add(adiacencies.get(i)[0]); //top left
                coordinatesOf2x2Tiles.add(adiacencies.get(i + 1)[0]); //bottom left
                coordinatesOf2x2Tiles.add(adiacencies.get(i + 1)[1]); //bottom right

                if(copyBoard.checkCross(coordinatesOf2x2Tiles)){
                    placeable = !copyBoard.illegalWallIDsCombinationChecker(coordinatesOf2x2Tiles);
                }
            }
        }

        return placeable;
    }

    public void findFinalMargin(Meeple meeple) {
        Coordinates meeplePosition = this.findPosition(meeple.getPosition());
        int row = meeplePosition.getRow();
        int column = meeplePosition.getColumn();
        Margin finalMargin;

        if (row > column) {
            if (rows - row > column)
                finalMargin = Margin.RIGHT; //it means the meeple is in the left triangle, so it has to move to the right
            else finalMargin = Margin.TOP;
        } else {
            if (rows - row > column) finalMargin = Margin.BOTTOM;
            else finalMargin = Margin.LEFT;
        }

        meeple.setFinalPosition(finalMargin);
    }

    public boolean checkFinalMarginReached(Meeple meeple) {

        Margin finalMargin = meeple.getFinalMargin();

        Coordinates meeplePosition = this.findPosition(meeple.getPosition());

        switch (finalMargin) {
            case TOP -> {
            if (meeplePosition.getRow() == 0) return true;
            }        case BOTTOM -> {
                if (meeplePosition.getRow() == rows - 1) return true;
            }        case LEFT -> {
                if (meeplePosition.getColumn() == 0) return true;
            }        case RIGHT -> {
                if (meeplePosition.getColumn() == columns - 1) return true;
            }
        }

        return false;
    }
}

    public int centreOfLine(int number) {
        return ((number - 1) / 2);
    }


    public Tile setMeeplePositionGivenMargin(Meeple meeple, Margin margin) throws PositionException {
        switch (margin){
            case LEFT:
                meeple.setPosition(getPosition(centreOfLine(getRows()),0));
                return meeple.getPosition();
            case RIGHT:
                meeple.setPosition(getPosition(centreOfLine(getRows()),getColumns()-1));
                return meeple.getPosition();
            case TOP:
                meeple.setPosition(getPosition(0,centreOfLine(getColumns())));
                return meeple.getPosition();
            case BOTTOM:
                meeple.setPosition(getPosition(getRows()-1,centreOfLine(getColumns())));
                return meeple.getPosition();
        }
        return null;
    }

    public boolean isOdd() {
        return ((this.getRows() % 2) != 0 && (this.getColumns() % 2) != 0);
    }


    public boolean isEqual() {
        return (this.getColumns() == this.getRows());
    }


}