package com.oop.chess;

import com.oop.chess.model.pieces.Piece;
import com.oop.chess.model.search.FEN;

/**
 * This class represents all the Evaluation Functions that can be used for the Minimax and the Expectimax algorithms.
 */
public class EvaluationFunction {

    /**
     * The following numbers are used to give all the evaluation functions the same weight, such that a really good score for mobility is being seen as equal as a really good score for material.
     * This can be achieved by multiplying all the evaluation functions (except for the material function) with a certain number determined by the highest value of the material function divided
     * by the highest value of the chosen evaluation function such that their highest outcomes are equal to the highest outcomes for the material function.
     * - For the PAWNS_MULTIPLYING_FACTOR, the denominator of the division is the highest possible value (without the minus) constructed by 8 isolated pawns, 8 blocked pawns and 4 doubled pawns (20).
     * - For the MOBILITY_MULTIPLYING_FACTOR, the denominator of the division is the highest possible value constructed by the maximum number of moves possible (This situation arises when each of the pieces is able to move to all allowed places).
     * - For the numerator in the divisions, it is constructed by the highest possible value computed by the material evaluation function equal to 239 (This situation when one player has all of its pieces while the opponent has none).
     */
    public static final double PAWNS_MULTIPLYING_FACTOR = 239d / 20;
    public static final double MOBILITY_MULTIPLYING_FACTOR = 239d / (24 + 8 + 26 + 16 + 8 + 26);


    /**
     * This method allows all the evaluation functions present to be used with just one method call. It diverges the calls to the methods for each evaluation function.
     *
     * @param board         The current game board.
     * @param isWhite       Whether the player is white or black.
     * @param weights       The current weights of the evaluation function components.
     * @param regularSearch Whether the function is called when search without or with Machine Learning is performed.
     * @return The value of all the evaluation functions combined.
     */
    public static double evaluationFunctionsCombined(Piece[][] board, boolean isWhite, double[] weights, boolean regularSearch) {
        double evaluationValuePawns = pawnsEvaluationFunction(board, isWhite, weights[0], regularSearch);
        double evaluationsValueMaterial = materialEvaluationFunction(board, isWhite, weights[1], weights[2], weights[3], weights[4], weights[5], weights[6]);
        double evaluationsValueMobility = mobilityEvaluationFunction(board, isWhite, weights[7], regularSearch);
        return (evaluationValuePawns + evaluationsValueMaterial + evaluationsValueMobility);
    }


