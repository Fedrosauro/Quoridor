package quoridor.game;

import quoridor.components.Meeple;
import quoridor.utils.Direction;

public class Player {

    private String name;
    private Meeple meeple;
    private int walls;

    private Direction winningDirection;

    public Player(String name, Meeple meeple,  int walls, Direction winningDirection){
        this.name = name;
        this.meeple = meeple;
        this.walls = walls;
        this.winningDirection = winningDirection;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    public Meeple getMeeple() {
        return meeple;
    }
    public int getWalls() {
        return walls;
    }

    public Direction getWinningDirection() {
        return winningDirection;
    }
}


