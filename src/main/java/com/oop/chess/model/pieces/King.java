package com.oop.chess.model.pieces;

import com.oop.chess.Game;

import java.util.ArrayList;

public class King extends Piece {
    boolean castle;
    private Rook Rook;

    public King(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
    }


    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        int changeX = Math.abs(finalX - initialX);
        int changeY = Math.abs(finalY - initialY);

        if (changeX <= 1 && changeY <= 1) {
            if (changeX != 0 && changeY != 0) {
                int[] move = {finalX, finalY};
                legalMoves.add(move);
            }
        }


        return legalMoves;
    }

    public boolean canCastle() {

        Piece rookWhite1 = Game.getPiece(0, 0);
        Piece rookWhite2 = Game.getPiece(0, 7);
        Piece rookBlack1 = Game.getPiece(7, 0);
        Piece rookBlack2 = Game.getPiece(7, 7);

        if (isWhite) {
            if ((this.getX() == 0 && this.getY() == 4) && (rookWhite1.equals(Rook) || rookWhite2.equals(Rook))) {
                castle = true;
            }
        }

        if (!isWhite) {
            if ((this.getX() == 7 && this.getY() == 4) && (rookBlack1.equals(Rook) || rookBlack2.equals(Rook))) {
                castle = true;
            }
        }
        return castle;
    }

}