    /**
     * This method computes the value of an evaluation function that utilizes the structures of the pawns of the passed on the board.
     *
     * @param board         The board of which the pawn structures are read.
     * @param isWhite       Whether the player is white or black.
     * @param weight        The weight of how much the result of the Pawn Structures evaluation function will matter in the eventual result (of combinations of evaluation functions).
     * @param regularSearch Whether the function is called when search without or with Machine Learning is performed.
     * @return The value of the Pawn Structures evaluation function.
     */
    public static double pawnsEvaluationFunction(Piece[][] board, boolean isWhite, double weight, boolean regularSearch) {
        double evaluationFunctionPawnStructure;

        if (weight == 0) {
            return 0;
        }

        // Doubled-pawns
        int doubledPawnsWhite = 0;
        int doubledPawnsBlack = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if ((board[i][j] != null) && (board[i][j].pieceType == Game.PieceEnum.PAWN)) {
                    if (board[i][j].isWhite()) {
                        for (int k = i - 1; k >= 0; k--) {
                            if ((board[k][j] != null) && (board[k][j].pieceType == Game.PieceEnum.PAWN) && (board[k][j].isWhite())) {
                                doubledPawnsWhite++;
                                break;
                            }
                        }
                    } else {
                        for (int k = i + 1; k < board[0].length; k++) {
                            if ((board[k][j] != null) && (board[k][j].pieceType == Game.PieceEnum.PAWN) && (!board[k][j].isWhite())) {
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

        for (Piece[] pieces : board) {
            for (int j = 0; j < board[0].length; j++) {
                if ((pieces[j] != null) && pieces[j].pieceType == Game.PieceEnum.PAWN) {
                    if (pieces[j].isWhite()) {
                        for (int k = 0; k < board[0].length; k++) {
                            if (j == 0) {
                                if ((board[k][j + 1] != null) && (board[k][j + 1].pieceType == Game.PieceEnum.PAWN) && (board[k][j + 1].isWhite())) {
                                    pawnWhiteIsIsolated = false;
                                    break;
                                }
                            } else if (j == 7) {
                                if ((board[k][j - 1] != null) && (board[k][j - 1].pieceType == Game.PieceEnum.PAWN) && (board[k][j - 1].isWhite())) {
                                    pawnWhiteIsIsolated = false;
                                    break;
                                }
                            } else {
                                if ((board[k][j + 1] != null) && (board[k][j + 1].pieceType == Game.PieceEnum.PAWN) && (board[k][j + 1].isWhite())) {
                                    pawnWhiteIsIsolated = false;
                                    break;
                                }
                                if ((board[k][j - 1] != null) && (board[k][j - 1].pieceType == Game.PieceEnum.PAWN) && (board[k][j - 1].isWhite())) {
                                    pawnWhiteIsIsolated = false;
                                    break;
                                }
                            }
                        }
                        if (pawnWhiteIsIsolated) {
                            isolatedPawnsWhite++;
                        }
                    } else {
                        for (int k = 0; k < board[0].length; k++) {
                            if (j == 0) {
                                if ((board[k][j + 1] != null) && (board[k][j + 1].pieceType == Game.PieceEnum.PAWN) && (!board[k][j + 1].isWhite())) {
                                    pawnBlackIsIsolated = false;
                                    break;
                                }
                            } else if (j == 7) {
                                if ((board[k][j - 1] != null) && (board[k][j - 1].pieceType == Game.PieceEnum.PAWN) && (!board[k][j - 1].isWhite())) {
                                    pawnBlackIsIsolated = false;
                                    break;
                                }
                            } else {
                                if ((board[k][j + 1] != null) && (board[k][j + 1].pieceType == Game.PieceEnum.PAWN) && (!board[k][j + 1].isWhite())) {
                                    pawnBlackIsIsolated = false;
                                    break;
                                }
                                if ((board[k][j - 1] != null) && (board[k][j - 1].pieceType == Game.PieceEnum.PAWN) && (!board[k][j - 1].isWhite())) {
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
                if ((board[i][j] != null) && (board[i][j].pieceType == Game.PieceEnum.PAWN)) {
                    if (board[i][j].isWhite()) {
                        if ((i != 0) && (board[i - 1][j] != null)) {
                            if (j == 0) {
                                if (board[i - 1][j + 1] == null) {
                                    blockedPawnsWhite++;
                                } else if ((board[i - 1][j + 1] != null) && (board[i - 1][j + 1].isWhite())) {
                                    blockedPawnsWhite++;
                                }
                            } else if (j == 7) {
                                if (board[i - 1][j - 1] == null) {
                                    blockedPawnsWhite++;
                                } else if ((board[i - 1][j - 1] != null) && (board[i - 1][j - 1].isWhite())) {
                                    blockedPawnsWhite++;
                                }
                            } else {
                                if ((board[i - 1][j + 1] == null) && (board[i - 1][j - 1] == null)) {
                                    blockedPawnsWhite++;
                                } else if (((board[i - 1][j + 1] != null) && (board[i - 1][j + 1].isWhite())) && (board[i - 1][j - 1] == null)) {
                                    blockedPawnsWhite++;
                                } else if ((board[i - 1][j + 1] == null) && ((board[i - 1][j - 1] != null) && (board[i - 1][j - 1].isWhite()))) {
                                    blockedPawnsWhite++;
                                } else if (((board[i - 1][j + 1] != null) && (board[i - 1][j + 1].isWhite())) && ((board[i - 1][j - 1] != null) && (board[i - 1][j - 1].isWhite()))) {
                                    blockedPawnsWhite++;
                                }
                            }
                        }
                    } else {
                        if ((i != board.length - 1) && (board[i + 1][j] != null)) {
                            if (j == 0) {
                                if (board[i + 1][j + 1] == null) {
                                    blockedPawnsBlack++;
                                } else if ((board[i + 1][j + 1] != null) && (!board[i + 1][j + 1].isWhite())) {
                                    blockedPawnsBlack++;
                                }
                            } else if (j == 7) {
                                if (board[i + 1][j - 1] == null) {
                                    blockedPawnsBlack++;
                                } else if ((board[i + 1][j - 1] != null) && (!board[i + 1][j - 1].isWhite())) {
                                    blockedPawnsBlack++;
                                }
                            } else {
                                if ((board[i + 1][j + 1] == null) && (board[i + 1][j - 1] == null)) {
                                    blockedPawnsBlack++;
                                } else if (((board[i + 1][j + 1] != null) && (!board[i + 1][j + 1].isWhite())) && (board[i + 1][j - 1] == null)) {
                                    blockedPawnsBlack++;
                                } else if ((board[i + 1][j + 1] == null) && ((board[i + 1][j - 1] != null) && (!board[i + 1][j - 1].isWhite()))) {
                                    blockedPawnsBlack++;
                                } else if (((board[i + 1][j + 1] != null) && (!board[i + 1][j + 1].isWhite())) && ((board[i + 1][j - 1] != null) && (!board[i + 1][j - 1].isWhite()))) {
                                    blockedPawnsBlack++;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isWhite) {
            evaluationFunctionPawnStructure = -1 * (doubledPawnsWhite + isolatedPawnsWhite + blockedPawnsWhite);
        } else
            evaluationFunctionPawnStructure = -1 * (doubledPawnsBlack + isolatedPawnsBlack + blockedPawnsBlack);

        if (regularSearch) {
            return (weight * (PAWNS_MULTIPLYING_FACTOR * evaluationFunctionPawnStructure));
        } else {
            return (weight * evaluationFunctionPawnStructure);
        }
    }


    /**
     * This method computes the value of an evaluation function that utilizes the pieces left of the passed on the board.
     *
     * @param board        The board of which the pieces are read.
     * @param isWhite      Whether the player is white or black.
     * @param weightKing   The weight of the King.
     * @param weightQueen  The weight of the Queen.
     * @param weightRook   The weight of the Rook.
     * @param weightBishop The weight of the Bishop.
     * @param weightKnight The weight of the Knight.
     * @param weightPawn   The weight of the Pawn.
     * @return The value of the Material evaluation function.
     */
    public static double materialEvaluationFunction(Piece[][] board, boolean isWhite, double weightKing, double weightQueen, double weightRook, double weightBishop, double weightKnight, double weightPawn) {
        double evaluationFunctionMaterial;

        if ((weightKing == 0) && (weightQueen == 0) && (weightRook == 0) && (weightBishop == 0) && (weightKnight == 0) && (weightPawn == 0)) {
            return 0;
        }

        int playerIndex;
        if (isWhite) {
            playerIndex = 0;
        } else {
            playerIndex = 1;
        }
        String fen = FEN.encode(board, playerIndex);

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
        for (int i = 0; fen.charAt(i) != ' '; i++) {
            if (Character.isWhitespace(fen.charAt(i))) {
                break;
            } else if ((fen.charAt(i)) == ('K')) {
                whiteKings++;
            } else if ((fen.charAt(i)) == ('B')) {
                whiteBishops++;
            } else if ((fen.charAt(i)) == ('P')) {
                whitePawns++;
            } else if ((fen.charAt(i)) == ('N')) {
                whiteKnights++;
            } else if ((fen.charAt(i)) == ('R')) {
                whiteRooks++;
            } else if ((fen.charAt(i)) == ('Q')) {
                whiteQueens++;
            } else if ((fen.charAt(i)) == ('k')) {
                blackKings++;
            } else if ((fen.charAt(i)) == ('b')) {
                blackBishops++;
            } else if ((fen.charAt(i)) == ('p')) {
                blackPawns++;
            } else if ((fen.charAt(i)) == ('n')) {
                blackKnights++;
            } else if ((fen.charAt(i)) == ('r')) {
                blackRooks++;
            } else if ((fen.charAt(i)) == ('q')) {
                blackQueens++;
            }
        }

        double kingsDifference = whiteKings - blackKings;
        double queensDifference = whiteQueens - blackQueens;
        double rooksDifference = whiteRooks - blackRooks;
        double bishopsDifference = whiteBishops - blackBishops;
        double knightsDifference = whiteKnights - blackKnights;
        double pawnsDifference = whitePawns - blackPawns;

        evaluationFunctionMaterial = weightKing * kingsDifference +
            weightQueen * queensDifference +
            weightRook * rooksDifference +
            weightBishop * bishopsDifference +
            weightKnight * knightsDifference +
            weightPawn * pawnsDifference;

        if (isWhite) {
            evaluationFunctionMaterial = evaluationFunctionMaterial * -1;
        }

        return evaluationFunctionMaterial;
    }


    /**
     * This method computes the value of an evaluation function that utilizes the mobility of the legal pieces of the passed on the board.
     *
     * @param board         The board of which the legal pieces are read.
     * @param isWhite       Whether the player is white or black.
     * @param weight        The weight of how much the result of the Mobility evaluation function will matter in the eventual result (of combinations of evaluation functions).
     * @param regularSearch Whether the function is called when search without or with Machine Learning is performed.
     * @return The value of the Mobility evaluation function.
     */
    public static double mobilityEvaluationFunction(Piece[][] board, boolean isWhite, double weight, boolean regularSearch) {
        double evaluationFunctionMobility;

        if (weight == 0) {
            return 0;
        }

        if (isWhite) {
            evaluationFunctionMobility = Game.getEveryLegalMoveOfPlayer(board, true).size();
        } else
            evaluationFunctionMobility = Game.getEveryLegalMoveOfPlayer(board, false).size();


        if (regularSearch) {
            return (weight * (MOBILITY_MULTIPLYING_FACTOR * evaluationFunctionMobility));
        } else {
            return (weight * evaluationFunctionMobility);
        }
    }


    /**
     * Retrieves a value of zero at all times.
     *
     * @return The value zero.
     */
    public static double nullEvaluationFunction() {
        return 0;
    }
}
