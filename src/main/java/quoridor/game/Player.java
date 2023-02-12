package quoridor.game;

import quoridor.components.Meeple;

import quoridor.utils.Direction;

public class Player {

    private String name;
    private Meeple meeple;
    private int walls;

    public Player(String name, Meeple meeple, int walls) {
        this.name = name;
        this.meeple = meeple;
        this.walls = walls;
    }

    public Meeple getMeeple() {
        return meeple;
    }

    public int getWalls() {
        return walls;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    public String getName() {
        return name;
    }

    public String printPlayerInfo() {
        return "\n  " + this.name + "\n" +
                "     " + getMeeple().printMeepleInfo() + "\n" +
                "     " + "usable walls: " + this.walls;
    }
}


