package com.oop.chess.model.pieces;

/**
 * This class represents a piece of the chess board.
 */
public abstract class Piece {
    // x and y coordinates of the piece on the board
    public int x = -1;
    public int y = -1;
    public boolean isWhite;

    public boolean isWhite() {
        return isWhite;
    }
    /**
     * getLegalMoves() - Checks the board for all legal moves that this piece can do
     * @return int[][] - coordinates of all the tiles that the piece can legally move to
     **/
    public abstract int[][] getLegalMoves();

    public int getX(Piece piece) {
        return x;
    }

    public int getY(Piece piece) {
        return y;
    }
}
