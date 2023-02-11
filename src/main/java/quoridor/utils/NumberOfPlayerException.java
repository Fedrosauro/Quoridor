package quoridor.utils;

public class NumberOfPlayerException extends Exception {

    public NumberOfPlayerException(int numberOfPlayer) {
        super("Number of player " + numberOfPlayer + " not valid: players less than 2 or equals to 3 or more than 5");
    }

}
