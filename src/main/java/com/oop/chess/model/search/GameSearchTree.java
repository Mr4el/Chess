package com.oop.chess.model.search;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.debug.GameLogger;
import com.oop.chess.model.config.Configuration;

import java.util.ArrayList;

public class GameSearchTree {

    // Store the best move found by the GST (from_x,from_y,to_x,to_y)
    // Change this only for root node once minimax scores are known?
    static int[] bestMove;

    static int statesEvaluated;
    static double optimalValue;

    static boolean alphaBeta;

    static int rounds = 0;

    static public ArrayList<Object> bestLeafNodes = new ArrayList<>();


    /**
     * Retrieves the best move while performing the search using the Minimax algorithm.
     *
     * @param depth     The maximum depth at which the algorithm will search.
     * @param isWhite   Whether the player is white or black.
     * @param alphaBeta Whether Alpha-Beta Pruning is used or not.
     * @return The best move.
     */
    public static int[] search(int depth, boolean isWhite, boolean alphaBeta, double[] weights, boolean ML_component) {
        GameSearchTree.alphaBeta = alphaBeta;


        statesEvaluated = 0;
        optimalValue = 0;
        ArrayList<Object> searchResult = depthSearch(depth, isWhite, true, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, weights);

        GameSearchTree.bestLeafNodes.add(searchResult.get(1));

        if (ML_component)
            GameSearchTree.bestLeafNodes.add(searchResult.get(1));

        GameLogger.logStatesSearched(statesEvaluated, optimalValue);

        if (Configuration.enableVisualSearch)
            Game.rebuildBoard();

        rounds++;

        return bestMove;
    }


    /**
     * Performs the search at the given depth. This method contains the implementation of the Minimax algorithm.
     *
     * @param depth            The depth at which the search will be performed.
     * @param isWhite          Whether the player is white or black.
     * @param root             Whether the current board is the root.
     * @param maximizingPlayer Whether the current player is the maximizing player or not.
     * @param alpha            The alpha value.
     * @param beta             The beta value.
     * @return The value of the evaluation function at the root.
     */
    public static ArrayList<Object> depthSearch(int depth, boolean isWhite, boolean root, boolean maximizingPlayer, double alpha, double beta, double[] weights) {
        statesEvaluated++;


        if (depth == 0) {
            String node = FEN.encode(Game.board, isWhite ? 1 : 0, 0, 0);
            double evaluation = EvaluationFunction.evaluationFunctionsCombined(Game.board, isWhite, weights, true);

            ArrayList<Object> returnList = new ArrayList<>();
            returnList.add(evaluation);
            returnList.add(node);

            return returnList;
        }

        // break recursion at lowest depth by simply returning the value of the evaluation function

        // Get every legal move for the game's current player
        ArrayList<int[]> moves;
        if (root) {
            moves = Game.getEveryLegalMoveOfPlayer(Game.board, isWhite, Game.getLegalPiece());

            // return only move available at root
            if (moves.size() == 1) {
                bestMove = moves.get(0);

                ArrayList<Object> returnList = new ArrayList<>();
                returnList.add(0);
                returnList.add(FEN.encode(Game.board, isWhite ? 0 : 1));

                return returnList;
            }
        } else
            moves = Game.getEveryLegalMoveOfPlayer(Game.board, isWhite, PieceEnum.ANY);

        // If the current player is the maximizing player we look at the next depth where the player is then the minimizing player.
        if (maximizingPlayer) {
            double maxEva = Double.NEGATIVE_INFINITY;
            String bestLeafString = "";

            for (int[] move : moves) {

                // Store state, execute move, restore state
                String node = FEN.encode(Game.board, isWhite ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], Configuration.enableVisualSearch, false);

                ArrayList<Object> childNode = depthSearch(depth - 1, !isWhite, false, false, alpha, beta, weights);
                double evaluationValue = (double) childNode.get(0);
                String childFenString = (String) childNode.get(1);

                Game.board = FEN.decode(node);

                maxEva = Math.max(maxEva, evaluationValue);

                if (evaluationValue == maxEva) {
                    bestLeafString = childFenString;

                    if (root) {
                        bestMove = move;
                        optimalValue = evaluationValue;
                    }
                }

                if (GameSearchTree.alphaBeta) {
                    alpha = Math.max(alpha, maxEva);

                    if (alpha >= beta)
                        break;
                }
            }

            ArrayList<Object> returnList = new ArrayList<>();
            returnList.add(maxEva);
            returnList.add(bestLeafString);

            return returnList;

            // If the current player is not the maximizing player we look at the next depth where the player is then the maximizing player.
        } else {
            double minEva = Double.POSITIVE_INFINITY;
            String bestLeafString = "";

            for (int[] move : moves) {

                // Store state, execute move, restore state
                String node = FEN.encode(Game.board, isWhite ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], Configuration.enableVisualSearch, false);

                ArrayList<Object> childNode = depthSearch(depth - 1, !isWhite, false, true, alpha, beta, weights);
                double evaluationValue = (double) childNode.get(0);
                String childFenString = (String) childNode.get(1);

                Game.board = FEN.decode(node);

                minEva = Math.min(minEva, evaluationValue);

                if (evaluationValue == minEva) {
                    bestLeafString = childFenString;

                    if (root) {
                        bestMove = move;
                        optimalValue = evaluationValue;
                    }
                }

                beta = Math.min(beta, minEva);

                if (GameSearchTree.alphaBeta) {
                    beta = Math.min(beta, minEva);

                    if (alpha >= beta)
                        break;
                }
            }

            ArrayList<Object> returnList = new ArrayList<>();
            returnList.add(minEva);
            returnList.add(bestLeafString);

            return returnList;
        }
    }
}
