package quoridor;

import org.junit.jupiter.api.Test;
import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.game.GameEngine;
import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GameFlowTest {


    @Test
    public void moveMeeple() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.doMove(players.get(0), Direction.UP);
        board.move(players.get(1).getMeeple(), Direction.UP);

        assertSame(players.get(0).getMeeple().getPosition(), players.get(1).getMeeple().getPosition());

    }

    @Test
    public void placeWall() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED), 10));

        GameEngine gameEngine = new GameEngine(players, board);

        gameEngine.doPlaceWall(players.get(0), new Coordinates(2, 2), Orientation.HORIZONTAL, 2);

        assertFalse(board.wallNotPresent(new Coordinates(2, 2), Orientation.HORIZONTAL, 2));
    }

    //non funziona fixa
    @Test
    public void moveMeepleInTurn() throws PositionException {
        Board board = new Board(7, 7);
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("giec", new Meeple(board.getPosition(0, 0), Color.GREEN), 10));
        players.add(new Player("fede", new Meeple(board.getPosition(0, 0), Color.RED), 10));

        GameEngine gameEngine = new GameEngine(players, board);
        gameEngine.getDirectionInput();
        assertSame(gameEngine.getDirectionInput(), Direction.UP);

    }

    //test in cui piazzi un wall e cerchi di muoverti
    //test in cui raggiungi il margine finale e cerchi di muoverti
    //test in cui piazzi un muro e cerchi di piazzare un altro muro

}



