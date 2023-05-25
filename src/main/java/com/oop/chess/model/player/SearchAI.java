package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.model.search.Expectimax;
import com.oop.chess.model.search.GameSearchTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents the AI implementation of a player of the game.
 */
public class SearchAI extends Player {
    boolean white;
    boolean move = false;
    boolean help;
    public boolean ML_component;
    int oldtile_x = 0;
    int oldtile_y = 0;
    int newtile_x = 0;
    int newtile_y = 0;

    //public static double[] weights = {0, 200, 9, 5, 3, 3, 1, 1};
    public static double[] weights = {0.008540710361972362,200.0,6.531346297830447,2.7811509905542304,1.0751178187426864,1.0238338993612515,0.3202340904449179,-0.046752062692078766};

    //public static double[] weights = {1,1,1,1,1,1,1,1};
    public static double[] weights_Minimax_without_ML_white = {1.786646829470027,201.7781061191081,8.309452416938491,4.559257109662285,2.8532239378507436,2.801940018469309,2.0983402095529735,1.7313540564159757};
    //public static double[] weights_Minimax_without_ML_white = {-0.15783604453542877,200.0,-0.33245414299726556,-1.4035058126088686,-0.22420600607601932,-0.3522486446432376,-0.10532924925896296,-0.03715688984201507};
    public static double[] weights_Minimax_without_ML_black = {0, 200, 9, 5, 3, 3, 1, 1};



    /**
     * Changes the weights of the components of the evaluation functions.
     *
     * @param w An array of weights to which the weights of the components of the evaluation functions.
     *          w[0] = weight of the pawn structures evaluation function.
     *          w[1] = weight of the King.
     *          w[2] = weight of the Queen.
     *          w[3] = weight of the Rooks.
     *          w[4] = weight of the Bishops.
     *          w[5] = weight of the Knights.
     *          w[6] = weight of the Pawns.
     *          w[7] = weight of the mobility evaluation function.
     */
    public static void changeWeights(double[] w) {
        weights = Arrays.copyOf(w, w.length);

        System.out.println("NEW WEIGHTS: " + Arrays.toString(weights));
    }

    /**
     * Enum for the different implemented algorithms.
     */
    public static enum ALGORITHMS {
        MINIMAX,
        MINIMAX_ALPHABETA,
        EXPECTIMAX
    }

    public static ALGORITHMS ALGORITHM = ALGORITHMS.MINIMAX_ALPHABETA;
    public static int depth = 3;

    /**
     * Sets the algorithm to be used to the passed on algorithm.
     * @param algorithm The passed on algorithm which will be used for the search.
     */
    public static void setAlgorithm(ALGORITHMS algorithm) {
        ALGORITHM = algorithm;
    }

    /**
     * Creates a new AI player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public SearchAI(boolean white, boolean help, boolean ML_component) {
        this.white = white;
        this.help = help;
        this.ML_component = ML_component;

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

    /**
     * Checks whether the current AI player is white or black.
     * @return Whether the current AI player is white or black.
     */
    public boolean isWhite() {
        return white;
    }

    /**
     * Gets a String of information about the current AI player.
     * @return A String of information about the current AI player.
     */
    public String toString() {
        return "(" + ALGORITHM + ", ML component: " + ML_component +")";
    }

    /**
     * Determines which algorithm is selected and therefore will be used as the search algorithm.
     */
    private void searchForMove() {

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

    /**
     * Starts the search using the Minimax algorithm.
     * @param alphabeta Whether Alpha-Beta Pruning is used or not.
     * @return The best moves found by the algorithm.
     */
    private int[] minimax(boolean alphabeta) {
        if (!ML_component) {
            depth = 3;
            return GameSearchTree.search(depth, white, alphabeta, weights_Minimax_without_ML_white, false);
        }
        depth = 3;
        return GameSearchTree.search(depth, white, alphabeta, weights, true);
    }

    /**
     * Starts the search using the Expectimax algorithm.
     * @return The best moves found by the algorithm.
     */
    private int[] expectimax() {
        return Expectimax.expectimaxSearch(depth, white);
    }
}