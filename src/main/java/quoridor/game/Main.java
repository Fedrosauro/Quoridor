package quoridor.game;

import quoridor.components.Board;
import quoridor.utils.*;

import java.util.ArrayList;

public class Main extends GameEngine {

    public Main(ArrayList<Player> players, Board board, GameType gameType) {
        super(players, board);
    }

    public void main(String[] args) throws PositionException, NumberOfPlayerException {
        GameEngine gameEngine = new GameEngine(2, 10, 10,20, GameType.CONSOLE_GAME);
        //gameEngine.playerTurn();

    }

}



