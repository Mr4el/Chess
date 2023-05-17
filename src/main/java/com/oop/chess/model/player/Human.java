package com.oop.chess.model.player;

import com.oop.chess.Game.PieceEnum;
public class Human implements Player {
    boolean white;

    // have we made a move?
    public boolean moved = false;

    boolean help;

    public Human(boolean white, boolean help) {
        this.white = white;
        this.help = help;
    }

    public boolean turn(PieceEnum piece) {
        /* TODO:
            Communicate with the GUI that the player can only move pieces with the following properties:
            1) Same colour as current player
            2) Same type as PieceEnum piece
        */

        if (moved) {
            moved = false;
            return true;
        }

        return false;
    }

    public boolean hasHelp() {
        return help;
    }

    public boolean isWhite() {
        return white;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
