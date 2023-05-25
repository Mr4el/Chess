package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;

/**
 * This class represents a Bishop piece of the chess board.
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop piece using the fact whether it is white or black and its initial location on the board.
     *
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting.
     * @param j The initial y-position on the board starting.
     */
    public Bishop(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        super.piece_type = PieceEnum.BISHOP;
    }

    /**
     * Returns an identical Bishop piece.
     *
     * @return a new identical piece.
     */
    @Override
    public Bishop clone() {
        return new Bishop(isWhite, x, y);
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

        // The positions a bishop can move to from its original position.
        int[][] directions_to_check = {
                {1, 1},
                {-1, 1},
                {1, -1},
                {-1, -1}
        };


        for (int i = 0; i < directions_to_check.length; i++) {
            int[] d = directions_to_check[i];

            int x = initialX + d[0];
            int y = initialY + d[1];

            boolean check = true;

            if (!Game.xyInBounds(x, y))
                continue;

            while (check) {
                Piece piece = Game.getPiece(x, y);
                boolean add_to_legal_moves = false;

                if (piece == null) {
                    add_to_legal_moves = true;
                } else if (piece.isWhite != isWhite) {
                    add_to_legal_moves = true;
                    check = false;
                } else
                    check = false;


                if (add_to_legal_moves) {
                    int[] move = {x, y};
                    legalMoves.add(move);
                }


                x += d[0];
                y += d[1];

                if (!Game.xyInBounds(x, y))
                    check = false;
            }
        }

        return legalMoves;
    }
}
