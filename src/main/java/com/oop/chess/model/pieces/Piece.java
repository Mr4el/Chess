package com.oop.chess.model.pieces;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.Game;
import com.oop.chess.model.Cell;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a piece of the chess board.
 */
public abstract class Piece {
    public PieceEnum piece_type;
    
    public boolean pawnPromotion;

    // x and y coordinates of the piece on the board

    public Cell currentCell;
    private boolean isCaptured = false;
    public int x = -1;
    public int y = -1;
    public boolean isWhite;

    public Piece() {

    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public PieceEnum getType() {
        return piece_type;
    }

    public void setWhite(boolean white) {
        this.isWhite = white;
    }

    public void setX(int xCoord) {
        x = xCoord;
    }

    public int getX() {
        return x;
    }

    public void setY(int yCoord) {
        y = yCoord;
    }

    public int getY() {
        return y;
    }


    public boolean isCaptured() {
        return this.isCaptured;
    }

    public void setCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    /**
     * Returns an identical piece.
     * @return a new identical piece.
     */
    public Piece clone() {
        return null;
    }

    /**
     * getLegalMoves() - Checks the board for all legal moves that this piece can do
     *
     * @return int[][] - coordinates of all the tiles that the piece can legally move to
     **/
    public abstract ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY);

    public ArrayList<int[]> getLegalMoves(int initialX, int initialY) {
        ArrayList<int[]> legalMoves = new ArrayList<int[]>();

        for(int i = 0; i < 8; i++){
            for(int ii = 0; ii < 8; ii++){
                ArrayList<int[]> moves = getLegalMoves(initialX, initialY, i, ii);

                if (moves != null) {
                    for(var iii = 0; iii < moves.size(); iii++){
                        int[] m = moves.get(iii);
                        if (Game.xyInBounds(m[0], m[1])){
                            boolean already_in = false;

                            check_duplicate:
                            for(int[] item : legalMoves){
                                if(Arrays.equals(m, item)){
                                    already_in = true;
                                    break check_duplicate;
                                }
                            }
                            
                            if (!already_in)
                                legalMoves.add(m);
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    public void makeMove(int initialX, int initialY, int finalX, int finalY) {

    }
    
    public boolean hasX(int x) {
        if (getX() == x) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean hasY(int y) {
        if (getY() == y) {
            return true;
        }
        else {
            return false;
        }
    }
}
