package com.oop.chess;

import com.oop.chess.model.player.*;

public class Game implements GameState {
    // store player 1 and 2 in array, players[current_player] accesses the current player (with current_player being either 0 or 1.)
    Player[] players;
    int current_player = 0;

    public Game() {

    }

    // update state of the game & progress
    public void run() {

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
