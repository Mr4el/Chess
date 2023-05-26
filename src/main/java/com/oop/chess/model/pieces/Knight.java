package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;

/**
 * This class represents a Knight piece of the chess board.
 */
public class Knight extends Piece {


    /**
     * Constructs a Knight piece using the fact whether it is white or black and its initial location on the board.
     *
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param x     The initial x-position on the board starting.
     * @param y     The initial y-position on the board starting.
     */
    public Knight(boolean white, int x, int y) {
        super.isWhite = white;
        super.x = x;
        super.y = y;
        super.pieceType = PieceEnum.KNIGHT;
    }


    /**
     * Returns an identical Knight piece.
     *
     * @return a new identical piece.
     */
    @Override
    public Knight clone() {
        return new Knight(isWhite, x, y);
    }


    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        // The positions to which a knight can possibly move from its starting point.
        int[][] positionsToCheck = {
            {2, 1},
            {-2, 1},
            {2, -1},
            {-2, -1},
            {1, 2},
            {-1, 2},
            {1, -2},
            {-1, -2}
        };

        for (int[] p : positionsToCheck) {
            if (Game.xyInBounds(x + p[0], y + p[1])) {
                Piece existingPiece = Game.getPiece(x + p[0], y + p[1]);

                if (existingPiece == null || this.isWhite != existingPiece.isWhite) {
                    int[] move = {x + p[0], y + p[1]};
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }
}
