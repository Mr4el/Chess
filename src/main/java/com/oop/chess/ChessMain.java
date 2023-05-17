package com.oop.chess;

import com.oop.chess.gui.*;
import com.oop.chess.model.player.*;

/**
 * The method from which the game is started and a new main menu will be launched.
 */
public class ChessMain {
    GameState state;            // the current state of the program
    final static int FPS = 60;  // run main game loop 60 times per second

    public static GUI gui;

    public enum PieceEnum {
        ANY,
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
    }


    public ChessMain() {
        state = new Menu(); // Start game in the menu
        run();
    }


    public void switchGameState(GameState new_state) {
        state = new_state;
        // TODO: probably some code that updates the GUI to match the new state
    }

    // MAIN GAME LOOP
    public void run() {
        while(true) {
            state.run();

            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new ChessMain();
    }
}
