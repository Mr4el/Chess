package com.oop.chess.model.pieces;

import java.util.ArrayList;

/**
 * This class represents a Queen piece of the chess board.
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Queen(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }

    /**
     * Returns an identical Queen piece.
     * @return a new identical piece.
     */
    @Override
    public Queen clone() {
        return new Queen(isWhite, x, y);
    }

    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {

        Bishop bishop = new Bishop(this.isWhite, this.x, this.y);
        Rook rook = new Rook(this.isWhite, this.x, this.y);

        ArrayList<int[]> bishopMoves = new ArrayList<>(bishop.getLegalMoves(initialX, initialY, finalX, finalY));
        ArrayList<int[]> rookMoves = new ArrayList<>(rook.getLegalMoves(initialX, initialY, finalX, finalY));

        bishopMoves.addAll(rookMoves);
        return bishopMoves;
    }
}
