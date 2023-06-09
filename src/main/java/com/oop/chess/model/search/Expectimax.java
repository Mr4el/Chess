package com.oop.chess.model.search;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.Game;

import java.util.ArrayList;

/**
 * This class represents the Expectimax algorithm.
 */
public class Expectimax {

    static boolean visualSearch = false;
    static Game.PieceEnum[] pieceArray = {
        Game.PieceEnum.ROOK, Game.PieceEnum.KNIGHT, Game.PieceEnum.BISHOP,
        Game.PieceEnum.KING, Game.PieceEnum.QUEEN, Game.PieceEnum.PAWN
    };
    public static double[] weightsExpectimax = {1, 200, 9, 5, 3, 3, 1, 1};


    /**
     * Retrieves the best move while performing the search using the Expectimax algorithm.
     *
     * @param depth    The maximum depth at which the algorithm will search.
     * @param is_white Whether the player is white or black.
     * @return The best move.
     */
    public static int[] expectimaxSearch(int depth, boolean is_white) {
        ArrayList<int[]> moves = Game.getEveryLegalMoveOfPlayer(Game.board, is_white, Game.getLegalPiece());
        double maxEvaluation = Double.MIN_VALUE;
        int[] bestMove = moves.get(0);

        for (int[] move : moves) {
            double evaluation = expectimaxSearch(depth - 1, !is_white, false, true, null);
            if (evaluation > maxEvaluation) {
                maxEvaluation = evaluation;
                bestMove = move;
            }
        }
        return bestMove;
    }


    /**
     * Performs the search at the given depth. This method contains the implementation of the Expectimax algorithm.
     *
     * @param depth            The depth at which the search will be performed.
     * @param isWhite          Whether the player is white or black.
     * @param maximizingPlayer Whether the current player is the maximizing player or not.
     * @param chanceLayer      Whether the current layer is a chance layer or not.
     * @param currentPiece     The current piece from which we will perform the algorithm.
     * @return The value of the evaluation function at the root.
     */
    public static double expectimaxSearch(int depth, boolean isWhite, boolean maximizingPlayer,
                                          boolean chanceLayer, Game.PieceEnum currentPiece) {
        if (depth == 0) {
            return EvaluationFunction.evaluationFunctionsCombined(Game.board, isWhite, weightsExpectimax, true);
        }

        // If the current layer is a chance layer, the expected value (the summation of the values of the children of the chance layer) divided by the total number of possible moves.
        if (chanceLayer) {
            double expectedValue = 0;
            int possibleMovePieces = 0;

            //calculate how many pieces can be rolled by the dice
            for (Game.PieceEnum pieceEnum : pieceArray) {
                ArrayList<int[]> legalMoves = Game.getEveryLegalMoveOfPlayer(Game.board, isWhite, pieceEnum);
                if (!legalMoves.isEmpty()) {
                    expectedValue += expectimaxSearch(depth, isWhite, maximizingPlayer, false, pieceEnum);
                    possibleMovePieces++;
                }
            }
            return expectedValue / possibleMovePieces;
        }

        // If the current player is maximizing, we look at the values of the layer below.
        else if (maximizingPlayer) {
            ArrayList<int[]> moves = Game.getEveryLegalMoveOfPlayer(Game.board, isWhite, currentPiece);
            double maxEvaluation = Double.MIN_VALUE;

            for (int[] move : moves) {
                String node = FEN.encode(Game.board, isWhite ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], visualSearch, false);

                double evaluation = expectimaxSearch(depth - 1, !isWhite, false, true, null);

                Game.board = FEN.decode(node);

                maxEvaluation = Math.max(maxEvaluation, evaluation);
            }
            return maxEvaluation;
        }

        // If the current player is not maximizing, we move to the layer below.
        else {
            ArrayList<int[]> moves = Game.getEveryLegalMoveOfPlayer(Game.board, isWhite, currentPiece);
            double minEvaluation = Double.MAX_VALUE;

            for (int[] move : moves) {
                String node = FEN.encode(Game.board, isWhite ? 0 : 1);
                Game.movePieceTo(move[0], move[1], move[2], move[3], visualSearch, false);

                double evaluation = expectimaxSearch(depth - 1, !isWhite, true, true, null);

                Game.board = FEN.decode(node);

                minEvaluation = Math.min(minEvaluation, evaluation);
            }
            return minEvaluation;
        }
    }
}
