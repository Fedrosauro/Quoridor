package quoridor.components;

import quoridor.utils.Color;
import quoridor.utils.Margin;

public class Meeple {

    private Tile position;
    private Color color;
    private Margin margin;
    private Margin finalMargin;


    public Meeple(Tile position, Color color) {
        this.position = position;
        this.color = color;
    }
    public void setInitialMargin(Margin margin) {
        this.margin = margin;
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
