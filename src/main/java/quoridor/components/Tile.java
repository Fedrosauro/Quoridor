package quoridor.components;

import quoridor.utils.Coordinate;

public class Tile {

    private Wall eastWall;
    private Wall northWall;

    public Tile(Wall eastWall, Wall northWall){
        this.eastWall = Wall.newInstance(eastWall);
        this.northWall = Wall.newInstance(northWall);
    }

}
