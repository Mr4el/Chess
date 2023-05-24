package com.oop.chess.model.player;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

import java.util.ArrayList;

/**
 * This class represents the AI implementation of a player of the game.
 */
public class RandomAI extends Player {
    boolean white;
    boolean move = false;

    boolean help;

    /**
     * Craetes a new AI player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public RandomAI(boolean white, boolean help) {
        this.white = white;
        this.help = help;

        pieces = new ArrayList<Piece>();
    }

    /**
     * Determines the logic for the player's turn.
     *
     * @param piece The piece the player is allowed to move.
     * @return Since this method is to be used for the implementation in the second phase, it serves as a placeholder.
     */
    public boolean turn(PieceEnum piece) {
        return false;
    }

    /**
     * Sets the move of the player from the first two parameters to the second two parameters, but for now is just a placeholder for an implementation in the second phase.
     *
     * @param ox The X-coordinate from where the piece will be moved.
     * @param oy The Y-coordinate from where the piece will be moved.
     * @param nx The X-cooridnate to which the piece will be moved.
     * @param ny The Y-coordinate to which the piece will be moved.
     */
    public void setMove(int ox, int oy, int nx, int ny) {

    }


    public String toString() {
        return "(AI Player," + (white ? "White" : "Black") + ")";
    }
}
