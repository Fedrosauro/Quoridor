package quoridor.components;

import quoridor.utils.Coordinates;

public class Wall {

    private int id;
    private Coordinates[] adjacency;

    public Wall() {
    }

    public Wall(int id, Coordinates[] adjacency) {
        this.id = id;
        this.adjacency = adjacency;
    }

    public int getId() {
        return id;
    }

    public Coordinates[] getAdjacency() {
        return adjacency;
    }
}
