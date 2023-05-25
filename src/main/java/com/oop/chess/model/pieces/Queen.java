package com.oop.chess.model.pieces;

import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;

/**
 * This class represents a Queen piece of the chess board.
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen piece using the fact whether it is white or black and its initial location on the board.
     *
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting.
     * @param j The initial y-position on the board starting.
     */
    public Queen(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        super.piece_type = PieceEnum.QUEEN;
    }

    /**
     * Returns an identical Queen piece.
     *
     * @return a new identical piece.
     */
    @Override
    public Queen clone() {
        return new Queen(isWhite, x, y);
    }

    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @param finalX The final X-coordinate of the piece.
     * @param finalY The final Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY) {
        Bishop bishop = new Bishop(this.isWhite, this.x, this.y);
        Rook rook = new Rook(this.isWhite, this.x, this.y);

        // Gets the legal moves of both a bishop and a rook piece, since these combined contain all the legal moves a Queen can make.
        ArrayList<int[]> bishopMoves = new ArrayList<>(bishop.getLegalMoves(initialX, initialY));
        ArrayList<int[]> rookMoves = new ArrayList<>(rook.getLegalMoves(initialX, initialY));

        bishopMoves.addAll(rookMoves);

        return bishopMoves;

    }
}
