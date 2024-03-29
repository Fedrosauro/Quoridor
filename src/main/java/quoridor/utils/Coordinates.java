package quoridor.utils;

public class Coordinates {

    private int column;
    private int row;

    public Coordinates(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "column=" + column + ", row=" + row + '}';
    }
}
