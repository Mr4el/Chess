package com.oop.chess.model.pieces;

public class Rook implements Piece {

    boolean isWhite;

    public Rook(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
