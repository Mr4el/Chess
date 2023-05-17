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
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Knight(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        super.piece_type = PieceEnum.KNIGHT;
    }

    /**
     * Returns an identical Knight piece.
     * @return a new identical piece.
     */
    @Override
    public Knight clone() {
        return new Knight(isWhite, x, y);
    }

    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        if (finalX == initialX && finalY == initialY) {
            return legalMoves; //cannot move nothing
        }
        if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
            return legalMoves;
        }

        int[][] positions_to_check = {
            {   2,  1},
            {  -2,  1},
            {   2, -1},
            {  -2, -1},
            {   1,  2},
            {  -1,  2},
            {   1, -2},
            {  -1, -2}
        };

        for(int i = 0; i < positions_to_check.length; i++) {
            int[] p = positions_to_check[i];
            Piece existing_piece = Game.getPiece(initialX + p[0], initialY + p[1]);

            if (existing_piece == null || this.isWhite != existing_piece.isWhite) {
                int[] move = {initialX + p[0], initialY + p[1]};
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }
}
