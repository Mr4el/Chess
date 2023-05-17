package com.oop.chess.model.pieces;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
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
