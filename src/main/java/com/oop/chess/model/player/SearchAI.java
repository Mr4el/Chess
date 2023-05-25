package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.model.search.Expectimax;
import com.oop.chess.model.search.GameSearchTree;

import javax.swing.*;
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
    int oldTileX = 0;
    int oldTileY = 0;
    int newTileX = 0;
    int newTileY = 0;

    // The initial weights for the evaluation function components.
    public static double[] weights = {1, 200, 9, 5, 3, 3, 1, 1};
    public static double[] weights_Minimax_without_ML_white = {1, 200, 9, 5, 3, 3, 1, 1};
    public static double[] weights_Minimax_without_ML_black = {1, 200, 9, 5, 3, 3, 1, 1};


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
    public enum ALGORITHMS {
        MINIMAX,
        MINIMAX_ALPHABETA,
        EXPECTIMAX
    }

    // Sets the default algorithm to be the minimax with alpha-beta pruning algorithm.
    public ALGORITHMS ALGORITHM = ALGORITHMS.MINIMAX_ALPHABETA;
    public static int depth;


    /**
     * Sets the algorithm to be used to the passed on algorithm.
     *
     * @param algorithm The passed on algorithm which will be used for the search.
     */
    public void setAlgorithm(ALGORITHMS algorithm) {
        ALGORITHM = algorithm;
    }


    /**
     * Sets the search depth value for the search algorithm.
     *
     * @param newDepth The new search depth value.
     */
    public static void setDepth(int newDepth) {
        depth = newDepth;
    }


    /**
     * Creates a new AI player.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @param help  A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
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
        ActionListener taskPerformer = evt -> searchForMove();
        Timer timer = new Timer(1, taskPerformer);
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
        return "(" + ALGORITHM + ", ML component: " + ML_component + ")";
    }


    /**
     * Determines which algorithm is selected and therefore will be used as the search algorithm.
     */
    private void searchForMove() {

        int[] myBestMove = new int[4];

        switch (ALGORITHM) {
            // Minimax without alphabeta
            case MINIMAX:
                myBestMove = minimax(false);
                break;

            // Minimax with alphabeta
            case MINIMAX_ALPHABETA:
                myBestMove = minimax(true);
                break;

            // Expectimax
            case EXPECTIMAX:
                myBestMove = expectimax();
                break;
        }

        Game.movePieceTo(myBestMove[0], myBestMove[1], myBestMove[2], myBestMove[3]);
    }


    /**
     * Starts the search using the Minimax algorithm.
     *
     * @param alphabeta Whether Alpha-Beta Prunining is used or not.
     * @return The best moves found by the algorithm.
     */
    private int[] minimax(boolean alphabeta) {
        if (!ML_component) {
            if (white) {
                return GameSearchTree.search(3, true, alphabeta, weights_Minimax_without_ML_white, false);
            } else {
                return GameSearchTree.search(3, false, alphabeta, weights_Minimax_without_ML_black, false);
            }
        }
        return GameSearchTree.search(depth, white, alphabeta, weights, true);
    }


    /**
     * Starts the search using the Expectimax algorithm.
     *
     * @return The best moves found by the algorithm.
     */
    private int[] expectimax() {
        System.out.println("Depth for expectimax: " + depth);
        return Expectimax.expectimaxSearch(depth, white);
    }
}