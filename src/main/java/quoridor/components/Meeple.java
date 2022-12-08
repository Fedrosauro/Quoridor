package quoridor.components;

import quoridor.utils.Color;

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
}
