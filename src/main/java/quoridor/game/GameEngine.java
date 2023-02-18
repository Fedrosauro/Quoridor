package quoridor.game;

import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.utils.*;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    List<Player> players;
    Board board;
    GameType gameType;
    private final OpponentType opponentType;
    private int indexOfActivePlayer;


    public GameEngine(List<Player> players, Board board) {
        this.players = players;
        this.board = board;
        this.opponentType = OpponentType.HUMAN;
    }

    public GameEngine(int totPlayers, List<String> nameOfPlayers, int rows, int columns, int totWalls, GameType gameType, OpponentType opponentType) throws PositionException, NumberOfPlayerException {
        this.gameType = gameType;
        this.opponentType = opponentType;

        this.players = new ArrayList<>();
        this.board = new Board(rows, columns);

        if (totPlayers != 2 && totPlayers != 4) throw new NumberOfPlayerException(totPlayers);

        int wallsPerPlayer = divideWalls(totWalls, totPlayers);

        players.add(new Player(nameOfPlayers.get(0), new Meeple(board.getPosition(0, 0), Color.GREEN, Margin.TOP), wallsPerPlayer));
        players.add(new Player(nameOfPlayers.get(1), new Meeple(board.getPosition(0, 0), Color.RED, Margin.BOTTOM), wallsPerPlayer));

        if (totPlayers == 4 && nameOfPlayers.size() >= 4) {
            players.add(new Player(nameOfPlayers.get(2), new Meeple(board.getPosition(0, 0), Color.BLUE, Margin.LEFT), wallsPerPlayer));
            players.add(new Player(nameOfPlayers.get(3), new Meeple(board.getPosition(0, 0), Color.YELLOW, Margin.RIGHT), wallsPerPlayer));
        }

        this.setInitialPositionOfPlayers();

        this.indexOfActivePlayer = players.size() - 1;

    }

    public int divideWalls(int walls, int players) {
        return walls / players;
    }

    public void setInitialPositionOfPlayers() throws PositionException {
        for (Player player : players) {
            board.setMeeplePosition(player.getMeeple());
        }
    }

    public void move(Player player, Direction direction) {
        if (!board.checkFinalMarginReached(player.getMeeple())) {
            board.move(player.getMeeple(), direction);
            updateBoard();
        }
    }

    private void updateBoard() {
        ArrayList<Meeple> meeples = new ArrayList<>();
        for (Player currentPlayer : players) {
            meeples.add(currentPlayer.getMeeple());

            board.setMeeples(meeples);
        }
    }

    public void placeWall(Player player, Coordinates coordinates, Orientation orientation, int dimWall) {

        if (!board.isWallEventuallyPlaceable(coordinates, orientation, dimWall, player)) return;

        board.placeWall(coordinates, orientation, dimWall);
        int walls = this.getActivePlayer().getWalls();
        this.getActivePlayer().setWalls(walls - 1);

    }

    public String getBoardStatus() {
        return board.printEntireBoard(players);
    }

    public Color getColorOf(Player player) {
        return player.getMeeple().getColor();
    }

    public Player getActivePlayer() {
        return players.get(indexOfActivePlayer);
    }

    public void nextActivePlayer() {
        this.indexOfActivePlayer = (this.indexOfActivePlayer + 1) % players.size();
    }


    public boolean didActivePlayerWin() {

        return board.checkFinalMarginReached(this.getActivePlayer().getMeeple());

    }

    public boolean moveIsAllowed(Player player, Direction direction) {

        Coordinates coordinates = board.findPosition(player.getMeeple().getPosition());

        return board.thereIsNoWall(coordinates, direction);

    }

    public boolean placementIsAllowed(Player activePlayer, Coordinates position, Orientation orientation, int dimension) {
        if (activePlayer.getWalls() <= 0) return false;
        return board.isWallEventuallyPlaceable(position, orientation, dimension, activePlayer);
    }

    public void autoMove(AutoPlayer player) {

        Coordinates currentPosition = board.findPosition(player.getMeeple().getPosition());
        Direction direction = null;

        try {

            do {
                direction = player.decideDirection(currentPosition, board);
            } while (this.moveIsAllowed(player, direction));
        } catch (PositionException e) {
            e.printStackTrace();
        }

        this.move(player, direction);

    }

    public void autoPlace(AutoPlayer player, int wallDimension) {

        Orientation orientation;
        Coordinates wallPosition;

        do {
            orientation = player.decideWallOrientation();
            wallPosition = player.decideWallPosition(players, board);
        } while (!this.placementIsAllowed(player, wallPosition, orientation, wallDimension));

        this.placeWall(player, wallPosition, orientation, wallDimension);


    }
}