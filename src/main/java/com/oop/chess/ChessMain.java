package com.oop.chess;

import com.oop.chess.gui.*;
import com.oop.chess.model.player.*;

/**
 * The method from which the game is started and a new main menu will be launched.
 */
public class ChessMain {
    GameState state;            // the current state of the program
    final static int FPS = 60;  // run main game loop 60 times per second

    public ChessMain() {
        state = new Game(this, new Human(true, false), new Human(false, false)); // Start game in the menu
        run();
    }


    public void switchGameState(GameState new_state) {
        state = new_state;
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