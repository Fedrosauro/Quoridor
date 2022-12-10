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
        switch (orientation){
            case HORIZONTAL -> matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall());
            case VERTICAL -> matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall());
        }
    }

    public void placeWall(Coordinates wallC, Orientation orientation, int dim) {
        singletPlacement(wallC, orientation);
        int i = 1;
        while(i < dim){
            switch (orientation) {
                case HORIZONTAL -> wallC.setColumn(wallC.getColumn() - 1);
                case VERTICAL -> wallC.setRow(wallC.getRow() + 1);
            }
            singletPlacement(wallC, orientation);
            i++;
        }
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

    public boolean checkCross(Coordinates wallC1, Coordinates wallC2, Coordinates wallC3){
        return matrix[wallC1.getRow()][wallC1.getColumn()].getEastWall() != null &&
                matrix[wallC2.getRow()][wallC2.getColumn()].getEastWall() != null &&
                matrix[wallC2.getRow()][wallC2.getColumn()].getNorthWall() != null &&
                matrix[wallC3.getRow()][wallC3.getColumn()].getNorthWall() != null;
    }
}
