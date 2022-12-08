package quoridor.components;

import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;

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

    public void wallPlacement(Coordinates wallC, Orientation orientation) {
        switch (orientation){
            case HORIZONTAL -> matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall());
            case VERTICAL -> matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall());
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
}
