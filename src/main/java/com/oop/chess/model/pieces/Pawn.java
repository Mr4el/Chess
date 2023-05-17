package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }


    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        Piece piece = Game.getPiece(finalX, finalY);

        if (isWhite) {                       //1 square movement and movement for taking piece for WHITE pieces
            if (finalX - initialX == 1) {
                if (finalY == initialY && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }

                if (Math.abs(initialY - finalY) == 1 && piece != null && !piece.isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }


            }
        }

        boolean b = initialX == 0 || initialX == 1 || initialX == 2 || initialX == 3 || initialX == 4 || initialX == 5 || initialX == 6 || initialX == 7;

        if (isWhite) { //2 square movement for WHITE pieces

            if (b && initialY == 1) {

                if (finalX - initialX == 2) {
                    if (finalY == initialY && piece == null) {

                        int[] move = {finalX, finalY};
                        legalMoves.add(move);
                    }
                }

            }

        }

        if (!isWhite) {          //1 square movement and movement for taking the piece for BLACK pieces
            if (finalX - initialX == -1) {
                if (finalY == initialY && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }

                if (Math.abs(initialY - finalY) == 1 && piece != null && piece.isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }


            }
        }

        if (!isWhite) {   //2 square movement for BLACK pieces
            if (b && initialY == 6) {

                if (finalX - initialX == -2) {
                    if (finalY == initialY && piece == null) {

                        int[] move = {finalX, finalY};
                        legalMoves.add(move);
                    }
                }

            }
        }

        return legalMoves;
    }
}
