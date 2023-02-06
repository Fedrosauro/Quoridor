package quoridor.components;

import quoridor.utils.Color;
import quoridor.utils.Margin;

public class Meeple {
    private Tile position;
    private Color color;
    private Margin margin;

    public Meeple(Tile position, Color color) {
        this.position = position;
        this.color = color;

    }



    public void setPosition(Tile position) {
        this.position = position;
    }

    public void setInitialMargin(Margin margin) {
        this.margin = margin;
    }

    public Tile getPosition() {
        return position;
    }



}
