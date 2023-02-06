package quoridor.components;

import quoridor.utils.Color;

public class Meeple {

    private Tile position;
    private Color color;

    }
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
