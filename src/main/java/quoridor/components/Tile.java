package quoridor.components;

public class Tile {

    private Wall eastWall;
    private Wall northWall;

    public Tile() {
        this.northWall = null;
        this.eastWall = null;
    }

    public Wall getEastWall() {
        return eastWall;
    }

    public void setEastWall(Wall eastWall) {
        this.eastWall = eastWall;
    }

    public Wall getNorthWall() {
        return northWall;
    }

    public void setNorthWall(Wall northWall) {
        this.northWall = northWall;
    }

    public boolean equalTile(Tile tile) {
        boolean equalWalls = true;
        if (this.northWall == null) equalWalls = tile.getNorthWall() == null;
        else equalWalls = this.northWall.getId() == tile.getNorthWall().getId();
        if (equalWalls) {
            if (this.eastWall == null) equalWalls = tile.getEastWall() == null;
            else equalWalls = this.eastWall.getId() == tile.getEastWall().getId();
        }
        return equalWalls;
    }
}
