package com.oop.chess.model.pieces;

/**
 * This class represents a piece of the chess board.
 */
public interface Piece {
    // x and y coordinates of the piece on the board
    public int x = 0;
    public int y = 0;

    public boolean isWhite();
    /**
     * getLegalMoves() - Checks the board for all legal moves that this piece can do
     * @return int[][] - coordinates of all the tiles that the piece can legally move to
     **/
    public int[][] getLegalMoves();


}
