package quoridor.components;

import quoridor.utils.*;

import java.util.ArrayList;


public class Board {

    private int rows;
    private int columns;
    private Tile[][] matrix;
    private ArrayList<Wall> walls;
    private ArrayList<Meeple> meeples;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new Tile[rows][columns];

        //popolo la matrice di tile
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new Tile();
            }
        }
    }

    public Tile getPosition(int row, int column) throws PositionException {

        if (row >= rows || row < 0 || column >= columns || column < 0)
            throw new PositionException(row, column);

        else return matrix[row][column];
    }

    //data una tile mi ritorna le coordinate della tile
    public Coordinate findPosition(Tile tile) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (tile.equals(matrix[i][j])) {
                    return new Coordinate(j, i);
                }
            }
        }
        return null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    //dà la posizione centrata della prima riga(0, centro)
    public int centreOfLine(int number) {
        return ((number - 1) / 2);
    }


    public Tile setMeeplePositionGivenMargin(Meeple meeple, Margin margin) throws PositionException {
        switch (margin){
            case LEFT:
                meeple.setPosition(getPosition(centreOfLine(getRows()),0));
                return meeple.getPosition();
            case RIGHT:
                meeple.setPosition(getPosition(centreOfLine(getRows()),getColumns()-1));
                return meeple.getPosition();
            case TOP:
                meeple.setPosition(getPosition(0,centreOfLine(getColumns())));
                return meeple.getPosition();
            case BOTTOM:
                meeple.setPosition(getPosition(getRows()-1,centreOfLine(getColumns())));
                return meeple.getPosition();
        }
        return null;
    }

    public boolean isOdd() {
        return ((this.getRows() % 2) != 0 && (this.getColumns() % 2) != 0);
    }

    public boolean isEqual() {
        return (this.getColumns() == this.getRows());
    }


}