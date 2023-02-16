package quoridor.game;

import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.utils.Action;
import quoridor.utils.Coordinates;
import quoridor.utils.Direction;

import java.util.Random;

public class AutoPlayer extends Player {

    private int moveProbability;
    private Board board; //da togliere fa schifo

    public AutoPlayer(String name, Meeple meeple, int walls, int moveProbability, Board board) {
        super(name, meeple, walls);
        this.board = board;

        if (moveProbability > 1) this.moveProbability = 1;
        else if (moveProbability < 0) this.moveProbability = 0;
        else this.moveProbability = moveProbability;

    }

    public int getMoveProbability() {
        return moveProbability;
    }

    public Action decideActionToPerform(){

        Random random = new Random();
        if(random.nextFloat() <= moveProbability) return Action.MOVEMEEPLE;
        else return Action.PLACEWALL;

    }

}

//auto move auto place risolvere bug crush matrice