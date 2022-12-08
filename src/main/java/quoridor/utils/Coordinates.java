package quoridor.utils;

public class Coordinates {

    private int column;
    private int row;

    public Coordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
