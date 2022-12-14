package quoridor.components;

import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;

import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

import java.util.ArrayList;

public class Board {

    private int rows;
    private int columns;
    private int wallID;
    private Tile[][] matrix;
    private ArrayList<Wall> walls;
    private ArrayList<Meeple> meeples;

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

    public Board(Tile[][] matrix){
        this.matrix = new Tile[matrix.length][matrix[0].length];

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                this.matrix[i][j] = new Tile();
                this.matrix[i][j].setNorthWall(matrix[i][j].getNorthWall());
                this.matrix[i][j].setEastWall(matrix[i][j].getEastWall());
            }
        }
        this.wallID = 1;
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

    public void doSequenceOfMoves(Meeple meeple, ArrayList<Direction> directions) {
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
        Tile[] adiacencies = new Tile[2];
        adiacencies[0] = matrix[wallC.getRow()][wallC.getColumn()];

        switch (orientation){
            case HORIZONTAL -> {
                adiacencies[1] = matrix[wallC.getRow() - 1][wallC.getColumn()];
                matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall(this.wallID, adiacencies));
            }
            case VERTICAL -> {
                adiacencies[1] = matrix[wallC.getRow()][wallC.getColumn() + 1];
                matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall(this.wallID, adiacencies));
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

    public boolean checkWallPresence(Coordinates wallC, Orientation orientation, int dimension) {
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
        return matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getEastWall() != null &&
                matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall() != null &&
                matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall() != null &&
                matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getNorthWall() != null;
    }

    public boolean illegalWallIDsCombinationChecker(ArrayList<Coordinates> arrListC) {
        ArrayList<Integer> IDsCount = new ArrayList<>();

        //IDsCount at first is empty so we add the first ID of the first wall checked
        IDsCount.add(matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getEastWall().getID());

        if(!IDsCount.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getID())){
            IDsCount.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getID());
        }

        if(!IDsCount.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getID())){
            IDsCount.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getID());
        }

        if(!IDsCount.contains(matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getNorthWall().getID())){
            IDsCount.add(matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getNorthWall().getID());
        }

        if(IDsCount.size() == 2) return true;
        else return false;
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
        return new Board(this.matrix);
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

    public ArrayList<Tile[]> getAdiacenciesOfLastWallPlaced(Coordinates wallC, Orientation orientation, int dimension) {
        ArrayList<Tile[]> adiacencies = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            switch (orientation){
                case HORIZONTAL -> {
                    adiacencies.add(matrix[wallC.getRow()][wallC.getColumn()].getNorthWall().getAdiacencies());
                    wallC.setColumn(wallC.getColumn() - 1);
                }
                case VERTICAL -> {
                    adiacencies.add(matrix[wallC.getRow()][wallC.getColumn()].getEastWall().getAdiacencies());
                    wallC.setRow(wallC.getRow() + 1);
                }
            }
        }
        return adiacencies;
    }
}
