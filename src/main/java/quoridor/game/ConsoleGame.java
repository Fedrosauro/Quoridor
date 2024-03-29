package quoridor.game;

import quoridor.utils.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleGame {

    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int WALLS = 20;
    private static final int WALL_DIM = 2;
    private static GameEngine gameEngine = null;

    public static void main(String[] args) {

        Player activePlayer;

        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");


        try {
            gameEngine = new GameEngine(NUMBER_OF_PLAYERS, names, ROWS, COLUMNS, WALLS, GameType.CONSOLE_GAME, OpponentType.HUMAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        welcomePlayers();

        while (!gameEngine.didActivePlayerWin()) {
            gameEngine.nextActivePlayer();
            activePlayer = gameEngine.getActivePlayer();
            printBoard();
            printActivePlayer();
            Action actionToPerform = askForWhichActionToPerform();

            if (actionToPerform == Action.MOVE_MEEPLE) {
                Direction direction;
                do {
                    direction = askForDirection();
                } while (!gameEngine.moveIsAllowed(activePlayer, direction));
                gameEngine.move(activePlayer, direction);
            } else {
                Orientation orientation;
                Coordinates position;
                do {
                    orientation = askForOrientation();
                    position = askForPosition();
                } while (!gameEngine.everyPlayerCanWin(position, orientation, WALL_DIM));
                gameEngine.placeWall(position, orientation, WALL_DIM);
            }

        }

        printBoard();
        printWinner();

    }

    private static void automaticTurn(AutoPlayer player) {
        Action action = player.decideActionToPerform();

        if (action == Action.MOVE_MEEPLE) gameEngine.autoMove(player);
        else gameEngine.autoPlace(player, WALL_DIM);

    }

    private static void printBoard() {
        System.out.println(gameEngine.getBoardStatus());
    }

    private static void printWinner() {

        System.out.println("-------------------------------");
        System.out.println("The winner is " + gameEngine.getActivePlayer().getName() + "!");
        System.out.println("-------------------------------");
    }

    private static Coordinates askForPosition() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Row: ");
        int row = scanner.nextInt();
        System.out.println();
        System.out.print("Column: ");
        int column = scanner.nextInt();
        System.out.println();

        return new Coordinates(row, column);
    }

    private static Orientation askForOrientation() {

        int orientationId;

        do {
            System.out.println("1. Horizontal");
            System.out.println("2. Vertical");
            System.out.print("How do you want to place the wall? ");
            Scanner scanner = new Scanner(System.in);
            orientationId = scanner.nextInt();
            System.out.println();
        } while (orientationId < 1 || orientationId > 2);

        if (orientationId == 1) return Orientation.HORIZONTAL;
        return Orientation.VERTICAL;
    }

    private static Direction askForDirection() {

        int directionId;

        do {
            System.out.println("1. Up");
            System.out.println("2. Down");
            System.out.println("3. Left");
            System.out.println("4. Right");
            System.out.print("Where do you want to move? ");
            Scanner scanner = new Scanner(System.in);
            directionId = scanner.nextInt();
            System.out.println();
        } while (directionId < 1 || directionId > 4);

        if (directionId == 1) return Direction.UP;
        if (directionId == 2) return Direction.DOWN;
        if (directionId == 3) return Direction.LEFT;
        return Direction.RIGHT;

    }

    private static Action askForWhichActionToPerform() {

        int actionId;

        do {
            System.out.println("1. Move");
            System.out.println("2. Place a wall");
            System.out.print("What do you want to do? ");
            Scanner scanner = new Scanner(System.in);
            actionId = scanner.nextInt();
        } while (actionId < 1 || actionId > 2);

        if (actionId == 1) return Action.MOVE_MEEPLE;
        return Action.PLACE_WALL;

    }

    private static void printActivePlayer() {

        Player player = gameEngine.getActivePlayer();
        String playerName = player.getName();
        String playerWalls = String.valueOf(player.getWalls());
        String color = getColorOf(player);

        System.out.println("--------------------");
        System.out.println(playerName + "'s turn: " + color);
        System.out.println(playerWalls + " walls remaining");
        System.out.println("--------------------");

    }

    private static void welcomePlayers() {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("\t \t  QUORIDOR");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }

    public static String getColorOf(Player player) {
        Color color = gameEngine.getColorOf(player);
        return String.valueOf(color.name().charAt(0));
    }

}



