package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

/**
 * This class represents a Bishop piece of the chess board.
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Bishop(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }


    /**
     * Returns an identical Bishop piece.
     * @return a new identical piece.
     */
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
