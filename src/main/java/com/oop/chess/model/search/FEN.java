package com.oop.chess.model.search;

import com.oop.chess.model.pieces.*;

import java.util.ArrayList;

// Forsyth-Edwards Notation
// Special notation for current state of board, useful for storing states without using too much memory
// Information on FEN: https://www.chessprogramming.org/Forsyth-Edwards_Notation

/**
 * This class represents how a game board is transformed into a much more compact FEN (Forsyth-Edwards Notation) String.
 * Special notation for current state of board, useful for storing states without using too much memory.
 * Information on FEN: <a href="https://www.chessprogramming.org/Forsyth-Edwards_Notation">...</a>
 */
public class FEN {
    static ArrayList<Piece> kings;
    static int[] enpassantCoords = {-1, -1};
    static String enpassantTile;


    /**
     * Encode a board to a FEN string.
     * Note: The encoding process is split up into multiple sub-methods.
     * The encoding of the actual board and additional information is kept separate.
     *
     * @param board              The board to be converted into FEN.
     * @param currentPlayerIndex Current player is white or black?
     * @return FEN string of board.
     */
    public static String encode(Piece[][] board, int currentPlayerIndex) {
        return encode(board, currentPlayerIndex, 0, 0);
    }


    /**
     * Encode a board to a FEN string.
     * Note: The encoding process is split up into multiple sub-methods.
     * The encoding of the actual board and additional information is kept separate.
     *
     * @param board              The board to be converted into FEN.
     * @param currentPlayerIndex Current player is white or black?
     * @param halfMoves          The number of half moves.
     * @param fullMoves          The number of full moves.
     * @return FEN string of board.
     */
    public static String encode(Piece[][] board, int currentPlayerIndex, int halfMoves, int fullMoves) {
        String fenString = "";

        // Store the kings for castling information
        kings = new ArrayList<>();
        enpassantCoords[0] = -1;
        enpassantCoords[1] = -1;
        enpassantTile = "-";

        // Encode the board
        fenString += encodeBoard(board) + " ";

        fenString += (currentPlayerIndex == 0 ? "w" : "b") + " ";

        fenString += encodeCastling() + " ";

        fenString += enpassantTile + " ";

        fenString += "" + halfMoves + " " + fullMoves;

        return fenString;
    }


    /**
     * Encodes the current board to a FEN String.
     *
     * @param board The current board.
     * @return The FEN string
     */
    public static String encodeBoard(Piece[][] board) {
        StringBuilder boardString = new StringBuilder();

        // STORE CURRENT STATE OF BOARD

        // Loop through rows
        for (int row = 0; row < board.length; row++) {
            int emptyTiles = 0;

            // Loop through columns of row
            for (int col = 0; col < board[0].length; col++) {
                Piece p = board[row][col];

                if (p == null) {
                    emptyTiles++;
                } else {
                    if (emptyTiles > 0) {
                        boardString.append(emptyTiles);
                        emptyTiles = 0;
                    }

                    char character;

                    switch (p.pieceType) {
                        case BISHOP -> character = 'b';
                        case KING -> {
                            kings.add(p);
                            character = 'k';
                        }
                        case KNIGHT -> character = 'n';
                        case PAWN -> {
                            Pawn pwn = (Pawn) p;
                            if (pwn.enPassantPossible && enpassantCoords[0] != pwn.x && enpassantCoords[1] != pwn.y) {
                                enpassantCoords[0] = pwn.x;
                                enpassantCoords[1] = pwn.y + (pwn.isWhite ? 1 : -1);

                                // 97: character code for A
                                int x_letter_id = 97 + pwn.x;

                                char x_letter_char = (char) (x_letter_id);

                                enpassantTile = Character.toString(x_letter_char) + (enpassantCoords[1] + 1);
                            }
                            character = 'p';
                        }
                        case QUEEN -> character = 'q';
                        case ROOK -> character = 'r';
                        default -> character = '*';
                    }

                    String piece = Character.toString(character);

                    boardString.append(p.isWhite() ? piece.toUpperCase() : piece);
                }
            }

            if (row < board.length - 1) {
                if (emptyTiles > 0)
                    boardString.append(emptyTiles);

                boardString.append("/");
            }
        }

        return boardString.toString();
    }


    /**
     * Encodes the castling of the current board to part of the FEN String.
     *
     * @return Part of the FEN String that contains the castling.
     */
    public static String encodeCastling() {
        StringBuilder castlingString = new StringBuilder();

        int castleMoves = 0;
        if (!kings.isEmpty()) {
            for (Piece k : kings) {
                King king = (King) k;

                if (king.castleLeft) {
                    castlingString.append(k.isWhite() ? "K" : "k");
                    castleMoves++;
                }

                if (king.castleRight) {
                    castlingString.append(k.isWhite() ? "Q" : "q");
                    castleMoves++;

                }
            }
        }

        if (kings.isEmpty() || castleMoves == 0)
            castlingString.append("-");

        return castlingString.toString();
    }


    /**
     * Decode a FEN string and give it back as a Piece[][] board
     *
     * @param fenString String to be converted into a board
     * @return converted board
     */
    public static Piece[][] decode(String fenString) {

        Piece[][] board = new Piece[8][8];

        int state = 0; // 0 = parsing board

        int x = 0;
        int y = 0;

        for (int i = 0; i < fenString.length(); i++) {
            char c = fenString.charAt(i);
            String C = String.valueOf(c);

            if (state == 0) {
                Piece newPiece = null;
                switch (C.toLowerCase()) {
                    case "p" -> newPiece = new Pawn(Character.isUpperCase(c), x, y);
                    case "n" -> newPiece = new Knight(Character.isUpperCase(c), x, y);
                    case "r" -> newPiece = new Rook(Character.isUpperCase(c), x, y);
                    case "b" -> newPiece = new Bishop(Character.isUpperCase(c), x, y);
                    case "q" -> newPiece = new Queen(Character.isUpperCase(c), x, y);
                    case "k" -> newPiece = new King(Character.isUpperCase(c), x, y);
                    case "/" -> {
                        x = -1;
                        y++;
                    }
                    case " " -> state = 1;
                    default -> x += Integer.parseInt(C) - 1;
                }

                if (newPiece != null) {
                    board[y][x] = newPiece;
                }

                x++;
            }
        }
        return board;
    }
}
