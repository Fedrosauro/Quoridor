package quoridor.game;

import quoridor.components.Meeple;

public class Player {

    private String name;
    private Meeple meeple;
    private int walls;


    public Player(String name, Meeple meeple, int walls) { //dummy
        this.name = name;
        this.meeple = meeple;
        this.walls = walls;
    }

    public Meeple getMeeple() {
        return meeple;
    }
}
