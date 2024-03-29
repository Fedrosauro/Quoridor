package quoridor.components;

import quoridor.exceptions.PositionException;
import quoridor.game.Player;
import quoridor.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

    private final Tile[][] matrix;
    private final int rows;
    private final int columns;
    private int wallId;
    private List<Meeple> meeples;

    public Board(int rows, int columns) {

        this.rows = rows;
        this.columns = columns;

        matrix = new Tile[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new Tile();
            }
        }
        this.wallId = 1;

        this.meeples = new ArrayList<>();

    }

    public Board(Tile[][] matrix, int wallId, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new Tile[matrix.length][matrix[0].length];

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                this.matrix[i][j] = new Tile();
                this.matrix[i][j].setNorthWall(matrix[i][j].getNorthWall());
                this.matrix[i][j].setEastWall(matrix[i][j].getEastWall());
            }
        }
        this.wallId = wallId;
    }

    public void setMeeples(List<Meeple> meeples) {
        this.meeples = meeples;
    }

    public void findFinalMargin(Meeple meeple) {
        Coordinates meeplePosition = this.findPosition(meeple.getPosition());
        int row = meeplePosition.getRow();
        int column = meeplePosition.getColumn();
        Margin finalMargin;

        if (row > column) {
            if (rows - row > column)
                finalMargin = Margin.RIGHT; //it means the meeple is in the left triangle, so it has to move to the right
            else finalMargin = Margin.TOP;
        } else {
            if (rows - row > column) finalMargin = Margin.BOTTOM;
            else finalMargin = Margin.LEFT;
        }

        meeple.setFinalPosition(finalMargin);

    }

    public Tile getPosition(int row, int column) throws PositionException {

        if (row >= rows || row < 0 || column >= columns || column < 0) throw new PositionException(row, column);

        else return matrix[row][column];
    }

    public Coordinates findPosition(Tile tile) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (tile.equals(matrix[i][j])) return new Coordinates(i, j);
            }
        }
        return null;
    }

    public void move(Meeple meeple, Direction direction) {

        Coordinates actualCoordinates = this.findPosition(meeple.getPosition());

        if (!thereIsNoWall(actualCoordinates, direction)) {
            return;
        }
        if (isOpponentClose(meeple, direction)) {
            Meeple opponent = getCloseOpponent(meeple, direction);
            if (thereIsNoWallBehindOpponent(opponent, direction) && !areTwoOpponentsAligned(meeple, direction)) {
                jump(meeple, direction, actualCoordinates);
            }
        } else step(meeple, direction, actualCoordinates);
    }

    public boolean areTwoOpponentsAligned(Meeple playerMeeple, Direction direction) {

        Meeple firstOpponent = getCloseOpponent(playerMeeple, direction);
        return isOpponentClose(firstOpponent, direction);

    }

    private void jump(Meeple meeple, Direction direction, Coordinates actualCoordinates) {
        moveWithStep(meeple, direction, actualCoordinates, 2);
    }

    private void step(Meeple meeple, Direction direction, Coordinates actualCoordinates) {
        moveWithStep(meeple, direction, actualCoordinates, 1);
    }

    private void moveWithStep(Meeple meeple, Direction direction, Coordinates actualCoordinates, int step) {
        switch (direction) {

            case RIGHT -> {
                if (actualCoordinates.getColumn() < columns - step)
                    meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() + step]);
            }
            case LEFT -> {
                if (actualCoordinates.getColumn() > (step - 1))
                    meeple.setPosition(matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() - step]);
            }
            case UP -> {
                if (actualCoordinates.getRow() < columns - step)
                    meeple.setPosition(matrix[actualCoordinates.getRow() + step][actualCoordinates.getColumn()]);
            }
            case DOWN -> {
                if (actualCoordinates.getRow() > (step - 1))
                    meeple.setPosition(matrix[actualCoordinates.getRow() - step][actualCoordinates.getColumn()]);
            }

        }
    }

    private boolean thereIsNoWallBehindOpponent(Meeple opponent, Direction direction) {
        Coordinates opponentCoordinates = this.findPosition(opponent.getPosition());
        return thereIsNoWall(opponentCoordinates, direction);
    }

    public boolean thereIsNoWall(Coordinates actualCoordinates, Direction direction) {

        Tile tile;

        switch (direction) {
            case RIGHT -> {

                if (actualCoordinates.getColumn() == columns - 1) return false;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if (tile.getEastWall() == null) return true;

            }
            case LEFT -> {

                if (actualCoordinates.getColumn() == 0) return false;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn() - 1];
                if (tile.getEastWall() == null) return true;

            }
            case UP -> {

                if (actualCoordinates.getRow() == rows - 1) return false;

                tile = matrix[actualCoordinates.getRow()][actualCoordinates.getColumn()];
                if (tile.getNorthWall() == null) return true;

            }
            case DOWN -> {

                if (actualCoordinates.getRow() == 0) return false;

                tile = matrix[actualCoordinates.getRow() - 1][actualCoordinates.getColumn()];
                if (tile.getNorthWall() == null) return true;

            }
        }

        return false;
    }

    public void doSequenceOfMoves(Meeple meeple, List<Direction> directions) {
        for (Direction direction : directions) {
            this.move(meeple, direction);
        }
    }

    public boolean isOpponentClose(Meeple meeple, Direction direction) {

        Coordinates coordinates = this.findPosition(meeple.getPosition());

        for (Meeple opponent : this.meeples) {

            if (meeple.getColor() != opponent.getColor()) {
                Coordinates opponentCoordinates = this.findPosition(opponent.getPosition());
                if (canMeepleBeJumpedOver(coordinates, opponentCoordinates, direction)) return true;
            }
        }

        return false;

    }

    public Meeple getCloseOpponent(Meeple meeple, Direction direction) {

        Coordinates coordinates = this.findPosition(meeple.getPosition());

        for (Meeple opponent : this.meeples) {

            Coordinates opponentCoordinates = this.findPosition(opponent.getPosition());
            if (canMeepleBeJumpedOver(coordinates, opponentCoordinates, direction)) return opponent;
        }

        return null;

    }

    private boolean canMeepleBeJumpedOver(Coordinates coordinates, Coordinates opponentCoordinates, Direction direction) {

        switch (direction) {
            case RIGHT -> {
                if (coordinates.getColumn() == opponentCoordinates.getColumn() - 1 && coordinates.getRow() == opponentCoordinates.getRow())
                    return true;
            }
            case LEFT -> {
                if (coordinates.getColumn() == opponentCoordinates.getColumn() + 1 && coordinates.getRow() == opponentCoordinates.getRow())
                    return true;
            }
            case UP -> {
                if (coordinates.getRow() == opponentCoordinates.getRow() - 1 && coordinates.getColumn() == opponentCoordinates.getColumn())
                    return true;
            }
            case DOWN -> {
                if (coordinates.getRow() == opponentCoordinates.getRow() + 1 && coordinates.getColumn() == opponentCoordinates.getColumn())
                    return true;
            }
        }

        return false;

    }


    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

    public void singleTileWallPlacement(Coordinates wallPosition, Orientation orientation) {
        Coordinates[] coordinates = new Coordinates[2];
        coordinates[0] = new Coordinates(wallPosition.getRow(), wallPosition.getColumn());

        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            coordinates[1] = new Coordinates(wallPosition.getRow() + 1, wallPosition.getColumn());
            matrix[wallPosition.getRow()][wallPosition.getColumn()].setNorthWall(new Wall(this.wallId, coordinates));
        } else if (orientation == Orientation.VERTICAL) {
            coordinates[1] = new Coordinates(wallPosition.getRow(), wallPosition.getColumn() + 1);
            matrix[wallPosition.getRow()][wallPosition.getColumn()].setEastWall(new Wall(this.wallId, coordinates));
        }
    }

    public void placeWall(Coordinates wallPosition, Orientation orientation, int dim) {
        Coordinates copiedPosition = new Coordinates(wallPosition.getRow(), wallPosition.getColumn());
        //in this way we don't compromise the initial coordinates that may be used later
        singleTileWallPlacement(copiedPosition, orientation);
        int i = 1;
        while (i < dim) {
            if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
                copiedPosition.setColumn(copiedPosition.getColumn() - 1);
            } else if (orientation == Orientation.VERTICAL) {
                copiedPosition.setRow(copiedPosition.getRow() - 1);
            }
            singleTileWallPlacement(copiedPosition, orientation);
            i++;
        }
        wallId++;
    }

    public boolean isWallNotPresent(Coordinates wallC, Orientation orientation, int dimension) {
        boolean result = true;
        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            for (int i = 0; i < dimension && result; i++) {
                result = matrix[wallC.getRow()][wallC.getColumn() - i].getNorthWall() == null;
            }
        } else if (orientation == Orientation.VERTICAL) {
            for (int i = 0; i < dimension && result; i++) {
                result = matrix[wallC.getRow() - i][wallC.getColumn()].getEastWall() == null;
            }
        }
        return result;
    }

    public boolean checkCross(List<Coordinates> arrListC) {
        return matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getNorthWall() != null && matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall() != null && matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall() != null && matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getEastWall() != null;
    }

    public boolean checkIllegalWallIdsCombination(List<Coordinates> arrListC) {
        ArrayList<Integer> idNumbers = new ArrayList<>();

        idNumbers.add(matrix[arrListC.get(2).getRow()][arrListC.get(2).getColumn()].getNorthWall().getId());

        if (!idNumbers.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getId())) {
            idNumbers.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getEastWall().getId());
        }

        if (!idNumbers.contains(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getId())) {
            idNumbers.add(matrix[arrListC.get(1).getRow()][arrListC.get(1).getColumn()].getNorthWall().getId());
        }

        if (!idNumbers.contains(matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getEastWall().getId())) {
            idNumbers.add(matrix[arrListC.get(0).getRow()][arrListC.get(0).getColumn()].getEastWall().getId());
        }

        return idNumbers.size() == 2;
    }

    public boolean checkWallOutOfBounds(Coordinates wallC, Orientation orientation, int dimension) {
        if (Objects.requireNonNull(orientation) == Orientation.VERTICAL) {
            return wallC.getRow() - dimension + 1 < 0;
        } else if (orientation == Orientation.HORIZONTAL) {
            return wallC.getColumn() - dimension + 1 < 0;
        }
        return false;
    }

    public boolean checkWallOnFirstRowOrLastColumn(Coordinates wallC, Orientation orientation) {
        if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
            return wallC.getRow() == matrix.length - 1;
        } else if (orientation == Orientation.VERTICAL) {
            return wallC.getColumn() == matrix[0].length - 1;
        }

        return false;

    }

    public Board cloneBoard() {
        return new Board(this.matrix, this.wallId, this.rows, this.columns);
    }

    public boolean isMatrixEqual(Board board) {
        boolean isEqual = true;
        for (int i = 0; i < this.matrix.length && isEqual; i++) {
            for (int j = 0; j < this.matrix[0].length && isEqual; j++) {
                isEqual = this.matrix[i][j].areTilesEqual(board.getMatrix()[i][j]);
            }
        }
        return isEqual && this.rows == board.getRows() && this.columns == board.getColumns();
    }

    public List<Coordinates[]> getAdjacencyOfLastWallPlaced(Coordinates wallPosition, Orientation orientation, int dimension) {
        Coordinates positionCopy = new Coordinates(wallPosition.getRow(), wallPosition.getColumn());
        ArrayList<Coordinates[]> adjacencyList = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            if (Objects.requireNonNull(orientation) == Orientation.HORIZONTAL) {
                adjacencyList.add(matrix[positionCopy.getRow()][positionCopy.getColumn()].getNorthWall().getAdjacency());
                positionCopy.setColumn(positionCopy.getColumn() - 1);
            } else if (orientation == Orientation.VERTICAL) {
                adjacencyList.add(matrix[positionCopy.getRow()][positionCopy.getColumn()].getEastWall().getAdjacency());
                positionCopy.setRow(positionCopy.getRow() - 1);
            }
        }
        return adjacencyList;
    }

    public boolean isWallPlaceable(Coordinates wallPosition, Orientation orientation, int dimension) {
        boolean placeable = !checkWallOutOfBounds(wallPosition, orientation, dimension) && !checkWallOnFirstRowOrLastColumn(wallPosition, orientation) && isWallNotPresent(wallPosition, orientation, dimension);

        if (placeable) {
            Board copyBoard = this.cloneBoard();
            copyBoard.placeWall(wallPosition, orientation, dimension); //because the wall is placeable

            List<Coordinates[]> adjacencyList = copyBoard.getAdjacencyOfLastWallPlaced(wallPosition, orientation, dimension);
            for (int i = 0; i < adjacencyList.size() - 1 && placeable; i++) {
                ArrayList<Coordinates> coordinatesOf2x2Tiles = new ArrayList<>();
                if (orientation == Orientation.HORIZONTAL) {
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i + 1)[1]); //top left
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i + 1)[0]); //bottom left
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i)[0]); //bottom right
                } else {
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i)[0]); //top left
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i + 1)[0]); //bottom left
                    coordinatesOf2x2Tiles.add(adjacencyList.get(i + 1)[1]); //bottom right
                }

                if (copyBoard.checkCross(coordinatesOf2x2Tiles)) {
                    placeable = !copyBoard.checkIllegalWallIdsCombination(coordinatesOf2x2Tiles);
                }
            }
        }

        return placeable;
    }

    public boolean checkFinalMarginReached(Meeple meeple) {

        Margin finalMargin = meeple.getFinalMargin();

        Coordinates meeplePosition = this.findPosition(meeple.getPosition());

        switch (finalMargin) {
            case TOP -> {
                if (meeplePosition.getRow() == 0) return true;
            }
            case BOTTOM -> {
                if (meeplePosition.getRow() == rows - 1) return true;
            }
            case LEFT -> {
                if (meeplePosition.getColumn() == 0) return true;
            }
            case RIGHT -> {
                if (meeplePosition.getColumn() == columns - 1) return true;
            }
        }

        return false;
    }

    public int centreOfLine(int number) {
        return ((number - 1) / 2);
    }

    public void setMeeplePosition(Meeple meeple) throws PositionException {
        switch (meeple.getInitialMargin()) {
            case LEFT -> meeple.setPosition(getPosition(centreOfLine(getRows()), 0));
            case RIGHT -> meeple.setPosition(getPosition(centreOfLine(getRows()), getColumns() - 1));
            case TOP -> meeple.setPosition(getPosition(0, centreOfLine(columns)));
            case BOTTOM -> meeple.setPosition(getPosition(getRows() - 1, centreOfLine(getColumns())));
        }

    }

    public boolean isOdd() {
        return ((this.getRows() % 2) != 0 && (this.getColumns() % 2) != 0);
    }

    public String printPathSolution(List<Coordinates> coordinates) {
        StringBuilder s = new StringBuilder();
        for (Coordinates position : coordinates) {
            s.append("[ ").append(position.getRow()).append(", ").append(position.getColumn()).append(" ] ");
        }
        return s.toString();
    }

    public Coordinates getMoveCoordinates(Player player) {
        ArrayList<Coordinates> path = new ArrayList<>();

        boolean winningPathExists = pathExistence(path, findPosition(player.getMeeple().getPosition()), player.getMeeple());
        if (winningPathExists) {
            return path.get(path.size() - 1);
        }
        return findPosition(player.getMeeple().getPosition());
    }

    public boolean isInsideBoard(int row, int column) {
        return row >= 0 && column >= 0 && row < matrix.length && column < matrix.length;
    }

    public List<String> printTile(int i, int j, List<Coordinates> playersPositions, List<Player> players) {
        String sAbove;
        String sUnder = "";
        ArrayList<String> result = new ArrayList<>();

        for (int k = 0; k < playersPositions.size(); k++) {
            if (i == playersPositions.get(k).getRow() && j == playersPositions.get(k).getColumn()) {
                sUnder = " " + players.get(k).getMeeple().getColor().toString().charAt(0);
                break;
            } else {
                sUnder = " O";
            }
        }

        if (this.matrix[i][j].getEastWall() != null) sUnder += " |";
        else sUnder += "  ";
        if (this.matrix[i][j].getNorthWall() != null) sAbove = "__  ";
        else sAbove = "    ";

        result.add(sAbove);
        result.add(sUnder);

        return result;
    }

    public String printTileRow(int row, List<Coordinates> playersPositions, List<Player> players) {
        StringBuilder sAbove = new StringBuilder();
        StringBuilder sUnder = new StringBuilder();
        List<String> tempResult;

        for (int j = 0; j < matrix.length; j++) {
            tempResult = printTile(row, j, playersPositions, players);
            sAbove.append(tempResult.get(0)).append("  ");
            sUnder.append(tempResult.get(1)).append("  ");
        }
        return "\n" + sAbove + "\n" + sUnder;
    }

    public String printEntireBoard(List<Player> playersList) {
        List<Coordinates> playersPositions = getPlayersPositions(playersList);

        StringBuilder s = new StringBuilder();
        for (int i = matrix.length - 1; i >= 0; i--) {
            s.append(printTileRow(i, playersPositions, playersList));
        }
        return s.toString();
    }

    public List<Coordinates> getPlayersPositions(List<Player> playersList) {
        ArrayList<Coordinates> playersPositions = new ArrayList<>();
        for (Player player : playersList) {
            playersPositions.add(findPosition(player.getMeeple().getPosition()));
        }
        return playersPositions;
    }

    public boolean checkFinalMarginCoordinatesReached(Coordinates position, Margin finalMargin) {
        switch (finalMargin) {
            case TOP -> {
                if (position.getRow() == 0) return true;
            }
            case BOTTOM -> {
                if (position.getRow() == rows - 1) return true;
            }
            case LEFT -> {
                if (position.getColumn() == 0) return true;
            }
            case RIGHT -> {
                if (position.getColumn() == columns - 1) return true;
            }
        }

        return false;
    }

    public boolean pathExistence(List<Coordinates> path, Coordinates position, Meeple meeple) {
        if (!isInsideBoard(position.getRow(), position.getColumn()) || matrix[position.getRow()][position.getColumn()].getVisitedTile())
            return false;

        path.add(position);
        matrix[position.getRow()][position.getColumn()].setVisitedTile();

        if (checkFinalMarginCoordinatesReached(position, meeple.getFinalMargin()) || (thereIsNoWall(position, Direction.UP) && pathExistence(path, new Coordinates(position.getRow() + 1, position.getColumn()), meeple)) || (thereIsNoWall(position, Direction.LEFT) && pathExistence(path, new Coordinates(position.getRow(), position.getColumn() - 1), meeple)) || (thereIsNoWall(position, Direction.RIGHT) && pathExistence(path, new Coordinates(position.getRow(), position.getColumn() + 1), meeple)) || (thereIsNoWall(position, Direction.DOWN) && pathExistence(path, new Coordinates(position.getRow() - 1, position.getColumn()), meeple)))
            return true;

        matrix[path.get(path.size() - 1).getRow()][path.get(path.size() - 1).getColumn()].resetVisitedTile();
        path.remove(path.size() - 1);

        return false;
    }

    public boolean checkWinningPath(Coordinates wallC, Orientation orientation, int dimension, Player player) {
        Board copyBoard = this.cloneBoard(); //created not to mess with the original board
        copyBoard.placeWall(wallC, orientation, dimension);
        ArrayList<Coordinates> path = new ArrayList<>();

        return copyBoard.pathExistence(path, findPosition(player.getMeeple().getPosition()), player.getMeeple());
    }

    public boolean isWallEventuallyPlaceable(Coordinates wallC, Orientation orientation, int dimension, Player player) {
        boolean placeable = isInsideBoard(wallC.getRow(), wallC.getColumn()) && !checkWallOutOfBounds(wallC, orientation, dimension) && isWallNotPresent(wallC, orientation, dimension) && !checkWallOnFirstRowOrLastColumn(wallC, orientation) && checkWinningPath(wallC, orientation, dimension, player);

        if (placeable) {
            Board copyBoard = this.cloneBoard();
            copyBoard.placeWall(wallC, orientation, dimension); //because the wall is placeable

            List<Coordinates[]> adjacency = copyBoard.getAdjacencyOfLastWallPlaced(wallC, orientation, dimension);
            for (int i = 0; i < adjacency.size() - 1 && placeable; i++) {
                ArrayList<Coordinates> coordinatesOf2x2Tiles = new ArrayList<>();
                if (orientation == Orientation.HORIZONTAL) {
                    coordinatesOf2x2Tiles.add(adjacency.get(i + 1)[1]); //top left
                    coordinatesOf2x2Tiles.add(adjacency.get(i + 1)[0]); //bottom left
                    coordinatesOf2x2Tiles.add(adjacency.get(i)[0]); //bottom right
                } else {
                    coordinatesOf2x2Tiles.add(adjacency.get(i)[0]); //top left
                    coordinatesOf2x2Tiles.add(adjacency.get(i + 1)[0]); //bottom left
                    coordinatesOf2x2Tiles.add(adjacency.get(i + 1)[1]); //bottom right
                }
                if (copyBoard.checkCross(coordinatesOf2x2Tiles)) {
                    placeable = !copyBoard.checkIllegalWallIdsCombination(coordinatesOf2x2Tiles);
                }
            }
        }

        return placeable;
    }
}