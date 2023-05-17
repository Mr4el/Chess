package com.oop.chess.model.pieces;

public class Knight implements Piece {

    boolean isWhite;

    public Knight(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
