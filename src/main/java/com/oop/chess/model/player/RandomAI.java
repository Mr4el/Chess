package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents the AI implementation of a player of the game.
 */
public class RandomAI extends Player {
    boolean white;
    boolean move = false;
    boolean help;
    int oldTileX = 0;
    int oldTileY = 0;
    int newTileX = 0;
    int newTileY = 0;


    /**
     * Creates a new AI player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help  A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public RandomAI(boolean white, boolean help) {
        this.white = white;
        this.help = help;

        pieces = new ArrayList<>();
    }


    /**
     * Determines the logic for the player's turn.
     *
     * @param piece The piece the player is allowed to move.
     * @return Since this method is to be used for the implementation in the second phase, it serves as a placeholder.
     */
    public boolean turn(PieceEnum piece) {
        // Wait 1 millisecond so the screen can update
        ActionListener taskPerformer = evt -> RandomBot.randomPlay(Game.getLegalPiece());
        Timer timer = new Timer(100, taskPerformer);
        timer.setRepeats(false);
        timer.start();

        return true;
    }


    /**
     * Sets the move of the player from the first two parameters to the second two parameters, but for now is just a placeholder for an implementation in the second phase.
     *
     * @param ox The X-coordinate from where the piece will be moved.
     * @param oy The Y-coordinate from where the piece will be moved.
     * @param nx The X-coordinate to which the piece will be moved.
     * @param ny The Y-coordinate to which the piece will be moved.
     */
    public void setMove(int ox, int oy, int nx, int ny) {
        this.oldTileX = ox;
        this.oldTileY = oy;
        this.newTileX = nx;
        this.newTileY = ny;

        this.move = true;
    }


    /**
     * Checks whether the current AI player is white or black.
     *
     * @return Whether the current AI player is white or black.
     */
    public boolean isWhite() {
        return white;
    }


    /**
     * Gets a String of information about the current AI player.
     *
     * @return A String of information about the current AI player.
     */
    public String toString() {
        return "(AI Player," + (white ? "White" : "Black") + ")";
    }
}
