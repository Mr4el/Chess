package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;

/**
 * This class represents a Pawn piece of the chess board.
 */
public class Pawn extends Piece {

    // This variable 'enPassantPossible' defines that the most recent move of the pawn is two squares and it is thus possible that it is captured.
    boolean enPassantPossible;

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
        enPassantPossible = false;

        super.piece_type = PieceEnum.PAWN;
    }

    /**
     * Returns an identical Pawn piece.
     * @return a new identical piece.
     */
    @Override
    public Pawn clone() {
        Pawn p = new Pawn(isWhite, x, y);
        p.enPassantPossible = enPassantPossible;
        return p;
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

        // This is the direction in which a pawn can move
        // For white, it's -1 because white pieces move  upwards, and for black it's 1 because black pieces move downwards
        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction =  1; // down

        // Check if there's a piece already at the final position
        Piece piece = Game.getPiece(finalX, finalY);

        // 1 tile movement
        if (finalY - initialY == direction) {
            // Same column, only move forwards if the space in front of the pawn is empty
            if (finalX == initialX && piece == null) {
                int[] move = {finalX, finalY};
                legalMoves.add(move);
            }

            // Capture a piece via diagonal movement
            if (Math.abs(initialX - finalX) == 1 && piece != null && piece.isWhite != isWhite) {
                int[] move = {finalX, finalY};
                legalMoves.add(move);
            }

            // En-passant movement
            Piece enPassantPiece = Game.getPiece(finalX, finalY - direction);
            if (enPassantPiece != null && enPassantPiece instanceof Pawn) {
                if (Math.abs(initialX - finalX) == 1 && ((Pawn) enPassantPiece).enPassantPossible && enPassantPiece.isWhite != isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }
            }
        }

        // 2 tile movement
        // Check if the pawn is on its first move
        boolean b;
        if (isWhite)
            b = (initialY == 6);
        else
            b = (initialY == 1);

        if (b) {
            if (finalY - initialY == 2*direction) {
                if (finalX == initialX && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }


    // Execute specific actions on a move
    public void makeMove(int initialX, int initialY, int finalX, int finalY) {
        Piece piece = Game.getPiece(finalX, finalY);

        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction =  1; // down

        // en passant is not possible (only moved by 1 tile)
        if (finalY - initialY == direction) {
            if (finalX == initialX && piece == null) {
                enPassantPossible = false;
            }
        }

        // check if enpassant is possible
        boolean b;
        if (isWhite)
            b = (initialY == 6);
        else
            b = (initialY == 1);

        if (b) {
            if (finalY - initialY == 2*direction) {
                if (finalX == initialX && piece == null) {
                    enPassantPossible = true;
                }
            }
        }

        // execute en passant
        Piece enPassantPiece = Game.getPiece(finalX, finalY - direction);
        if (enPassantPiece != null && enPassantPiece instanceof Pawn) {
            if (Math.abs(initialX - finalX) == 1 && ((Pawn) enPassantPiece).enPassantPossible && enPassantPiece.isWhite != isWhite) {
                // delete the en passant piece
                Game.board[finalY - direction][finalX] = null;
                Game.GUIdeletePiece(finalX, finalY - direction);
            }
        }
    }
}
