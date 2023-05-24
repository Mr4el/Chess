package com.oop.chess.model.player;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

import java.util.ArrayList;

/**
 * This interface represents a single player of the game.
 */
public abstract class Player {
    boolean white = true;
    boolean move = false;
    boolean help = true;

    public ArrayList<Piece> pieces;

    /**
     * Determines the logic for the player's turn.
     *
     * @param piece The piece the player is allowed to move.
     * @return Whether the player has made a move and thus ended their turn.
     */
    public boolean turn(PieceEnum piece) {
        return true;
    };

    /**
     * Returns whether the current player is white or black.
     *
     * @return Whether the current player is white or black.
     */
    public boolean isWhite() {
        return white;
    };

    /**
     * Sets the move of the player from the first two parameters to the second two parameters.
     *
     * @param ox The X-coordinate from where the piece will be moved.
     * @param oy The Y-coordinate from where the piece will be moved.
     * @param nx The X-cooridnate to which the piece will be moved.
     * @param ny The Y-coordinate to which the piece will be moved.
     */
    public void setMove(int ox, int oy, int nx, int ny) {

    };

}