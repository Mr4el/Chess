package com.oop.chess.model.player;

public interface Player {
    int color = 0;

    /**
     * turn() - the logic for the player's turn (dice roll, choose piece, choose move)
     * @return boolean - false if player hasn't made a move yet, true if player does make a move
     **/
    public boolean turn();  // turn method, in which the player rolls the dice and makes their move.
}
