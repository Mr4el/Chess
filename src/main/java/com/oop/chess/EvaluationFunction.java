package com.oop.chess;

import com.oop.chess.model.pieces.Piece;

/**
 * This class represents all the Evaluation Functions that can be used for the Minimax and the Expectimax algorithms.
 */
public class EvaluationFunction {
    /**
     * This method computes the evaluation function that belongs to the current board and current string fen.
     * Inside this method there are multiple evaluation functions present and their values are calculated.
     *
     * @param fen The Forsyth-Edwards Notation of the current game board.
     * @param isWhite Whether the player is white or black.
     * @param pawnStructuresExamine Whether the pawn structures are examined.
     * @param materialCountingExamine Whether the number of pieces per type is examined.
     * @param mobilityCountingExamine Whether the total legal moves per player is examined.
     * @param nullCountingExamine Whether the heuristic of zero will be examined.
     * @return The value of the evaluation function.
     */
    public static double evaluationFunction(String fen, boolean isWhite,
                                            boolean pawnStructuresExamine, boolean materialCountingExamine, boolean mobilityCountingExamine, boolean nullCountingExamine) {

        // By setting this boolean to true, the pawnStructures will be examined. Otherwise, due to efficiency, not.
        boolean pawnStructures = pawnStructuresExamine;

        // By setting this boolean to true, the number of pieces per type will be examined. Otherwise, due to efficiency, not.
        boolean materialCounting = materialCountingExamine;

        // By setting this boolean to true, the number of total legal moves per player will be examined. Otherwise, due to efficiency, not.
        boolean mobilityCounting = mobilityCountingExamine;

        // By setting this boolean to true, the heuristic of zero will be examined. Otherwise, due to efficiency, not.
        boolean nullCounting = nullCountingExamine;


        double evaluationFunctionPawnStructure  = 0;
        double evaluationFunctionMaterial       = 0;
        double evaluationFunctionMobility       = 0;
        double evaluationFunctionZero           = 0;
        double evaluationFunctionChosen         = 0;


        // Examining the different pawn structures.

        if (pawnStructures) {
            Piece[][] board = Game.board;

            // Doubled-pawns
            int doubledPawnsWhite = 0;
            int doubledPawnsBlack = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {

                    if ((board[i][j] != null) && (board[i][j].piece_type == Game.PieceEnum.PAWN)) {
                        if (board[i][j].isWhite()) {
                            for (int k = i - 1; k >= 0; k--) {
                                if ((board[k][j] != null) && (board[k][j].piece_type == Game.PieceEnum.PAWN) && (board[k][j].isWhite())) {
                                    doubledPawnsWhite++;
                                    break;
                                }
                            }
                        } else {
                            for (int k = i + 1; k < board[0].length; k++) {
                                if ((board[k][j] != null) && (board[k][j].piece_type == Game.PieceEnum.PAWN) && (!board[k][j].isWhite())) {
                                    doubledPawnsBlack++;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // Isolated Pawns
            boolean pawnWhiteIsIsolated = true;
            boolean pawnBlackIsIsolated = true;
            int isolatedPawnsWhite = 0;
            int isolatedPawnsBlack = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if ((board[i][j] != null) && board[i][j].piece_type == Game.PieceEnum.PAWN) {
                        if (board[i][j].isWhite()) {
                            for (int k = 0; k < board[0].length; k++) {
                                if (j == 0) {
                                    if ((board[k][j + 1] != null) && (board[k][j + 1].piece_type == Game.PieceEnum.PAWN) && (board[k][j + 1].isWhite())) {
                                        pawnWhiteIsIsolated = false;
                                        break;
                                    }
                                } else if (j == 7) {
                                    if ((board[k][j - 1] != null) && (board[k][j - 1].piece_type == Game.PieceEnum.PAWN) && (board[k][j - 1].isWhite())) {
                                        pawnWhiteIsIsolated = false;
                                        break;
                                    }
                                } else {
                                    if ((board[k][j + 1] != null) && (board[k][j + 1].piece_type == Game.PieceEnum.PAWN) && (board[k][j + 1].isWhite())) {
                                        pawnWhiteIsIsolated = false;
                                        break;
                                    }
                                    if ((board[k][j - 1] != null) && (board[k][j - 1].piece_type == Game.PieceEnum.PAWN) && (board[k][j - 1].isWhite())) {
                                        pawnWhiteIsIsolated = false;
                                        break;
                                    }
                                }
                            }
                            if (pawnWhiteIsIsolated) {
                                isolatedPawnsWhite++;
                            }
                        }

                        else {
                            for (int k = 0; k < board[0].length; k++) {
                                if (j == 0) {
                                    if ((board[k][j + 1] != null) && (board[k][j + 1].piece_type == Game.PieceEnum.PAWN) && (!board[k][j + 1].isWhite())) {
                                        pawnBlackIsIsolated = false;
                                        break;
                                    }
                                } else if (j == 7) {
                                    if ((board[k][j - 1] != null) && (board[k][j - 1].piece_type == Game.PieceEnum.PAWN) && (!board[k][j - 1].isWhite())) {
                                        pawnBlackIsIsolated = false;
                                        break;
                                    }
                                } else {
                                    if ((board[k][j + 1] != null) && (board[k][j + 1].piece_type == Game.PieceEnum.PAWN) && (!board[k][j + 1].isWhite())) {
                                        pawnBlackIsIsolated = false;
                                        break;
                                    }
                                    if ((board[k][j - 1] != null) && (board[k][j - 1].piece_type == Game.PieceEnum.PAWN) && (!board[k][j - 1].isWhite())) {
                                        pawnBlackIsIsolated = false;
                                        break;
                                    }
                                }
                            }
                            if (pawnBlackIsIsolated) {
                                isolatedPawnsBlack++;
                            }
                        }
                    }

                    pawnWhiteIsIsolated = true;
                    pawnBlackIsIsolated = true;
                }
            }

            // Blocked Pawns
            int blockedPawnsWhite = 0;
            int blockedPawnsBlack = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if ((board[i][j] != null) && (board[i][j].piece_type == Game.PieceEnum.PAWN)) {
                        if (board[i][j].isWhite()) {
                            if ((i != 0) && (board[i-1][j] != null)) {
                                if (j == 0) {
                                    if (board[i-1][j+1] == null) {
                                        blockedPawnsWhite++;
                                    }
                                    else if ((board[i-1][j+1] != null) && (board[i-1][j+1].isWhite())) {
                                        blockedPawnsWhite++;
                                    }
                                } else if (j == 7) {
                                    if (board[i-1][j-1] == null) {
                                        blockedPawnsWhite++;
                                    }
                                    else if ((board[i-1][j-1] != null) && (board[i-1][j-1].isWhite())) {
                                        blockedPawnsWhite++;
                                    }
                                } else {
                                    if ((board[i-1][j+1] == null) && (board[i-1][j-1] == null)) {
                                        blockedPawnsWhite++;
                                    }
                                    else if (((board[i-1][j+1] != null) && (board[i-1][j+1].isWhite())) && (board[i-1][j-1] == null)) {
                                        blockedPawnsWhite++;
                                    }
                                    else if ((board[i-1][j+1] == null) && ((board[i-1][j-1] != null) && (board[i-1][j-1].isWhite()))) {
                                        blockedPawnsWhite++;
                                    }
                                    else if (((board[i-1][j+1] != null) && (board[i-1][j+1].isWhite())) && ((board[i-1][j-1] != null) && (board[i-1][j-1].isWhite()))) {
                                        blockedPawnsWhite++;
                                    }
                                }
                            }
                        }

                        else {
                            if ((i != board.length - 1) && (board[i+1][j] != null)) {
                                if (j == 0) {
                                    if (board[i+1][j+1] == null) {
                                        blockedPawnsBlack++;
                                    }
                                    else if ((board[i+1][j+1] != null) && (!board[i+1][j+1].isWhite())) {
                                        blockedPawnsBlack++;
                                    }
                                } else if (j == 7) {
                                    if (board[i+1][j-1] == null) {
                                        blockedPawnsBlack++;
                                    }
                                    else if ((board[i+1][j-1] != null) && (!board[i+1][j-1].isWhite())) {
                                        blockedPawnsBlack++;
                                    }
                                } else {
                                    if ((board[i+1][j+1] == null) && (board[i+1][j-1] == null)) {
                                        blockedPawnsBlack++;
                                    }
                                    else if (((board[i+1][j+1] != null) && (!board[i+1][j+1].isWhite())) && (board[i+1][j-1] == null)) {
                                        blockedPawnsBlack++;
                                    }
                                    else if ((board[i+1][j+1] == null) && ((board[i+1][j-1] != null) && (!board[i+1][j-1].isWhite()))) {
                                        blockedPawnsBlack++;
                                    }
                                    else if (((board[i+1][j+1] != null) && (!board[i+1][j+1].isWhite())) && ((board[i+1][j-1] != null) && (!board[i+1][j-1].isWhite()))) {
                                        blockedPawnsBlack++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (ChessMain.SearchBotWhite && Game.getCurrentPlayer().isWhite()) {
                evaluationFunctionPawnStructure = (doubledPawnsWhite - doubledPawnsBlack) + (isolatedPawnsWhite - isolatedPawnsBlack) + (blockedPawnsWhite - blockedPawnsBlack);
            } else
                evaluationFunctionPawnStructure = (doubledPawnsBlack - doubledPawnsWhite) + (isolatedPawnsBlack - isolatedPawnsWhite) + (blockedPawnsBlack - blockedPawnsWhite);

            evaluationFunctionChosen += evaluationFunctionPawnStructure;

        }

        // Counting the number of pieces per type.

        if (materialCounting) {
            int whiteKings = 0;
            int blackKings = 0;
            int whiteBishops = 0;
            int blackBishops = 0;
            int whitePawns = 0;
            int blackPawns = 0;
            int whiteKnights = 0;
            int blackKnights = 0;
            int whiteRooks = 0;
            int blackRooks = 0;
            int whiteQueens = 0;
            int blackQueens = 0;

            // Searching through the string to find the number of pieces for each type left on the board. By looping through this string we do not have to examine the empty spaces while looping through the board would require this.
            for (int i = 0; i < fen.length(); i++) {
                if (Character.isWhitespace(fen.charAt(i))) { break; }

                else if ((fen.charAt(i)) == ('K')) { whiteKings++; }

                else if ((fen.charAt(i)) == ('B')) { whiteBishops++; }

                else if ((fen.charAt(i)) == ('P')) { whitePawns++; }

                else if ((fen.charAt(i)) == ('N')) { whiteKnights++; }

                else if ((fen.charAt(i)) == ('R')) { whiteRooks++; }

                else if ((fen.charAt(i)) == ('Q')) { whiteQueens++; }

                else if ((fen.charAt(i)) == ('k')) { blackKings++; }

                else if ((fen.charAt(i)) == ('b')) { blackBishops++; }

                else if ((fen.charAt(i)) == ('p')) { blackPawns++; }

                else if ((fen.charAt(i)) == ('n')) { blackKnights++; }

                else if ((fen.charAt(i)) == ('r')) { blackRooks++; }

                else if ((fen.charAt(i)) == ('q')) { blackQueens++; }
            }

            double kingsDifference      = whiteKings    - blackKings;
            double queensDifference     = whiteQueens   - blackQueens;
            double rooksDifference      = whiteRooks    - blackRooks;
            double bishopsDifference    = whiteBishops  - blackBishops;
            double knightsDifference    = whiteKnights  - blackKnights;
            double pawnsDifference      = whitePawns    - blackPawns;

            double kingsCoefficient     = 200;
            double queensCoefficient    = 9;
            double rooksCoefficient     = 5;
            double bishopsCoefficient   = 3;
            double knightsCoefficient   = 3;
            double pawnsCoefficient     = 1;

            evaluationFunctionMaterial = kingsCoefficient*kingsDifference + queensCoefficient*queensDifference + rooksCoefficient*rooksDifference + bishopsCoefficient*bishopsDifference + knightsCoefficient*knightsDifference + pawnsCoefficient*pawnsDifference;

            if (ChessMain.SearchBotWhite && Game.getCurrentPlayer().isWhite()) {
                evaluationFunctionChosen += evaluationFunctionMaterial;
            } else
                evaluationFunctionChosen += evaluationFunctionMaterial * -1;
        }

        // Counting the total number of legal moves per player.

        if (mobilityCounting) {
            int numberLegalMovesWhite = Game.getEveryLegalMoveOfPlayer(isWhite).size();
            int numberLegalMovesBlack = Game.getEveryLegalMoveOfPlayer(!isWhite).size();

            if (ChessMain.SearchBotWhite && Game.getCurrentPlayer().isWhite()) {
                evaluationFunctionMobility = numberLegalMovesWhite - numberLegalMovesBlack;
            } else
                evaluationFunctionMobility = numberLegalMovesBlack - numberLegalMovesWhite;

            evaluationFunctionChosen += 0.1 * evaluationFunctionMobility;
        }

        // The evaluation function value of zero. Instead of adding to evaluationFunctionChosen we assign just 0 to it.

        if (nullCounting) {
            evaluationFunctionZero = 0;
            evaluationFunctionChosen = evaluationFunctionZero;
        }

        return evaluationFunctionChosen;
    }
}
