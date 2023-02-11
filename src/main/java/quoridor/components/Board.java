package quoridor.components;

import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public Board(Tile[][] matrix, int wallID, int rows, int columns){
        this.rows = rows;
        this.columns = columns;
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

                if(actualCoordinates.getColumn() == columns - 1) return false; //true

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if(tile.getEastWall() == null) return true;

            }
            case LEFT -> {

                if(actualCoordinates.getColumn() == 0) return false; //true

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()-1];
                if(tile.getEastWall() == null) return true;

            }
            case UP -> {

                if(actualCoordinates.getRow() == rows - 1) return false; //true

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if(tile.getNorthWall() == null) return true;

            }
            case DOWN -> {

                if(actualCoordinates.getRow() == 0) return false; //true

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

        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            coordinates[1] = new Coordinates(wallC.getRow() - 1, wallC.getColumn());
            matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall(this.wallID, coordinates));
        } else if (orientation == Orientation.VERTICAL) {
            coordinates[1] = new Coordinates(wallC.getRow(), wallC.getColumn() + 1);
            matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall(this.wallID, coordinates));
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
                case VERTICAL -> copyWallC.setRow(copyWallC.getRow() - 1);
            }
            singletPlacement(copyWallC, orientation);
            i++;
        }
        wallID++;
    }

    public boolean wallNotPresent(Coordinates wallC, Orientation orientation, int dimension) {
        boolean result = true;
        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            for (int i = 0; i < dimension && result; i++) {
                result = matrix[wallC.getRow()][wallC.getColumn() - i].getNorthWall() == null;
            }
        } else if (orientation == Orientation.VERTICAL) {
            for (int i = 0; i < dimension && result; i++) {
                result = matrix[wallC.getRow() - i][wallC.getColumn()].getEastWall() == null;
            }
        }
        return result;
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
        if (Objects.requireNonNull(orientation) == Orientation.VERTICAL) {
            return wallC.getRow() - dimension + 1 < 0;
        } else if (orientation == Orientation.HORIZONTAL) {
            return wallC.getColumn() - dimension + 1 < 0;
        }
        return false;
    }

    public boolean wallOnFirstRowOrLastColumnChecker(Coordinates wallC, Orientation orientation) {
        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            return wallC.getRow() == matrix.length - 1;
        } else if (orientation == Orientation.VERTICAL) {
            return wallC.getColumn() == matrix[0].length - 1;
        }
        return false;
    }

    public Board cloneObject() {
        return new Board(this.matrix, this.wallID, this.rows, this.columns);
    }

    public boolean equalMatrix(Board board){
        boolean equal = true;
        for(int i = 0; i < this.matrix.length && equal; i++){
            for(int j = 0; j < this.matrix[0].length && equal; j++){
                equal = this.matrix[i][j].equalTile(board.getMatrix()[i][j]);
            }
        }
        return equal && this.rows == board.getRows() && this.columns == board.getColumns();
    }

    public ArrayList<Coordinates[]> getAdiacenciesOfLastWallPlaced(Coordinates wallC, Orientation orientation, int dimension) {
        Coordinates copyWallC = new Coordinates(wallC.getRow(), wallC.getColumn());
        ArrayList<Coordinates[]> adiacencies = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
                adiacencies.add(matrix[copyWallC.getRow()][copyWallC.getColumn()].getNorthWall().getAdiacencies());
                copyWallC.setColumn(copyWallC.getColumn() - 1);
            } else if (orientation == Orientation.VERTICAL) {
                adiacencies.add(matrix[copyWallC.getRow()][copyWallC.getColumn()].getEastWall().getAdiacencies());
                copyWallC.setRow(copyWallC.getRow() - 1);
            }
        }
        return adiacencies;
    }

    public boolean isWallPlaceable(Coordinates wallC, Orientation orientation, int dimension) {
        boolean placeable = !wallOutOfBoundChecker(wallC, orientation, dimension)
                && !wallOnFirstRowOrLastColumnChecker(wallC, orientation)
                && wallNotPresent(wallC, orientation, dimension);

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
            }
            case BOTTOM -> {
                if (meeplePosition.getRow() == rows - 1) return true;
            }
            case LEFT -> {
                if (meeplePosition.getColumn() == 0) return true;
            }
            case RIGHT -> {
                if (meeplePosition.getColumn() == columns - 1) return true;
            }
        }

        return false;
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


    public boolean isWallPlaceableAdvanced(Coordinates wallC, Orientation orientation, int dimension, Player player) throws PositionException {
        boolean placeable = wallNotPresent(wallC, orientation, dimension)
                && !wallOutOfBoundChecker(wallC, orientation, dimension)
                && !wallOnFirstRowOrLastColumnChecker(wallC, orientation)
                /*&& winningPathCheck(wallC, orientation, dimension, player)*/;

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

    public String printPathSolution(ArrayList<Coordinates> coordinates){
        String s = "";
        for(int i = 0; i < coordinates.size(); i++){
            s += "[ "+ coordinates.get(i).getRow() + ", " + coordinates.get(i).getColumn() + " ] ";
        }
        return s;
    }

    public boolean insideBoard(int row, int column){
        return row >= 0 && column >= 0 && row < matrix.length && column < matrix.length;
    }

    public ArrayList<String> printTile(int i, int j, Player player){
        Coordinates playerCoordinates = findPosition(player.getMeeple().getPosition());
        String sAbove = "";
        String sUnder = "";
        ArrayList<String> result = new ArrayList<>();

        if(i == playerCoordinates.getRow() && j == playerCoordinates.getColumn()){
            sUnder = " X";
        }else{
            sUnder = " O";
        }
        if(this.matrix[i][j].getEastWall() != null) sUnder += " |";
        else sUnder += "  ";
        if(this.matrix[i][j].getNorthWall() != null) sAbove = "__  ";
        else sAbove = "    ";

        result.add(sAbove); result.add(sUnder);

        return result;
    }

    public String printTileRow(int row, Player player){
        String sAbove = "";
        String sUnder = "";
        ArrayList<String> tempResult;

        for(int j = 0; j < matrix.length; j++){
            tempResult = printTile(row, j, player);
            sAbove += tempResult.get(0) + "  ";
            sUnder += tempResult.get(1) + "  ";
        }
        return "\n" + sAbove + "\n" + sUnder;
    }

    public String printEntireBoard(Player player){
        String s = "";
        for(int i = matrix.length - 1; i >= 0; i--){
            s += printTileRow(i, player);
        }
        return s;
    }

    public boolean checkFinalMarginCoordinatesReached(Coordinates position, Margin finalMargin) {
        switch (finalMargin) {
            case TOP -> {
                if (position.getRow() == 0) return true;
            }
            case BOTTOM -> {
                if (position.getRow() == rows - 1) return true;
            }
            case LEFT -> {
                if (position.getColumn() == 0) return true;
            }
            case RIGHT -> {
                if (position.getColumn() == columns - 1) return true;
            }
        }

        return false;
    }

    public boolean pathExistance(ArrayList<Coordinates> path, Coordinates position, Meeple meeple) {
        if(!insideBoard(position.getRow(), position.getColumn()) || matrix[position.getRow()][position.getColumn()].getVisitedTile()) return false;

        path.add(position);
        matrix[position.getRow()][position.getColumn()].setVisitedTile();

        if(checkFinalMarginCoordinatesReached(position, meeple.getFinalMargin())
                || (thereIsNoWall(position, Direction.UP) && pathExistance(path, new Coordinates(position.getRow() + 1, position.getColumn()), meeple))
                || (thereIsNoWall(position, Direction.LEFT) && pathExistance(path, new Coordinates(position.getRow(), position.getColumn() - 1), meeple))
                || (thereIsNoWall(position, Direction.RIGHT) && pathExistance(path, new Coordinates(position.getRow(), position.getColumn() + 1), meeple))
                || (thereIsNoWall(position, Direction.DOWN) && pathExistance(path, new Coordinates(position.getRow() - 1, position.getColumn()), meeple)))
            return true;

        matrix[path.get(path.size() - 1).getRow()][path.get(path.size() - 1).getColumn()].resetVisitedTile();
        path.remove(path.size() - 1);

        return false;
    }
/*
    public boolean winningPathCheck(Coordinates wallC, Orientation orientation, int dimension, Player player) throws PositionException {
        Board copyBoard = cloneObject(); //created to not mess with the original board
        copyBoard.placeWall(wallC, orientation, dimension);
        Player copyPlayer = new Player("testPlayer",
                        new Meeple(copyBoard.getPosition(findPosition(player.getMeeple().getPosition()).getRow(), findPosition(player.getMeeple().getPosition()).getColumn()),
                        player.getMeeple().getColor()),
                        player.getWalls(),
                        player.getWinningDirection()); //without copyPlayer the reference of the tile is another and the print does not work
        ArrayList<Coordinates> path = new ArrayList<>();

        return copyBoard.pathExistance(path, findPosition(player.getMeeple().getPosition()), player);
    }*/
}
