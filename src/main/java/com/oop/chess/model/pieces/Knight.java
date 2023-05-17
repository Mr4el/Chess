package com.oop.chess.model.pieces;

public class Knight extends Piece {

    public Knight(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }

    public int[][] getLegalMoves() {
        return null;
    }
}
