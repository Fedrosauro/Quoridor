package quoridor.game;

import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.utils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class GameEngine {
    ArrayList<Player> players;
    Board board;
    GameType gameType;
    private OpponentType opponentType;
    private int indexOfActivePlayer;


    public GameEngine(ArrayList<Player> players, Board board) {
        this.players = players;
        this.board = board;
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

        for (Player player : players) {
            player.getMeeple().setFinalMarginGivenInitial(player.getMeeple().getInitialMargin()); //TODO: edit method so that it has 0 params and uses the margin of the meeple
        }

        this.setInitialMeepleDependingOnPlayers(); //TODO: refactoring del nome in setInitialPosition

        this.indexOfActivePlayer = 0;

    }

    public int divideWalls(int walls, int players) {
        return walls / players;
    }

    public boolean divideWallPerPlayer(int totalWalls) throws NumberOfPlayerException {
        int totalPlayers = players.size();
        if (totalPlayers < 2 || totalPlayers == 3 || totalPlayers > 4) {
            throw new NumberOfPlayerException(totalPlayers);
        }

        if ((totalWalls > 1 && players.size() == 2) || (totalWalls > 3 && players.size() == 4)) {
            int division = totalWalls / totalPlayers;
            for (int i = 0; i < totalPlayers; i++) {
                players.get(i).setWalls(division);
            }
            return true;
        }
        return false;

    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setInitialMeepleDependingOnPlayers() throws PositionException {

        for (Player player : players) {
            board.setMeeplePosition(player.getMeeple(), player.getMeeple().getInitialMargin()); //TODO: elimina parametro
        }
    }

    public Direction getDirectionInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the direction: ");
        String directionInput = scanner.nextLine();

        if (directionInput.equalsIgnoreCase("UP")) {
            return Direction.UP;
        } else if (directionInput.equalsIgnoreCase("DOWN")) {
            return Direction.DOWN;
        } else if (directionInput.equalsIgnoreCase("LEFT")) {
            return Direction.LEFT;
        } else if (directionInput.equalsIgnoreCase("RIGHT")) {
            return Direction.RIGHT;
        } else {
            throw new IllegalArgumentException("Invalid direction: " + directionInput);
        }
    }

    public Coordinates getCoordinatesInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter x coordinate: ");
        int xInput = scanner.nextInt();
        System.out.print("Enter y coordinate:");
        int yInput = scanner.nextInt();

        if (xInput > 0 && yInput > 0 && xInput < board.getColumns() && yInput < board.getRows()) {
            return new Coordinates(xInput, yInput);
        } else {
            throw new IllegalArgumentException("Invalid coordinates: " + xInput + ", " + yInput);
        }
    }

    public Orientation getOrientationInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter orientation wall: 1 for horizonatal, 2 for vertical -> ");
        int orientationInput = scanner.nextInt();

        if (orientationInput == 1) {
            return Orientation.HORIZONTAL;
        } else if (orientationInput == 2) {
            return Orientation.VERTICAL;
        } else {
            throw new IllegalArgumentException("Invalid input: " + orientationInput);
        }
    }

    public int getDimensionWallInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter wall dimension:  ");
        int wallDimensionInput = scanner.nextInt();

        if (wallDimensionInput >= 1 && wallDimensionInput < board.getRows() && wallDimensionInput < board.getColumns()) {
            return wallDimensionInput;
        } else {
            throw new IllegalArgumentException("Invalid wall dimension input: " + wallDimensionInput);
        }
    }

    public void doMove(Player player, Direction direction) {
        if (!board.checkFinalMarginReached(player.getMeeple())) {
            board.move(player.getMeeple(), direction);
            updateBoard();
        }
    }

    private void updateBoard() {
        ArrayList<Meeple> meeples = new ArrayList<>();
        for (Player currentPlayer : players) {
            meeples.add(currentPlayer.getMeeple());

            Coordinates c = board.findPosition(currentPlayer.getMeeple().getPosition());

            board.setMeeples(meeples);
        }
    }


    public void doPlaceWall(Player player, Coordinates coordinates, Orientation orientation, int dimWall) {
        if (board.isWallPlaceableAdvanced(coordinates, orientation, dimWall, player)) {
            board.placeWall(coordinates, orientation, dimWall);
            int walls = this.getActivePlayer().getWalls();
            this.getActivePlayer().setWalls(walls - 1);
        }
    } //TODO:refactoring delle condizioni

    public void playerTurn(Action action, Player player) {
        if (action == Action.MOVEMEEPLE) {
            doMove(player, getDirectionInput());
        } else if (action == Action.PLACEWALL) {
            doPlaceWall(player, getCoordinatesInput(), getOrientationInput(), getDimensionWallInput());
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }

    }


    public String printPlayersInfo() {
        String s = "\n=====================\nPLAYERS\n";
        for (int i = 0; i < players.size(); i++) {
            s += players.get(i).printPlayerInfo();
        }
        return s;
    }

    public String printBoardInfo() {
        return "\n=====================\nBOARD" + board.printEntireBoard(players) + "\n=====================";
    }

    public String getBoardStatus() {
        return board.printEntireBoard(players);
    }

    public Color getColorOf(Player player) {
        return player.getMeeple().getColor();
    }

    public String printGameState() {
        return printPlayersInfo() + printBoardInfo() + "\n";
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
        return board.isWallPlaceableAdvanced(position, orientation, dimension, activePlayer);
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

        this.doMove(player, direction);

    }

    public void autoPlace(AutoPlayer player, int wallDimension) {

        Orientation orientation;
        Coordinates wallPosition;

        do {
            orientation = player.decideWallOrientation(players);
            wallPosition = player.decideWallPosition(players, board);
        } while (!this.placementIsAllowed(player, wallPosition, orientation, wallDimension));

        this.doPlaceWall(player, wallPosition, orientation, wallDimension);


    }
}