package com.oop.chess.model.pieces;

public class Pawn implements Piece {

    boolean isWhite;

    public Pawn(boolean white) {
        isWhite = white;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
