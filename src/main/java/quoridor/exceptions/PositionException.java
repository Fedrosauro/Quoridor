package quoridor.exceptions;

public class PositionException extends Exception {

    public PositionException(int row, int column) {
        super("Cannot retrieve index " + row + "," + column);
    }

}
