package com.oop.chess.model.search;

import com.oop.chess.model.pieces.*;

import java.util.ArrayList;

// Forsyth-Edwards Notation
// Special notation for current state of board, useful for storing states without using too much memory
// Information on FEN: https://www.chessprogramming.org/Forsyth-Edwards_Notation

public class FEN {
    static ArrayList<Piece> kings;
    static int[] enpassantcoords = {-1,-1};
    static String enpassanttile;

    /**
     * Encode a board to a FEN string.
     * Note: The encoding process is split up into multiple sub-methods.
     *
     * @param board The board to be converted into FEN
     * @param current_player_index Current player is white or black?
     * @return FEN string of board
     */
    public static String encode(Piece[][] board, int current_player_index) {
        return encode(board, current_player_index, 0, 0);
    }

    public static String encode(Piece[][] board, int current_player_index, int half_moves, int full_moves) {
        String fen_string = "";

        // Store the kings for castling information
        kings = new ArrayList<Piece>();
        enpassantcoords[0] = -1;
        enpassantcoords[1] = -1;
        enpassanttile = "-";

        // Enocde the board
        fen_string += encodeBoard(board) + " ";

        fen_string += (current_player_index == 0 ? "w" : "b") + " ";

        fen_string += encodeCastling() + " ";

        fen_string += enpassanttile + " ";

        // todo: encode en-passant piece (maybe not needed because of how we coded en passant?)

        fen_string += "" + half_moves + " " + full_moves;

        return fen_string;
    }



    public static String encodeBoard(Piece[][] board) {
        String board_string = "";

        // STORE CURRENT STATE OF BOARD

        // Loop through rows
        for(int i = 0; i < board.length; i++) {
            int empty_tiles = 0;

            // Loop through columns of row i
            for(int ii = 0; ii < board[0].length; ii++) {
                Piece p = board[i][ii];

                if (p == null) {
                    empty_tiles++;
                } else {
                    if (empty_tiles > 0) {
                        board_string += Integer.toString(empty_tiles);
                        empty_tiles = 0;
                    }

                    char character;

                    switch(p.piece_type) {
                        case BISHOP:
                            character = 'b';
                            break;
                        case KING:
                            kings.add(p);
                            character = 'k';
                            break;
                        case KNIGHT:
                            character = 'n';
                            break;
                        case PAWN:
                            Pawn pwn = (Pawn)p;
                            if (pwn.enPassantPossible && enpassantcoords[0] != pwn.x && enpassantcoords[1] != pwn.y) {
                                enpassantcoords[0] = pwn.x;
                                enpassantcoords[1] = pwn.y + (pwn.isWhite ? 1 : -1);

                                // 97: character code for A
                                int x_letter_id = 97 + pwn.x;

                                char x_letter_char = (char)(x_letter_id);

                                enpassanttile = Character.toString(x_letter_char) + (enpassantcoords[1] + 1);
                            }
                            character = 'p';
                            break;

                        case QUEEN:
                            character = 'q';
                            break;

                        case ROOK:
                            character = 'r';
                            break;

                        default:
                            character = '*';
                            break;
                    }

                    String piece = Character.toString(character);

                    board_string += (p.isWhite() ? piece.toUpperCase() : piece);
                }
            }

            if (i < board.length - 1) {
                if (empty_tiles > 0)
                    board_string += Integer.toString(empty_tiles);

                board_string += "/";
            }
        }

        return board_string;
    }

    public static String encodeCastling() {
        String castling_string = "";

        int castle_moves = 0;
        if (!kings.isEmpty()) {
            for(Piece k : kings) {
                King king = (King)k;

                if (king.castleLeft) {
                    castling_string += (k.isWhite() ? "K" : "k");
                    castle_moves++;
                }

                if (king.castleRight) {
                    castling_string += (k.isWhite() ? "Q" : "q");
                    castle_moves++;

                }
            }
        }

        if (kings.isEmpty() || castle_moves == 0)
            castling_string += "-";

        return castling_string;
    }


    /**
     * Decode a FEN string and give it back as a Piece[][] board
     *
     * @param fen_string String to be converted into a board
     * @return converted board
     */

    // TODO: add castling/en passant information to the decoding process
    public static Piece[][] decode(String fen_string) {

        Piece[][] board = new Piece[8][8];

        int state = 0; // 0 = parsing board

        int x = 0;
        int y = 0;

        for (int i = 0; i < fen_string.length(); i++){
            char c = fen_string.charAt(i);
            String C = String.valueOf(c);

            // there's probably a better way to do this, maybe a map?
            if (state == 0) {
                Piece new_piece = null;
                switch(C.toLowerCase()){
                    case "p":
                        new_piece = new Pawn(Character.isUpperCase(c),x,y);
                        break;
                    case "n":
                        new_piece = new Knight(Character.isUpperCase(c),x,y);
                        break;
                    case "r":
                        new_piece = new Rook(Character.isUpperCase(c),x,y);
                        break;
                    case "b":
                        new_piece = new Bishop(Character.isUpperCase(c),x,y);
                        break;
                    case "q":
                        new_piece = new Queen(Character.isUpperCase(c),x,y);
                        break;
                    case "k":
                        new_piece = new King(Character.isUpperCase(c),x,y);
                        break;
                    case "/":
                        x = -1;
                        y++;
                        break;
                    case " ":
                        state = 1;
                        break;
                    default:
                        x += Integer.parseInt(C)-1;
                        break;
                }

                if (new_piece != null) {
                    board[y][x] = new_piece;
                }

                x++;
            }
        }
        return board;
    }
}
