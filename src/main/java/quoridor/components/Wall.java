package quoridor.components;

public class Wall {

    //add adiacencies field to make the getAdiacenciesOfLastWallPlaced method easier
    private int ID;

    public Wall(){}

    public Wall(int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
