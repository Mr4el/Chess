package com.oop.chess.model.search;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.Game;

import java.util.ArrayList;

public class Expectimax {

    static boolean visual_search = false;

    public static int[] expectimaxSearch(int depth, boolean is_white) {
        ArrayList<int[]> moves = Game.getEveryLegalMoveOfPlayer(is_white, Game.getLegalPiece());
        double maxEvaluation = Double.MIN_VALUE;
        int[] bestMove = moves.get(0);

        for (int[] move : moves) {
            double evaluation = expectimaxSearch(depth-1, !is_white, false, true);
            if (evaluation > maxEvaluation) {
                maxEvaluation = evaluation;
                bestMove = move;
            }
        }
        return bestMove;
    }


    public static double expectimaxSearch(int depth, boolean is_white, boolean maximizingPlayer,
                                          boolean chanceLayer) {
        if (depth == 0) {
            if (is_white) {
                String node = FEN.encode(Game.board, 1, 0, 0);
                return EvaluationFunction.evaluationFunction(node, true, false, true, true, false);
            } else {
                String node = FEN.encode(Game.board, 0, 0, 0);
                return EvaluationFunction.evaluationFunction(node, false, false,true,true,false);
            }
        }

        ArrayList<int[]> moves = Game.getEveryLegalMoveOfPlayer(is_white, Game.PieceEnum.ANY);

        if (!chanceLayer) {
            if (maximizingPlayer) {
                double maxEvaluation = Double.MIN_VALUE;

                for (int[] move : moves) {
                    String node = FEN.encode(Game.board, is_white ? 0 : 1);
                    Game.movePieceTo(move[0], move[1], move[2], move[3], visual_search, false);

                    double evaluation = expectimaxSearch(depth-1, !is_white, false, true);

                    Game.board = FEN.decode(node);

                    maxEvaluation = Math.max(maxEvaluation, evaluation);
                }
                return maxEvaluation;
            } else {
                double minEvaluation = Double.MAX_VALUE;

                for (int[] move : moves) {
                    String node = FEN.encode(Game.board, is_white ? 0 : 1);
                    Game.movePieceTo(move[0], move[1], move[2], move[3], visual_search, false);

                    double evaluation = expectimaxSearch(depth-1, !is_white, true, true);

                    Game.board = FEN.decode(node);

                    minEvaluation = Math.min(minEvaluation, evaluation);
                }
                return minEvaluation;
            }
        } else {
            int movesSize = moves.size();
            double expectedValue = 0;

            for (int[] move : moves) {
                expectedValue += expectimaxSearch(depth-1, is_white, maximizingPlayer, false) / movesSize;
            }
            return expectedValue;
        }
    }

}
