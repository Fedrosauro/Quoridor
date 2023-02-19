package quoridor.components;

import quoridor.utils.Color;
import quoridor.utils.Margin;

public class Meeple {

    private Tile position;
    private Color color;
    private final Margin margin;
    private Margin finalMargin;


    public Meeple(Tile position, Color color) {
        this.position = position;
        this.color = color;
        this.margin = Margin.TOP;
        this.setFinalMarginGivenInitial();
    }

    public Meeple(Tile position, Color color, Margin margin) {
        this.position = position;
        this.color = color;
        this.margin = margin;
        this.setFinalMarginGivenInitial();
    }

    private void setFinalMarginGivenInitial() {
        switch (this.margin) {
            case LEFT -> this.finalMargin = Margin.RIGHT;
            case RIGHT -> this.finalMargin = Margin.LEFT;
            case TOP -> this.finalMargin = Margin.BOTTOM;
            case BOTTOM -> this.finalMargin = Margin.TOP;
        }
    }

    public Margin getInitialMargin() {
        return margin;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public void setFinalPosition(Margin finalMargin) {
        this.finalMargin = finalMargin;
    }

    public Margin getFinalMargin() {
        return finalMargin;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Meeple: " + this.color;
    }
}
