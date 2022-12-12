package quoridor.components;

import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

import java.util.ArrayList;

public class Board {

    private int rows;
    private int columns;
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
                    return new Coordinates(j, i);
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

}
