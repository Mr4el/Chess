package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.gui.HumanClicking;
import com.oop.chess.model.pieces.Piece;

import java.util.ArrayList;

/**
 * This class represents a human player.
 */
public class Human extends Player {

    boolean move;
    boolean white;
    boolean help;
    int oldtile_x = 0;
    int oldtile_y = 0;
    int newtile_x = 0;
    int newtile_y = 0;
    HumanClicking clicker = null;

    /**
     * Craetes a new human player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public Human(boolean white, boolean help) {
        this.white = white;
        this.help = help;

        pieces = new ArrayList<Piece>();
    }

    /**
     * Determines the logic for the player's turn.
     *
     * @param piece The piece the player is allowed to move.
     * @return Whether the player has made a move and thus ended their turn.
     */
    public boolean turn(PieceEnum piece) {
        // Add the human's clicker to the board
        if (clicker == null) {
            clicker = new HumanClicking(this);
        }
        clicker.enabled = true;

        // If the clicker calls setMove(x,y,x,y), then the player can make their move

        return false;
    }

    /**
     * Sets the move of the player from the first two parameters to the second two parameters.
     *
     * @param ox The X-coordinate from where the piece will be moved.
     * @param oy The Y-coordinate from where the piece will be moved.
     * @param nx The X-cooridnate to which the piece will be moved.
     * @param ny The Y-coordinate to which the piece will be moved.
     */
    public void setMove(int ox, int oy, int nx, int ny) {
        this.oldtile_x = ox;
        this.oldtile_y = oy;
        this.newtile_x = nx;
        this.newtile_y = ny;

        this.move = true;
    }

    /**
     * Returns whether the player has selected the help option.
     *
     * @return Whether the player has selected the help option.
     */
    public boolean hasHelp() {
        return help;
    }

    /**
     * Returns whether the current player is white or black.
     *
     * @return Whether the current player is white or black.
     */
    public boolean isWhite() {
        return white;
    }

    /**
     * Gets a String of information about the current human player.
     * @return A String of information about the current human player.
     */
    public String toString() {
        return "(Human Player," + (white ? "White" : "Black") + ")";
    }
}
