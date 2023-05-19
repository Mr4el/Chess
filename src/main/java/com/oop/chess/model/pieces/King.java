package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.CastleMove;

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
            	int[][] positions_to_check = {
            			{1, 0},
                        {0,  + 1},
                        { + 1,  + 1},
                        { - 1, 0},
                        {0,  - 1},
                        { - 1,  - 1},
                        { - 1,  + 1},
                        { + 1,  - 1}
                };
            	
            	for (int i = 0; i < positions_to_check.length; i++) {
                    int[] p = positions_to_check[i];
                    
                    if (!Game.xyInBounds(initialX + p[0], initialY + p[1]))
                        continue;

                    Piece existing_piece = Game.getPiece(initialX + p[0], initialY + p[1]);

                    if (existing_piece == null || this.isWhite != existing_piece.isWhite) {
                        int[] move = {initialX + p[0], initialY + p[1]};
                        legalMoves.add(move);
                    }
                }
            }
        }

        return legalMoves;
    }
    
    public boolean canCastleLeft() {

        return this.castleLeft;

    }

    public boolean canCastleRight() {

        return this.castleRight;

    }

    public void castleCheck() {


        Piece rookWhite1 = Game.getPiece(7, 0);
        Piece rookWhite2 = Game.getPiece(7, 7);

        Piece rookBlack1 = Game.getPiece(0, 0);
        Piece rookBlack2 = Game.getPiece(0, 7);

        if (isWhite) {

            if (!(this.getX() == 0 && this.getY() == 4)) {

                CastleMove.cantCastleLeft();
                CastleMove.cantCastleRight();

            }

            if (!(rookWhite1.piece_type == PieceEnum.ROOK && rook.isWhite)) {
                CastleMove.cantCastleLeft();

            }
            if (!(rookWhite2.piece_type == PieceEnum.ROOK && rook.isWhite)) {

                CastleMove.cantCastleRight();
            }


        }

        if (!isWhite) {
            if ((this.getX() == 7 && this.getY() == 4)) {

                CastleMove.cantCastleLeft();
                CastleMove.cantCastleRight();

            }
            if (!(rookBlack1.piece_type == PieceEnum.ROOK && !rook.isWhite)) {

                CastleMove.cantCastleLeft();

            }
            if (!(rookBlack2.piece_type == PieceEnum.ROOK) && !rook.isWhite) {

                CastleMove.cantCastleRight();

            }
        }

    }

    public void makeCastleMove() {

        boolean castle = true;

        if (this.isWhite) {

            if (this.canCastleRight()) {

                for (int i = 6; i > 4 && castle; i--) {

                    Piece piece = Game.getPiece(7, i);
                    if (piece != null) {
                        castle = false;
                    }

                }
                if (castle) {

                    Game.movePieceTo(this.x, this.y, 7, 6);
                    Game.movePieceTo(7, 7, 7, 5);
                }
            }

            if (this.canCastleLeft()) {
                castle = true;
                for (int i = 1; i < 4 && castle; i++) {

                    Piece piece = Game.getPiece(7, i);
                    if (piece != null) {
                        castle = false;
                    }

                }
                if (castle) {
                    Game.movePieceTo(this.x, this.y, 7, 2);
                    Game.movePieceTo(0, 7, 7, 3);
                }
            }
        }


        if (!this.isWhite) {

            if (this.canCastleRight()) {

                castle = true;
                for (int i = 5; i < 7 && castle; i++) {

                    Piece piece = Game.getPiece(0, i);
                    if (piece != null) {
                        castle = false;
                    }
                }
                if (castle) {

                    Game.movePieceTo(this.x, this.y, 0, 6);
                    Game.movePieceTo(7, 0, 0, 5);
                }
            }
            if (this.canCastleLeft()) {

                castle = true;
                for (int i = 1; i < 4 && castle; i++) {

                    Piece piece = Game.getPiece(0, i);
                    if (piece != null) {
                        castle = false;
                    }
                }
                if (castle) {
                    Game.movePieceTo(this.x, this.y, 0, 2);
                    Game.movePieceTo(0, 0, 0, 3);
                }
            }
        }
    }
}


