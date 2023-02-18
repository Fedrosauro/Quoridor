package quoridor.components;

import quoridor.utils.Coordinates;

public class Wall {

    //add adiacencies field to make the getAdjacenciesOfLastWallPlaced method easier
    private int id;
    private Coordinates[] coordinates;

    public Wall() {
    }

    public Wall(int id, Coordinates[] coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public Coordinates[] getAdjacency() {
        return coordinates;
    }
}
