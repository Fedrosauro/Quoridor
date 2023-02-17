package quoridor.game;

import quoridor.components.Board;
import quoridor.components.Meeple;
import quoridor.utils.*;

import java.util.List;
import java.util.Random;

public class AutoPlayer extends Player {

    private final int WALL_OFFSET_FROM_OPPONENT = 1;
    private float moveProbability;

    public AutoPlayer(String name, Meeple meeple, int walls, float moveProbability, Board board) {
        super(name, meeple, walls);

        if (moveProbability > 1) this.moveProbability = 1;
        else if (moveProbability < 0) this.moveProbability = 0;
        else this.moveProbability = moveProbability;

    }

    public float getMoveProbability() {
        return moveProbability;
    }

    public Action decideActionToPerform() {

        Random random = new Random();
        if (random.nextFloat() <= moveProbability) return Action.MOVEMEEPLE;
        else return Action.PLACEWALL;

    }

    public Direction decideDirection(Coordinates currentPosition, Board board) throws PositionException {
        Coordinates desiredPosition = board.getMoveCoordinates(this);

        if (currentPosition.getRow() == desiredPosition.getRow() - 1) return Direction.UP;
        if (currentPosition.getRow() == desiredPosition.getRow() + 1) return Direction.DOWN;
        if (currentPosition.getColumn() == desiredPosition.getColumn() - 1) return Direction.RIGHT;
        if (currentPosition.getColumn() == desiredPosition.getColumn() + 1) return Direction.LEFT;

        throw new PositionException(currentPosition.getRow(), currentPosition.getColumn());

    }

    public Orientation decideWallOrientation(List<Player> opponents) {

        Random r = new Random();
        if (r.nextInt(1) == 1) return Orientation.HORIZONTAL;
        else return Orientation.VERTICAL;

    }

    public Coordinates decideWallPosition(List<Player> opponents, Board board) {

        Random r = new Random();

        Player opponentToConsider = opponents.get(r.nextInt(opponents.size()));
        Coordinates positionToConsider = board.findPosition(opponentToConsider.getMeeple().getPosition());

        int column = positionToConsider.getColumn() + r.nextInt(WALL_OFFSET_FROM_OPPONENT + 1);
        int row = positionToConsider.getRow() + r.nextInt(WALL_OFFSET_FROM_OPPONENT + 1);

        return new Coordinates(row, column);

    }

}