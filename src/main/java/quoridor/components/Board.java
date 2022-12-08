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

        switch (direction) {

            case RIGHT -> meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() + 1]);
            case LEFT -> meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() - 1]);
            case UP -> meeple.setPosition(matrix[actualCoordinates.getRow() + 1][actualCoordinates.getColumn()]);
            case DOWN -> meeple.setPosition(matrix[actualCoordinates.getRow() - 1][actualCoordinates.getColumn()]);

        }
    }
}
