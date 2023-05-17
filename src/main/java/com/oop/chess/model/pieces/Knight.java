package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
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
