package com.oop.chess.model.player;

public class Human implements Player {

    boolean help;

    public Human(boolean help) {
        this.help = help;
    }
    public boolean turn() {
        return false;
    }

    public boolean hasHelp() {
        return help;
    }
}
