package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.model.search.Expectimax;
import com.oop.chess.model.search.GameSearchTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    public static enum ALGORITHMS {
        MINIMAX,
        MINIMAX_ALPHABETA,
        EXPECTIMAX
    }

    static ALGORITHMS ALGORITHM = ALGORITHMS.MINIMAX_ALPHABETA;    // 0 = minimax, 1 = minimax + alphabeta, 2 = expectimax
    final int DEPTH = 3;

    public static void setAlgorithm(ALGORITHMS algorithm) {
        ALGORITHM = algorithm;
    }

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
        // Search to depth 2 for now
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                searchForMove();
            }
        };
        Timer timer = new Timer(1,taskPerformer);
        timer.setRepeats(false);
        timer.start();


        return false;
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

    private void searchForMove() {
        System.out.println(DEPTH);

        int[] my_best_move = new int[4];

        switch(ALGORITHM) {
            // Minimax without alphabeta
            case MINIMAX:
                my_best_move = minimax(false);
                break;

            // Minimax with alphabeta
            case MINIMAX_ALPHABETA:
                my_best_move = minimax(true);
                break;

            // Expectimax
            case EXPECTIMAX:
                my_best_move = expectimax();
                break;
        }

        Game.movePieceTo(my_best_move[0], my_best_move[1], my_best_move[2], my_best_move[3]);
    }

    private int[] minimax(boolean alphabeta) {
        return GameSearchTree.search(DEPTH, white, alphabeta);
    }

    private int[] expectimax() {
        return Expectimax.expectimaxSearch(DEPTH, white);
    }
}