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
    
    boolean moved = false;  // has king moved yet?
    int castleMoveLeftX = -1;
    int castleMoveRightX = -1;

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
    	King k = new King(isWhite, x, y);
        k.moved = true;
        return k;
    }


    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        int changeX = Math.abs(finalX - initialX);
        int changeY = Math.abs(finalY - initialY);

        if (!(finalX == initialX && finalY == initialY)) {

            int[][] positions_to_check = {
                    { 1,  0},
                    { 0,  1},
                    { 1,  1},
                    {-1,  0},
                    { 0, -1},
                    {-1, -1},
                    {-1,  1},
                    { 1, -1}
            };

            // check if castle can be performed
            if (!this.moved) {

                castleCheck(initialX, initialY);

                if (canCastleLeft()) {
                    if (Game.getPiece(initialX-2,initialY) == null) {
                        int[] m = {initialX-2,initialY};
                        legalMoves.add(m);
                    }
                }

                if (canCastleRight()) {
                    if (Game.getPiece(initialX+2,initialY) == null) {
                        int[] m = {initialX+2,initialY};
                        legalMoves.add(m);
                    }
                }
            }


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

        return legalMoves;
    }
    
    public boolean canCastleLeft() {

        return this.castleLeft;

    }

    public boolean canCastleRight() {

        return this.castleRight;

    }

    public void castleCheck(int initialX, int initialY) {
        int check_y;

        if (isWhite) 
            check_y = 7;
        else
            check_y = 0;

        // left rook
        Piece rook1 = Game.getPiece(0,check_y);
        // right rook
        Piece rook2 = Game.getPiece(7,check_y);

        if (this.moved) {
            CastleMove.cantCastleLeft();
            CastleMove.cantCastleRight();
        }

        boolean piece_in_the_way_left = false;
        check_left:
        for(int i = initialX-1; i > 0; i--) {
            Piece p = Game.getPiece(i, check_y);
            if (p != null){
                piece_in_the_way_left = true;
                break check_left;
            }
        }
        if (piece_in_the_way_left || !(rook1 != null && rook1.piece_type == PieceEnum.ROOK && rook1.isWhite == this.isWhite && !((Rook)rook1).moved)) {
            CastleMove.cantCastleLeft();
        }

        boolean piece_in_the_way_right = false;
        check_right:
        for(int i = initialX+1; i < 7; i++) {
            Piece p = Game.getPiece(i, check_y);
            if (p != null){
                piece_in_the_way_right = true;
                break check_right;
            }
        }
        if (piece_in_the_way_right || !(rook2 != null && rook2.piece_type == PieceEnum.ROOK && rook2.isWhite == this.isWhite && !((Rook)rook2).moved)) {
            CastleMove.cantCastleRight();
        }

    }

    public void makeCastleMove(int ix, int iy, int fx, int fy) {
        if (fy != iy)
            return;

        // if castled right, move rook to the left of king
        if (fx - ix == 2) 
            Game.movePieceTo(7, iy, fx-1, fy);
        // if castled left, move rook to the right of king
        if (fx - ix == -2) 
            Game.movePieceTo(0, iy, fx+1, fy);
    }

    public void makeMove(int initialX, int initialY, int finalX, int finalY) {
        makeCastleMove(initialX, initialY, finalX, finalY);
    }
}


