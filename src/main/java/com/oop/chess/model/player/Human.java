package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.gui.HumanClicking;

public class Human implements Player {

    boolean move;
    boolean white;
    boolean help;
    int oldtile_x = 0;
    int oldtile_y = 0;
    int newtile_x = 0;
    int newtile_y = 0;
    HumanClicking clicker = null;

    public Human(boolean white, boolean help) {
        this.white = white;
        this.help = help;

    }

    // Player turn logic
    public boolean turn(PieceEnum piece) {
        // Add the human's clicker to the board
        if (clicker == null) {
            clicker = new HumanClicking(this);
        }
        clicker.enabled = true;

        // If the clicker calls setMove(x,y,x,y), then the player can make their move
        if (move) {
            Game.movePieceTo(oldtile_x, oldtile_y, newtile_x, newtile_y);
            move = false;
            clicker.enabled = false;
            return true;
        }

        return false;
    }

    // Set the player's move
    public void setMove(int ox, int oy, int nx, int ny) {
        this.oldtile_x = ox;
        this.oldtile_y = oy;
        this.newtile_x = nx;
        this.newtile_y = ny;

        this.move = true;
    }

    public boolean hasHelp() {
        return help;
    }

    public boolean isWhite() {
        return white;
    }
}
