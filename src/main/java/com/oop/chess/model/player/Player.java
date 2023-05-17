package com.oop.chess.model.player;

import com.oop.chess.ChessMain.PieceEnum;

public interface Player {
    boolean white = true;

    /**
     * turn() - the logic for the player's turn (dice roll, choose piece, choose move)
     * @return boolean - false if player hasn't made a move yet, true if player does make a move
     **/
    public boolean turn(PieceEnum piece);

    public boolean isWhite();
}