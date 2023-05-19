package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import dicechess.Pieces.Rook;

import java.util.ArrayList;

/**
 * This class represents a King piece of the chess board.
 */
public class King extends Piece {

	public static boolean castleRight = true;
    public static boolean castleLeft = true;
    public Rook rook;

    /**
     * Constructs a King piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public King(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        super.piece_type = PieceEnum.KING;
    }

    /**
     * Returns an identical King piece.
     * @return a new identical piece.
     */
    @Override
    public King clone() {
        return new King(isWhite, x, y);
    }


    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        int changeX = Math.abs(finalX - initialX);
        int changeY = Math.abs(finalY - initialY);

        if (changeX <= 1 && changeY <= 1) {
            if (!(finalX == initialX && finalY == initialY)) {
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
