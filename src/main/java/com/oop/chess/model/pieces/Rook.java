package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a Rook piece of the chess board.
 */
public class Rook extends Piece {
	
	public static boolean castleRight = true;
	public static boolean castleLeft = true;
	boolean moved = false;

    /**
     * Constructs a Rook piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Rook(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        super.piece_type = PieceEnum.ROOK;
    }

    /**
     * Returns an identical Rook piece.
     * @return a new identical piece.
     */
    @Override
    public Rook clone() {
        return new Rook(isWhite, x, y);
    }

    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

//  		if (finalX == initialX && finalY == initialY) {
//      		return legalMoves; //cannot move nothing
//  		}
//  		if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
//      		return legalMoves;
//  		}    

        // right
        boolean noMoves = true;
        for (int i = initialX + 1; i <= 7 && noMoves; i++) {

        	if (!Game.xyInBounds(i, initialY))
                continue;
        	
            Piece piece = Game.getPiece(i, initialY);
            if (piece == null) {
                int[] move = {i, initialY};
                legalMoves.add(move);

            } else {

                if (this.isWhite && !piece.isWhite) {
                    int[] move = {i, initialY};
                    legalMoves.add(move);
                } else if (!this.isWhite && piece.isWhite) {
                    int[] move = {i, initialY};
                    legalMoves.add(move);
                }
                noMoves = false;
            }


        }

        // left
        noMoves = true;
        for (int i = initialX - 1; i >= 0 && noMoves; i--) {
        	if (!Game.xyInBounds(i, initialY))
                continue;
            Piece piece = Game.getPiece(i, initialY);
            if (piece == null) {
                int[] move = {i, initialY};
                legalMoves.add(move);

            } else {

                if (this.isWhite && !piece.isWhite) {
                    int[] move = {i, initialY};
                    legalMoves.add(move);
                } else if (!this.isWhite && piece.isWhite) {
                    int[] move = {i, initialY};
                    legalMoves.add(move);
                }
                noMoves = false;
            }

        }

        // down
        noMoves = true;
        for (int i = initialY + 1; i <= 7 && noMoves; i++) {
        	if (!Game.xyInBounds(initialX, i))
                continue;
            Piece piece = Game.getPiece(initialX, i);
            if (piece == null) {
                int[] move = {initialX, i};
                legalMoves.add(move);

            } else {

                if (this.isWhite && !piece.isWhite) {
                    int[] move = {initialX, i};
                    legalMoves.add(move);
                } else if (!this.isWhite && piece.isWhite) {
                    int[] move = {initialX, i};
                    legalMoves.add(move);
                }
                noMoves = false;
            }

        }

        // up
        noMoves = true;
        for (int i = initialY - 1; i >= 0 && noMoves; i--) {
        	if (!Game.xyInBounds(initialX, i))
                continue;
        	Piece piece = Game.getPiece(initialX, i);
            if (piece == null) {
                int[] move = {initialX, i};
                legalMoves.add(move);

            } else {

                if (this.isWhite && !piece.isWhite) {
                    int[] move = {initialX, i};
                    legalMoves.add(move);
                } else if (!this.isWhite && piece.isWhite) {
                    int[] move = {initialX, i};
                    legalMoves.add(move);
                }
                noMoves = false;
            }

        }
        return legalMoves;
    }

}
