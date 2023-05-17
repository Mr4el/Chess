package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }


    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        if (finalX == initialX && finalY == initialY) {
            return null; //cannot move nothing
        }
        if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
            return null;
        }

        if (initialX - finalX != initialY - finalY) {
            return null;
        }

        for (int i = initialX + 1, j = initialY + 1; i < finalX && j < finalY; i++, j++) { //NE movement
            Piece piece = Game.getPiece(i, j);

            if (piece == null) {
                int[] move = {i, j};
                legalMoves.add(move);

            }
        }

        for (int i = initialX + 1, j = initialY - 1; i < finalX && j > 0; i++, j--) {//SE movement
            Piece piece = Game.getPiece(i, j);

            if (piece == null) {
                int[] move = {i, j};
                legalMoves.add(move);

            }
        }

        for (int i = initialX - 1, j = initialY + 1; i > 0 && j < finalY; i--, j++) { //SW movement
            Piece piece = Game.getPiece(i, j);

            if (piece == null) {
                int[] move = {i, j};
                legalMoves.add(move);

            }
        }

        for (int i = initialX - 1, j = initialY - 1; i > 0 && j > 0; i--, j--) { //NW movement
            Piece piece = Game.getPiece(i, j);

            if (piece == null) {
                int[] move = {i, j};
                legalMoves.add(move);

            }
        }

        return legalMoves;
    }
}
