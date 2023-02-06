package quoridor.components;

import quoridor.utils.*;

public class Meeple {

    private Tile position;
    private Color color;
    private Margin finalMargin;

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

    public void setFinalPosition(Margin finalMargin) {
        this.finalMargin = finalMargin;
    }

    public Margin getFinalMargin() {
        return finalMargin;
    }
}
