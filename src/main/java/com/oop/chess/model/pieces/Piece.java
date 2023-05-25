package com.oop.chess.model.pieces;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a piece of the chess board.
 */
public abstract class Piece {
    public PieceEnum pieceType;

    public boolean pawnPromotion;

    private boolean isCaptured = false;

    public int x = -1;
    public int y = -1;

    public boolean isWhite;

    public Piece() {

    }


    /**
     * Returns whether the piece is white.
     *
     * @return Returns whether the piece is white.
     */
    public boolean isWhite() {
        return this.isWhite;
    }


    /**
     * Returns the type of the piece.
     *
     * @return Returns the type of the piece.
     */
    public PieceEnum getType() {
        return pieceType;
    }


    /**
     * Sets the piece to be the boolean white.
     *
     * @param white The boolean to which the piece is set.
     */
    public void setWhite(boolean white) {
        this.isWhite = white;
    }


    /**
     * Sets the X-coordinate to the given X-coordinate.
     *
     * @param xCoord The given X-coordinate.
     */
    public void setX(int xCoord) {
        x = xCoord;
    }


    /**
     * Gets the X-coordinate of the piece.
     *
     * @return The X-coordinate of the piece.
     */
    public int getX() {
        return x;
    }


    /**
     * Sets the Y-coordinate to the given X-coordinate.
     *
     * @param yCoord The given Y-coordinate.
     */
    public void setY(int yCoord) {
        y = yCoord;
    }


    /**
     * Gets the Y-coordinate of the piece.
     *
     * @return The Y-coordinate of the piece.
     */
    public int getY() {
        return y;
    }


    /**
     * Returns whether the piece is captured.
     *
     * @return Whether the piece is captured.
     */
    public boolean isCaptured() {
        return this.isCaptured;
    }


    /**
     * Sets the capture variable to the given boolean.
     *
     * @param isCaptured The given boolean variable.
     */
    public void setCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }


    /**
     * Returns an identical piece.
     *
     * @return a new identical piece.
     */
    public Piece clone() {
        return null;
    }


    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @param finalX   The final X-coordinate of the piece.
     * @param finalY   The final Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        return null;
    }


    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int ii = 0; ii < 8; ii++) {
                ArrayList<int[]> moves = getLegalMoves(initialX, initialY, i, ii);

                if (moves != null) {
                    for (int[] m : moves) {
                        if (Game.xyInBounds(m[0], m[1])) {
                            boolean alreadyIn = false;

                            for (int[] item : legalMoves) {
                                if (Arrays.equals(m, item)) {
                                    alreadyIn = true;
                                    break;
                                }
                            }

                            if (!alreadyIn)
                                legalMoves.add(m);
                        }
                    }
                }
            }
        }

        return legalMoves;
    }


    /**
     * Makes a move from one to another tile.
     *
     * @param initialX The initial X-coordinate of the pawn.
     * @param initialY The initial Y-coordinate of the pawn.
     * @param finalX   The final X-coordinate of the pawn.
     * @param finalY   The final Y-coordinate of the pawn.
     */
    public void makeMove(int initialX, int initialY, int finalX, int finalY) {

    }


    /**
     * Checks whether the passed x is the X-coordinate of the piece.
     *
     * @param x The X-coordinate to be checked.
     * @return Returns whether the passed x is the X-coordinate of the piece.
     */
    public boolean hasX(int x) {
        return getX() == x;
    }


    /**
     * Checks whether the passed y is the Y-coordinate of the piece.
     *
     * @param y The Y-coordinate to be checked.
     * @return Returns whether the passed y is the Y-coordinate of the piece.
     */
    public boolean hasY(int y) {
        return getY() == y;
    }


    /**
     * Checks whether the current piece is equal to the passed on piece.
     *
     * @param obj The passed on piece.
     * @return Whether the current piece is equal to the passed on piece.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;

        return (pieceType == ((Piece) obj).pieceType && x == ((Piece) obj).x && y == ((Piece) obj).y);
    }


    /**
     * Gets a String of information about the current piece.
     *
     * @return The String with information about the current piece.
     */
    public String toString() {
        return "( " + pieceType + " : " + (isWhite ? "White" : "Black") + ", x=" + x + ", y=" + y + ")";
    }
}
