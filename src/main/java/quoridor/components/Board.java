package quoridor.components;

import quoridor.utils.Coordinates;
import quoridor.utils.Orientation;

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
            case HORIZONTAL -> matrix[wallC.getRow()][wallC.getColumn()].setNorthWall(new Wall(this.wallID));
            case VERTICAL -> matrix[wallC.getRow()][wallC.getColumn()].setEastWall(new Wall(this.wallID));
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
}
