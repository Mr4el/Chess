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

    // Represents whether the king has already moved.
    boolean moved = false;


    /**
     * Constructs a King piece using the fact whether it is white or black and its initial location on the board.
     *
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param x     The initial x-position on the board.
     * @param y     The initial y-position on the board.
     */
    public King(boolean white, int x, int y) {
        super.isWhite = white;
        super.x = x;
        super.y = y;
        super.pieceType = PieceEnum.KING;
    }


    /**
     * Returns an identical King piece.
     *
     * @return a new identical piece.
     */
    @Override
    public King clone() {
        King k = new King(isWhite, x, y);
        k.moved = true;
        return k;
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

        int[][] positionsToCheck = {
            {1, 0},
            {0, 1},
            {1, 1},
            {-1, 0},
            {0, -1},
            {-1, -1},
            {-1, 1},
            {1, -1}
        };

        // check if castle can be performed
        if (!this.moved) {

            castleCheck(initialX, initialY);

            if (canCastleLeft()) {
                if (Game.getPiece(initialX - 2, initialY) == null) {
                    int[] m = {initialX - 2, initialY};
                    legalMoves.add(m);
                }
            }

            if (canCastleRight()) {
                if (Game.getPiece(initialX + 2, initialY) == null) {
                    int[] m = {initialX + 2, initialY};
                    legalMoves.add(m);
                }
            }
        }


        for (int[] p : positionsToCheck) {
            if (!Game.xyInBounds(initialX + p[0], initialY + p[1]))
                continue;

            Piece existing_piece = Game.getPiece(initialX + p[0], initialY + p[1]);

            if (existing_piece == null || this.isWhite != existing_piece.isWhite) {
                int[] move = {initialX + p[0], initialY + p[1]};
                legalMoves.add(move);
            }
        }


        return legalMoves;
    }


    /**
     * Returns whether the king can castle left.
     *
     * @return Returns whether the king can castle left.
     */
    public boolean canCastleLeft() {
        return castleLeft;
    }


    /**
     * Returns whether the king can castle right.
     *
     * @return Returns whether the king can castle right.
     */
    public boolean canCastleRight() {
        return castleRight;
    }


    /**
     * Checks whether the king can castle.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     */
    public void castleCheck(int initialX, int initialY) {
        int checkY;

        if (isWhite)
            checkY = 7;
        else
            checkY = 0;

        // left rook
        Piece rook1 = Game.getPiece(0, checkY);
        // right rook
        Piece rook2 = Game.getPiece(7, checkY);

        if (this.moved) {
            CastleMove.cantCastleLeft();
            CastleMove.cantCastleRight();
        }

        boolean pieceInTheWayLeft = false;
        for (int i = initialX - 1; i > 0; i--) {
            Piece p = Game.getPiece(i, checkY);
            if (p != null) {
                pieceInTheWayLeft = true;
                break;
            }
        }
        if (pieceInTheWayLeft || !(rook1 != null && rook1.pieceType == PieceEnum.ROOK && rook1.isWhite == this.isWhite && !((Rook) rook1).moved)) {
            CastleMove.cantCastleLeft();
        }

        boolean pieceInTheWayRight = false;
        for (int i = initialX + 1; i < 7; i++) {
            Piece p = Game.getPiece(i, checkY);
            if (p != null) {
                pieceInTheWayRight = true;
                break;
            }
        }
        if (pieceInTheWayRight || !(rook2 != null && rook2.pieceType == PieceEnum.ROOK && rook2.isWhite == this.isWhite && !((Rook) rook2).moved)) {
            CastleMove.cantCastleRight();
        }

    }


    /**
     * Makes a castle move from one to another tile.
     *
     * @param ix The initial X-coordinate of the piece.
     * @param iy The initial Y-coordinate of the piece.
     * @param fx The final X-coordinate of the piece.
     * @param fy The final Y-coordinate of the piece.
     */
    public void makeCastleMove(int ix, int iy, int fx, int fy) {
        if (fy != iy)
            return;

        // if castled right, move rook to the left of king
        if (fx - ix == 2)
            Game.movePieceTo(7, iy, fx - 1, fy);
        // if castled left, move rook to the right of king
        if (fx - ix == -2)
            Game.movePieceTo(0, iy, fx + 1, fy);
    }


    /**
     * Makes a castle move from one tile to another.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @param finalX   The final X-coordinate of the piece.
     * @param finalY   The final Y-coordinate of the piece.
     */
    public void makeMove(int initialX, int initialY, int finalX, int finalY) {
        makeCastleMove(initialX, initialY, finalX, finalY);
    }
}


