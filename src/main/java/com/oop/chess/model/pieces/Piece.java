package com.oop.chess.model.pieces;

import com.oop.chess.model.Cell;

import java.util.ArrayList;

/**
 * This class represents a piece of the chess board.
 */
public abstract class Piece {

    // x and y coordinates of the piece on the board

    public Cell currentCell;
    private boolean isCaptured = false;
    public int x = -1;
    public int y = -1;
    public boolean isWhite;

    public Piece() {

    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public void setWhite(boolean white) {
        this.isWhite = white;
    }

    public void setX(int xCoord) {
        x = xCoord;
    }

    public int getX() {
        return x;
    }

    public void setY(int yCoord) {
        y = yCoord;
    }

    public int getY() {
        return y;
    }


    public boolean isCaptured() {
        return this.isCaptured;
    }

    public void setCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }


    /**
     * getLegalMoves() - Checks the board for all legal moves that this piece can do
     *
     * @return int[][] - coordinates of all the tiles that the piece can legally move to
     **/
    public abstract ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY);
}
