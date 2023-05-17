package com.oop.chess.model.player;

import com.oop.chess.Game.PieceEnum;

public class AI implements Player {
    boolean white;
    boolean move = false;

    // have we made a move?
    public boolean moved = false;

    boolean help;

    public AI(boolean white, boolean help) {
        this.white = white;
        this.help = help;
    }

    // valid pieces from the PIECES enum
    public boolean turn(PieceEnum piece) {
        return false;
    }

    public boolean hasHelp() {
        return help;
    }

    public boolean isWhite() {
        return white;
    }

    public void setMove(int ox, int oy, int nx, int ny) {

    }
}
