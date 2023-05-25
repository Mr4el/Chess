package com.oop.chess.model.search;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.debug.GameLogger;
import com.oop.chess.gui.GuiGame;

import java.util.ArrayList;

public class GameSearchTree {
    // General idea:
    // Get list of legal moves for current player.
    //
    // For each move, execute the move, then perform the search again with depth - 1. After that, undo the move.
    //

    // Display the search visually? (Slower if true)
    static boolean visual_search = false;

    // Store the best move found by the GST (from_x,from_y,to_x,to_y)
    // Change this only for root node once minimax scores are known?
    static int[] bestMove;

    static int states_evaluated;
    static double optimal_value;

    static boolean alpha_beta;

    static int rounds = 0;

    static public ArrayList<Object> best_leaf_nodes = new ArrayList<Object>();

    // Method that does the search and then returns the static bestmove

    /**
     * Retrieves the best move while performing the search using the Minimax algorithm.
     * @param depth The maximum depth at which the algorithm will search.
     * @param is_white Whether the player is white or black.
     * @param alpha_beta Whether Alpha-Beta Pruning is used or not.
     * @return The best move.
     */
    public static int[] search(int depth, boolean is_white, boolean alpha_beta, double[] weights) {
        GameSearchTree.alpha_beta = alpha_beta;

        states_evaluated = 0;
        optimal_value = 0;
        ArrayList<Object> search_result = depthSearch(depth, is_white, true, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, weights);

        GameSearchTree.best_leaf_nodes.add(search_result.get(1));

        // System.out.println("[SEARCH AI] Best score: " + optimal_value + ", " + states_evaluated + " states evaluated.");

        GameLogger.logStatesSearched(states_evaluated, optimal_value);

        if (visual_search)
            Game.rebuildBoard();

        rounds++;

        // return the best move (from_x, from_y, to_x, to_y)
        return bestMove;
    }

    /**
     * Performs the search at the given depth. This method contains the implementation of the Minimax algorithm.
     *
     * @param depth The depth at which the search will be performed.
     * @param is_white Whether the player is white or black.
     * @param root Whether the current board is the root.
     * @param maximizingPlayer Whether the current player is the maximizing player or not.
     * @param alpha The alpha value.
     * @param beta The beta value.
     * @return The value of the evaluation function at the root.
     */
    public static ArrayList<Object> depthSearch(int depth, boolean is_white, boolean root, boolean maximizingPlayer, double alpha, double beta, double[] weights) {
        states_evaluated++;

        if (depth == 0) {
            String node = FEN.encode(Game.board, is_white ? 1 : 0, 0, 0);
            double evaluation = EvaluationFunction.evaluationFunctionsCombined(Game.board, is_white, weights, true);

            ArrayList<Object> return_list = new ArrayList<>();
            return_list.add(evaluation);
            return_list.add(node);

            return return_list;
        }

        // break recursion at lowest depth by simply returning the value of the evaluation function

        // Get every legal move for the game's current player
        ArrayList<int[]> moves;
        if (root) {
            moves = Game.getEveryLegalMoveOfPlayer(Game.board,is_white, Game.getLegalPiece());

            // return only move available at root
            if (moves.size() == 1) {
                bestMove = moves.get(0);

                ArrayList<Object> return_list = new ArrayList<Object>();
                return_list.add(0);
                return_list.add(FEN.encode(Game.board, is_white ? 0 : 1));

                return return_list;
            }
        } else
            moves = Game.getEveryLegalMoveOfPlayer(Game.board,is_white, PieceEnum.ANY);

        // If the current player is the maximizing player we look at the next depth where the player is then the minimizing player.
        if (maximizingPlayer) {
            double maxEva = Double.NEGATIVE_INFINITY;
            String best_leaf_string = "";

            for (int[] move : moves) {

                // Store state, execute move, restore state
                String node = FEN.encode(Game.board, is_white ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], visual_search, false);

                ArrayList<Object> child_node = depthSearch(depth - 1, !is_white, false, false, alpha, beta, weights);
                double evaluation_value = (double) child_node.get(0);
                String child_fen_string = (String) child_node.get(1);

                Game.board = FEN.decode(node);

                maxEva = Math.max(maxEva, evaluation_value);

                if (evaluation_value == maxEva) {
                    best_leaf_string = child_fen_string;

                    if (root) {
                        bestMove = move;
                        optimal_value = evaluation_value;
                    }
                }

                if (GameSearchTree.alpha_beta) {
                    alpha = Math.max(alpha, maxEva);

                    if (alpha >= beta)
                        break;
                }
            }

            ArrayList<Object> return_list = new ArrayList<Object>();
            return_list.add(maxEva);
            return_list.add(best_leaf_string);

            return return_list;

            // If the current player is not the maximizing player we look at the next depth where the player is then the maximizing player.
        } else {
            double minEva = Double.POSITIVE_INFINITY;
            String best_leaf_string = "";

            for (int[] move : moves) {

                // Store state, execute move, restore state
                String node = FEN.encode(Game.board, is_white ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], visual_search, false);

                ArrayList<Object> child_node = depthSearch(depth - 1, !is_white, false, true, alpha, beta, weights);
                double evaluation_value = (double) child_node.get(0);
                String child_fen_string = (String) child_node.get(1);

                Game.board = FEN.decode(node);

                minEva = Math.min(minEva, evaluation_value);

                if (evaluation_value == minEva) {
                    best_leaf_string = child_fen_string;

                    if (root) {
                        bestMove = move;
                        optimal_value = evaluation_value;
                    }
                }

                beta = Math.min(beta, minEva);

                if (GameSearchTree.alpha_beta) {
                    beta = Math.min(beta, minEva);

                    if (alpha >= beta)
                        break;
                }
            }

            ArrayList<Object> return_list = new ArrayList<Object>();
            return_list.add(minEva);
            return_list.add(best_leaf_string);

            return return_list;

        }


        // This is purely for testing purposes, remove this later

    }

}


//    public int minimax(ArrayList<int[]> node, int depth, boolean maximizingPlayer) {
//
//        ArrayList<int[]> child = Game.getEveryLegalMoveOfPlayer(is_white, PieceEnum.ANY);
//        int maxEva = (int) Double.NEGATIVE_INFINITY;
//        int minEva = (int) Double.POSITIVE_INFINITY;
//
//
//        if (maximizingPlayer) {     // for Maximizer Player
//
//            for (int i = 0; i < child.size(); i++) {
//                //int eva = minimax(child, depth - 1, false);
//                int eva = game.evaluationFunction();
//                int nMaxEva = Math.max(maxEva, eva);//gives Maximum of the values
//                minimax(child, depth - 1, false);
//                return nMaxEva;
//            }
//        } else {                         // for Minimizer player
//
//            for (int i = 0; i < node.size(); i++) {
//                //int eva = minimax(child, depth - 1, true);
//                int eva = game.evaluationFunction();
//                int nMinEva = Math.min(minEva, eva);
//                minimax(child, depth - 1, true);//gives minimum of the values
//                return nMinEva;
//            }
////arraylist return
//        }
//
//
//    }
