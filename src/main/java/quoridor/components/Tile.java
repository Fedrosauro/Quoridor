package quoridor.components;

public class Tile {

    private Wall eastWall;
    private Wall northWall;

    private boolean visitedTile;

    public Tile(){
        this.northWall = null;
        this.eastWall = null;
        this.visitedTile = false;
    }

    public void setEastWall(Wall eastWall){
        this.eastWall = eastWall;
    }

    public void setNorthWall(Wall northWall){
        this.northWall = northWall;
    }

    public Wall getEastWall() {
        return eastWall;
    }

    public Wall getNorthWall() {
        return northWall;
    }

    public boolean equalTile(Tile tile) {
        boolean equalWalls = true;
        if(this.northWall == null)
            equalWalls = tile.getNorthWall() == null;
        else equalWalls = this.northWall.getID() == tile.getNorthWall().getID();
        if(equalWalls) {
            if (this.eastWall == null)
                equalWalls = tile.getEastWall() == null;
            else equalWalls = this.eastWall.getID() == tile.getEastWall().getID();
        }
        return equalWalls;
    }

    public void setVisitedTile(){
        this.visitedTile = true;
    }

    public void resetVisitedTile() {
        this.visitedTile = false;
    }
}
