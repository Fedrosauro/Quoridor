package quoridor.components;

public class Wall {

    //add adiacencies field to make the getAdiacenciesOfLastWallPlaced method easier
    private int ID;
    private Tile[] adiacencies;

    public Wall(){}

    public Wall(int ID, Tile[] adiacencies){
        this.ID = ID;
        this.adiacencies = adiacencies;
    }

    public int getID() {
        return ID;
    }

    public Tile[] getAdiacencies() {
        return adiacencies;
    }
}
