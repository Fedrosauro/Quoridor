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

    public void setPosition(Tile position) {
        this.position = position;
    }

    public Tile getPosition() {
        return position;
    }

}
