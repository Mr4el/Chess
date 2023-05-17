package com.oop.chess.model.pieces;

import com.oop.chess.Game;

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
            return null; //cannot move nothing
        }
        if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
            return null;
        }

        Piece upRight = Game.getPiece(initialX + 1, initialY + 2);
        Piece upLeft = Game.getPiece(initialX - 1, initialY + 2);
        Piece rightUp = Game.getPiece(initialX + 2, initialY + 1);
        Piece rightDown = Game.getPiece(initialX + 2, initialY - 1);
        Piece downRight = Game.getPiece(initialX + 1, initialY - 2);
        Piece downLeft = Game.getPiece(initialX - 1, initialY - 2);
        Piece leftUp = Game.getPiece(initialX - 2, initialY + 1);
        Piece leftDown = Game.getPiece(initialX - 2, initialY - 1);


        if (upRight == null) {

            int[] move = {upRight.getX(), upRight.getY()};
            legalMoves.add(move);

        }

        if (upLeft == null) {

            int[] move = {upLeft.getX(), upLeft.getY()};
            legalMoves.add(move);

        }

        if (rightUp == null) {

            int[] move = {rightUp.getX(), rightUp.getY()};
            legalMoves.add(move);

        }

        if (rightDown == null) {

            int[] move = {rightDown.getX(), rightDown.getY()};
            legalMoves.add(move);


        }

        if (downRight == null) {

            int[] move = {downRight.getX(), downRight.getY()};
            legalMoves.add(move);

        }

        if (downLeft == null) {

            int[] move = {downLeft.getX(), downLeft.getY()};
            legalMoves.add(move);

        }

        if (leftUp == null) {

            int[] move = {leftUp.getX(), leftUp.getY()};
            legalMoves.add(move);

        }

        if (leftDown == null) {

            int[] move = {leftDown.getX(), leftDown.getY()};
            legalMoves.add(move);

        }

        return legalMoves;
    }
}
