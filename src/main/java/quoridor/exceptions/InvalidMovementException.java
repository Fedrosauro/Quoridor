package quoridor.exceptions;

import quoridor.utils.Direction;

public class InvalidMovementException extends Exception {

    public InvalidMovementException(Direction direction) {
        super("Impossible to move the meeple in direction " + direction.name());
    }

}
