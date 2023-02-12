package quoridor.game;

import quoridor.components.Board;
import quoridor.utils.Margin;
import quoridor.utils.NumberOfPlayerException;
import quoridor.utils.PositionException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class GameEngine {

    List<Player> players;
    Board board;


    public GameEngine(List<Player> players, Board board) {
        this.players = players;
        this.board = board;
    }


    public boolean illegalWall(int totalWalls) {
        return totalWalls > 1;
    }

    public void getPossibleMoves(Player player) {

        //aggiorna le positions nei meeple per ottenere di quanto puoi spostarti in ogni direzione (anche obliqua se vuoi)

    }


    public List<Player> divideWallPerPlayer(int totalWalls) throws NumberOfPlayerException {
        int totalPlayers = players.size();
        if (totalPlayers < 2 || totalPlayers == 3 || totalPlayers > 4) {
            throw new NumberOfPlayerException(totalPlayers);
        }
        if (illegalWall(totalWalls)) {
            int division = totalWalls / totalPlayers;
            for (Player player : players) {
                player.setWalls(division);
            }
            return players;
        }
        return new ArrayList<>();

    }

    public List<Player> getPlayers() {
        return players;
    }


    public void setInitialMeepleDependingOnPlayers() throws NumberOfPlayerException, PositionException {
        if (players.size() < 2 || players.size() == 3 || players.size() > 4) {
            throw new NumberOfPlayerException(players.size());
        }
        List<Margin> marginList = new ArrayList<>(EnumSet.allOf(Margin.class));

        for (int i = 0; i < players.size(); i++) {
            board.setMeeplePositionGivenMargin(players.get(i).getMeeple(), marginList.get(i));
        }
    }

    public String printPlayersInfo() {
        String s = "\n=====================\nPLAYERS";
        for(int i = 0; i < players.size(); i++){
            s += players.get(i).printPlayerInfo();
        }
        return s;
    }
}