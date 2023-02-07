package quoridor.components;

import quoridor.utils.Coordinates;

public class Wall {

    //add adiacencies field to make the getAdiacenciesOfLastWallPlaced method easier
    private int ID;
    private Coordinates[] coordinates;

    public Wall(){}

    public Wall(int ID, Coordinates[] coordinates){
        this.ID = ID;
        this.coordinates = coordinates;
    }

    public int getID() {
        return ID;
    }

    public Coordinates[] getAdiacencies() {
        return coordinates;
    }
}
