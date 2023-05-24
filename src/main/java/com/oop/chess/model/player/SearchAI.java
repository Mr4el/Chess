package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.model.search.GameSearchTree;

import java.util.ArrayList;

/**
 * This class represents the AI implementation of a player of the game.
 */
public class SearchAI extends Player {
    boolean white;
    boolean move = false;
    boolean help;
    int oldtile_x = 0;
    int oldtile_y = 0;
    int newtile_x = 0;
    int newtile_y = 0;

    /**
     * Creates a new AI player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public SearchAI(boolean white, boolean help) {
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
    public boolean turn(Game.PieceEnum piece) {

        // If the clicker calls setMove(x,y,x,y), then the player can make their move
        if (move) {

            // Search to depth 2 for now
            int[] my_best_move = GameSearchTree.search(3,white);

            Game.movePieceTo(my_best_move[0], my_best_move[1], my_best_move[2], my_best_move[3]);

            move = false;
            // return true;
        }

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
        this.oldtile_x = ox;
        this.oldtile_y = oy;
        this.newtile_x = nx;
        this.newtile_y = ny;

        this.move = true;
    }

    public boolean isWhite() {
        return white;
    }

    public String toString() {
        return "(AI Player," + (white ? "White" : "Black") + ")";
    }
}