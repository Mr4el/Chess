package com.oop.chess.model.pieces;

public class Bishop implements Piece {

    boolean isWhite;

    public Bishop(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
