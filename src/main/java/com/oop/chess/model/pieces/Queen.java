package com.oop.chess.model.pieces;

public class Queen implements Piece {

    boolean isWhite;

    public Queen(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
