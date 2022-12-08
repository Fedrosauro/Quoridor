package quoridor.components;

import quoridor.utils.Orientation;

import java.util.ArrayList;

public class Wall {

    private ArrayList<Tile> adjacency;
    private Orientation orientation;
    private int dimension;

    public Wall(ArrayList<Tile> adjacency, Orientation orientation, int dimension){
        this.adjacency = new ArrayList<>(adjacency);
        this.orientation = orientation;
        this.dimension = dimension;
    }

    public static Wall newInstance(Wall wall){
        return new Wall(wall.adjacency, wall.orientation, wall.dimension);
    }


}
