package quoridor.components;

public class Tile {

    private Wall eastWall;
    private Wall northWall;

    public Tile() {

        this.eastWall = null;
        this.northWall = null;

    }

    public Wall getEastWall() {
        return eastWall;
    }

    public Wall getNorthWall() {
        return northWall;
    }

    public void setEastWall(Wall eastWall) {
        this.eastWall = eastWall;
    }

    public void setNorthWall(Wall northWall) {
        this.northWall = northWall;
    }
}
