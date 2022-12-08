package quoridor.components;

import quoridor.utils.Color;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;
import quoridor.utils.PositionException;

public class Meeple {

    private Tile position;
    private Color color;

    public Meeple(Tile initialPosition, Color color) {
        this.position = initialPosition;
        this.color = color;
    }

    public Tile getPosition() {
        return position;
    }

    public void move(Direction direction, Board board) {

        Coordinates actualCoordinates = board.findPosition(position);

        try {
            switch (direction) {

                case RIGHT -> this.position = board.getPosition(actualCoordinates.getRow(), actualCoordinates.getColumn() + 1);
                case LEFT -> this.position = board.getPosition(actualCoordinates.getRow(), actualCoordinates.getColumn() - 1);
                case UP -> this.position = board.getPosition(actualCoordinates.getRow() + 1, actualCoordinates.getColumn());
                case DOWN -> this.position = board.getPosition(actualCoordinates.getRow() - 1, actualCoordinates.getColumn());
            }
        } catch (PositionException e) {
            e.printStackTrace();
        }
    }
}
