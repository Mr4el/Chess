package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

/**
 * This class represents a Pawn piece of the chess board.
 */
public class Pawn extends Piece {

    /**
     * Constructs a Pawn piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Pawn(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }

    /**
     * Returns an identical Pawn piece.
     * @return a new identical piece.
     */
    @Override
    public Pawn clone() {
        return new Pawn(isWhite, x, y);
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

        if (isWhite) {//1 square movement and movement for taking piece for WHITE pieces

            Piece piece = Game.getPiece(finalX, finalY);

            if (finalY - initialY == -1) {
                if (finalX == initialX && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }

                if (Math.abs(initialX - finalX) == 1 && piece != null && !piece.isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }


            }
        }

        boolean b = ((initialX == 0 || initialX == 1 || initialX == 2 || initialX == 3 || initialX == 4 || initialX == 5 || initialX == 6 || initialX == 7) && initialY == 6);

        if (isWhite) { //2 square movement for WHITE pieces
            Piece piece = Game.getPiece(finalX, finalY);
            if (b == true) {

                if (finalY - initialY == -2) {
                    if (finalX == initialX && piece == null) {

                        int[] move = {finalX, finalY};
                        legalMoves.add(move);
                    }
                }

            }

        }

        if (!isWhite) {          //1 square movement and movement for taking the piece for BLACK pieces
            Piece piece = Game.getPiece(finalX, finalY);
            if (finalY - initialY == 1) {
                if (finalX == initialX && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }

                if (Math.abs(initialX - finalX) == 1 && piece != null && piece.isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }


            }
        }
        boolean a = ((initialX == 0 || initialX == 1 || initialX == 2 || initialX == 3 || initialX == 4 || initialX == 5 || initialX == 6 || initialX == 7) && initialY == 1);
        if (!isWhite) {   //2 square movement for BLACK pieces
            Piece piece = Game.getPiece(finalX, finalY);
            if (a == true) {

                if (finalY - initialY == 2) {
                    if (finalX == initialX && piece == null) {

                        int[] move = {finalX, finalY};
                        legalMoves.add(move);
                    }
                }

            }
        }

        return legalMoves;
    }
}
