package quoridor.game;

import quoridor.components.Meeple;
import quoridor.utils.Action;

import java.util.Random;

public class AutoPlayer extends Player {

    private int moveProbability;

    public AutoPlayer(String name, Meeple meeple, int walls, int moveProbability) {
        super(name, meeple, walls);

        if (moveProbability > 1) this.moveProbability = 1;
        else if (moveProbability < 0) this.moveProbability = 0;
        else this.moveProbability = moveProbability;

    }

    public int getMoveProbability() {
        return moveProbability;
    }

    public Action decideActionToPerform(){

        Random random = new Random();
        Boolean performMove = random.nextBoolean();

        if(performMove) return Action.MOVEMEEPLE;
        else return Action.PLACEWALL;

    }

}

//auto move auto place risolvere bug crush matrice