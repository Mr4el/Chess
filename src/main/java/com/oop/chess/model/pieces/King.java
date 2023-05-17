package com.oop.chess.model.pieces;

public class King implements Piece {

    boolean isWhite;

    public King(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
