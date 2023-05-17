package com.oop.chess;

import com.oop.chess.model.pieces.*;
import com.oop.chess.model.player.*;
import com.oop.chess.ChessMain.PieceEnum;

import java.util.Objects;

public class Game implements GameState {
    // store player 1 and 2 in array, players[current_player] accesses the current player (with current_player being either 0 or 1.)
    Player[] players;
    int current_player = 0;

    //TODO: Include Dice.

    Piece[][] board;
    Piece[] white_pieces;
    Piece[] black_pieces;

    // what is the current state of the turn?
    TURN_STATES turn_state = TURN_STATES.DICE_ROLL;

    // the different values which the turn state can have.
    enum TURN_STATES {
        DICE_ROLL,
        CHOOSING_MOVE,
        SWITCH_TURN
    }

    public Game(Player player1, Player player2) {
        //TODO: Initialize Dice.

        Piece[][] board = new Piece[8][8];

        board[0][0] = new Rook(false,0,0);
        board[0][1] = new Knight(false,0,1);
        board[0][2] = new Bishop(false,0,2);
        board[0][3] = new Queen(false,0,3);
        board[0][4] = new King(false,0,4);
        board[0][5] = new Bishop(false,0,5);
        board[0][6] = new Knight(false,0,6);
        board[0][7] = new Rook(false,0,7);

        for (int j = 0; j < board.length; j++) {
            board[1][j] = new Pawn(false,1,j);
        }

        for (int j = 0; j < board.length; j++) {
            board[6][j] = new Pawn(true,6,j);
        }

        board[7][0] = new Rook(true,7,0);
        board[7][1] = new Knight(true,7,1);
        board[7][2] = new Bishop(true,7,2);
        board[7][3] = new Queen(true,7,3);
        board[7][4] = new King(true,7,4);
        board[7][5] = new Bishop(true,7,5);
        board[7][6] = new Knight(true,7,6);
        board[7][7] = new Rook(true,7,7);

        //Initialize white and black pieces arrays.
        black_pieces = new Piece[16];
        white_pieces = new Piece[16];

        int i = 0;
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 2; x++) {
                black_pieces[i] = getPiece(x, y);
                white_pieces[i] = getPiece(x, 6 + y);
                i++;
            }
        }
    }

    PieceEnum legal_piece;

    // update state of the game & progress
    public void run() {
        switch(turn_state) {
            case DICE_ROLL:
                legal_piece = (new Dice()).roll(players[current_player].isWhite());

                /* since the dice roll should take place in a while loop until a valid piece type has been found, it will only change the turn state once that piece has been found.
                 */
                turn_state = TURN_STATES.CHOOSING_MOVE;
                break;

            case CHOOSING_MOVE:
                players[current_player].turn(legal_piece);
                break;

            default:
                break;
        }
    }


    public Piece getPiece(int x, int y) {
        Piece foundPiece = board[x][y];
        if (Objects.nonNull(foundPiece)) {
            return foundPiece;
        }

        else {
            return null;
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
