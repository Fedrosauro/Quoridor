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

    public void setFinalMarginGivenInitial(Margin margin){
        switch (margin){
            case LEFT ->
                    setFinalPosition(Margin.RIGHT);
            case RIGHT ->
                    setFinalPosition(Margin.LEFT);
            case TOP ->
                    setFinalPosition(Margin.BOTTOM);
            case BOTTOM ->
                    setFinalPosition(Margin.TOP);
        }
    }
    public void setInitialMargin(Margin margin) {
        this.margin = margin;
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

    public String printMeepleInfo() {
        return "meeple: " + this.color;
    }
}
