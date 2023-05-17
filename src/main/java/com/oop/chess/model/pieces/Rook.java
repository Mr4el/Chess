package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(boolean white, int i, int j) {
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

        //up moves
        for (int i = initialX + 1; i < 7; i++) {
            Piece piece = Game.getPiece(i, initialY);

            if (piece == null) {
                int[] move = {i, initialY};
                legalMoves.add(move);

            }

        }

        for (int i = initialX - 1; i > 0; i--) { //down

            Piece piece = Game.getPiece(i, initialY);

            if (piece == null) {
                int[] move = {i, initialY};
                legalMoves.add(move);

            }
        }
        for (int j = initialY + 1; j < 7; j++) {//right

            Piece piece = Game.getPiece(initialX, y);

            if (piece == null) {
                int[] move = {initialX, y};
                legalMoves.add(move);

            }
        }
        for (int j = initialY - 1; j > 0; j--) {//left
            Piece piece = Game.getPiece(initialX, j);

            if (piece == null) {
                int[] move = {initialX, j};
                legalMoves.add(move);

            }
        }

        return legalMoves;
    }


}
