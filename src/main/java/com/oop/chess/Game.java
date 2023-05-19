package com.oop.chess;

import com.oop.chess.model.pieces.*;
import com.oop.chess.model.player.*;
import com.oop.chess.gui.*;

import java.util.Arrays;
import java.util.Objects;

public class Game implements GameState {
    public static Piece[][] board;
    ChessMain parent;

    // store player 1 and 2 in array, players[current_player_index] accesses the current player (with current_player_index being either 0 or 1.)
    // current_player is a static reference to the current player, mainly useful for GUI stuff
    Player[] players = {null, null};
    public static Player current_player;
    int current_player_index;
    
    static boolean king_captured = false;

    //Piece[][] board;
    Piece[] white_pieces;
    Piece[] black_pieces;

    // The game's GUI;
    GuiGame gui;

    // the different values which the turn state can have.
    enum TURN_STATES {
        START,              // basic initilization
        DICE_ROLL,          // rolling dice & determining legal pieces
        CHOOSING_MOVE,      // player choosing a move
        SWITCH_TURN,        // switching turns (so player 2 plays after player 1)
        GAME_END
    }

    // what is the current state of the turn?
    TURN_STATES turn_state = TURN_STATES.START;

    // enum for the different piece types (used as identifiers)
    public enum PieceEnum {
        ANY,    // mainly used for debugging
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
    }

    // the piece type chosen by the dice
    public static PieceEnum legal_piece;

    // constructor
    public Game(ChessMain parent, Player player1, Player player2) {
        this.parent = parent;

        initializeBoard();

        this.gui = new GuiGame(board);

        //TODO: Initialize Dice.


        this.players[0] = player1;
        this.players[1] = player2;

        current_player_index = 0;
        current_player = this.players[current_player_index];
    }



    // the main game loop
    // update state of the game & progress
    public void run() {
        switch (turn_state) {
            case START:
                gui.setTitle((current_player_index == 0 ? "OOP CHESS - White's Turn" : "OOP CHESS - Black's Turn"));
                turn_state = TURN_STATES.DICE_ROLL;
                break;

            // roll the dice to determine which pieces the player is allowed to move
            case DICE_ROLL:
                legal_piece = Dice.roll(current_player.isWhite());
                if (legal_piece != null) {
                    gui.setTitle(gui.getTitle() + " - Legal piece: " + legal_piece);

                    turn_state = TURN_STATES.CHOOSING_MOVE;
                    break;
                }

                else {
                    System.out.println("No moves can be made");
                }

            // the player is currently making a move
            case CHOOSING_MOVE:
                // if this returns true, then the player has successfully finished a round
                if (current_player.turn(legal_piece)) {
                    turn_state = TURN_STATES.SWITCH_TURN;
                    GuiGame.frame.repaint();
                }
                break;

            // next player's turn
            case SWITCH_TURN:
            	if (king_captured) {
                    turn_state = TURN_STATES.GAME_END;
                    break;
                }
                current_player_index++;
                if (current_player_index > 1)
                    current_player_index = 0;

                current_player = players[current_player_index];

                turn_state = TURN_STATES.START;
                break;
                
            // King captured
            case GAME_END:
                String title = "";
                if (current_player.isWhite())
                    title = "White captured Black's King!";
                else
                    title = "Black captured White's King!";
                
                gui.setTitle(title);
                break;

            default:
                break;
        }
    }


    // get a piece from the board
    // (x,y) = (0,0) -> top left corner of the board
    // (x,y) = (7,7) -> bottom right corner of the board
    public static Piece getPiece(int x, int y) {
    	if (xyInBounds(x,y)) {
            Piece foundPiece = board[y][x];
            if (Objects.nonNull(foundPiece)) {
                return foundPiece;
            }
        }

        return null;
    }

    // Check if coordinates x y are within the bounds of the board
    public static boolean xyInBounds(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length)
            return false;
        return true;
    }

    // Move a piece from an initial (x,y) position to a new (x,y) position
    public static void movePieceTo(int from_x, int from_y, int to_x, int to_y){
        Piece p = getPiece(from_x,from_y);

        if (p != null) {
            p.makeMove(from_x,from_y,to_x,to_y);

            // check if tile on to_x,to_y is king
            Piece p2 = getPiece(to_x,to_y);
            if (p2 != null && p2.piece_type == PieceEnum.KING)
            king_captured = true;
            
            Piece p_clone = p.clone();
            board[to_y][to_x] = p_clone;
            board[from_y][from_x] = null;

            GUIdeletePiece(from_x, from_y);
            GUIdeletePiece(to_x, to_y);

            GUIsetPiece(to_x, to_y, p_clone.piece_type, p_clone.isWhite);
        }
    }

    // Get the current player
    public static Player getCurrentPlayer() {
        return current_player;
    }

    // Get the current legal piece (as decided by the dice)
    public static PieceEnum getLegalPiece() {
        return legal_piece;
    }

    // GUI COMMUNICATION
    // Ensure that a piece on the board is deleted visually
    public static void GUIdeletePiece(int x, int y) {
        GuiGame.removeVisualPiece(x,y);
    }
    // Ensure that a piece on the board is deleted visually
    public static void GUIsetPiece(int x, int y, PieceEnum type, Boolean white) {
        GuiGame.addVisualPiece(x,y,type,white);
    }

    // Initialize all pieces on the board
    public void initializeBoard() {

        board = new Piece[8][8];

        board[0][0] = new Rook(false, 0, 0);
        board[0][1] = new Knight(false, 0, 1);
        board[0][2] = new Bishop(false, 0, 2);
        board[0][3] = new Queen(false, 0, 3);
        board[0][4] = new King(false, 0, 4);
        board[0][5] = new Bishop(false, 0, 5);
        board[0][6] = new Knight(false, 0, 6);
        board[0][7] = new Rook(false, 0, 7);

        for (int j = 0; j < board.length; j++) {
            board[1][j] = new Pawn(false, 1, j);
        }

        for (int j = 0; j < board.length; j++) {
            board[6][j] = new Pawn(true, 6, j);
        }

        board[7][0] = new Rook(true, 7, 0);
        board[7][1] = new Knight(true, 7, 1);
        board[7][2] = new Bishop(true, 7, 2);
        board[7][3] = new Queen(true, 7, 3);
        board[7][4] = new King(true, 7, 4);
        board[7][5] = new Bishop(true, 7, 5);
        board[7][6] = new Knight(true, 7, 6);
        board[7][7] = new Rook(true, 7, 7);

        //Initialize white and black pieces arrays.
        black_pieces = new Piece[16];
        white_pieces = new Piece[16];

        int i = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                black_pieces[i] = getPiece(x, y);
                white_pieces[i] = getPiece(x, 6 + y);
                i++;
            }
        }
    }
}

/* basic idea for game loop ( update() ):

    - dice roll, check if any legal moves are possible with the piece chosen by the dice, roll again if needed
    - call players[current_player].turn method with specific piece type
    - player can choose a piece of the type chosen by the dice
    - chosen piece checks all legal moves, displays them to the player
    - player chooses a movie, moved piece calls its move() method

    at the end of update(), if the current player has successfully made a legal move, set current_player to 0 if it's 1, and 1 if it's 0.
*/
