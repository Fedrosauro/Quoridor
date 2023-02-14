package quoridor.game;

import quoridor.components.Board;
import quoridor.utils.*;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;


public class GameEngine {
    ArrayList<Player> players;
    Board board;

    GameType gameType;


    public GameEngine(ArrayList<Player> players, Board board) {
        this.players = players;
        this.board = board;
    }

    public GameEngine(ArrayList<Player> players, Board board, GameType gameType) {
        this.players = players;
        this.board = board;
        this.gameType = gameType;
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


    public void setInitialMeepleDependingOnPlayers() throws NumberOfPlayerException, PositionException {
        if (players.size() < 2 || players.size() == 3 || players.size() > 4) {
            throw new NumberOfPlayerException(players.size());
        }
        List<Margin> marginList = new ArrayList<>(EnumSet.allOf(Margin.class));

        for (int i = 0; i < players.size(); i++) {
            board.setMeeplePosition(players.get(i).getMeeple(), marginList.get(i));
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

        if (xInput>0 && yInput>0 && xInput < board.getColumns() && yInput < board.getRows()) {
            return new Coordinates(xInput, yInput);
        } else {
            throw new IllegalArgumentException("Invalid coordinates: " + xInput + ", " + yInput);
        }
    }
    public Orientation getOrientationInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter orientation wall: 1 for horizonatal, 2 for vertical -> ");
        int orientationInput = scanner.nextInt();

        if (orientationInput==1 ) {
            return Orientation.HORIZONTAL;
        } else if (orientationInput==2) {
            return Orientation.VERTICAL;
        }else {
            throw new IllegalArgumentException("Invalid input: "+ orientationInput);
        }
    }
    public int getDimensionWallInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter wall dimension:  ");
        int wallDimensionInput = scanner.nextInt();

        if (wallDimensionInput>=1 &&  wallDimensionInput<board.getRows() && wallDimensionInput<board.getColumns()) {
            return wallDimensionInput;
        }else {
            throw new IllegalArgumentException("Invalid wall dimension input: "+ wallDimensionInput);
        }
    }

    public void doMove(Player player, Direction direction) {
        player.getMeeple().setFinalMarginGivenInitial(player.getMeeple().getFinalMargin());
        if (!board.checkFinalMarginReached(player.getMeeple())) {
            board.move(player.getMeeple(), direction);
        }

    }
    public void doPlaceWall(Player player, Coordinates coordinates, Orientation orientation, int dimWall) {
        if (!board.checkFinalMarginReached(player.getMeeple())) {
            if (board.isWallPlaceableAdvanced(coordinates, orientation, dimWall, player)) {
                board.placeWall(coordinates, orientation, dimWall);
            }
        }
    }
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

    public String printGameState() {
        return printPlayersInfo() + printBoardInfo() + "\n";
    }


}