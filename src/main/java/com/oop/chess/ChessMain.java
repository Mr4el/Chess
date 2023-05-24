package com.oop.chess;

import com.oop.chess.gui.*;
import com.oop.chess.model.player.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the implemented game.
 */
public class ChessMain {
    final static int FPS = 60;  // run main game loop 60 times per second

    public final static boolean debug = false;

    GuiMenu menu_gui;

    static Game game = null;

    Timer task_timer = null;

    // game singleton
    static ChessMain dc_instance = null;

    /**
     * Diverges a call to create a new game but only when no such game has been created already (using the singleton design pattern).
     *
     * @return
     */
    public static ChessMain getInstance() {
        if (dc_instance == null) {
            dc_instance = new ChessMain();
        }
        return dc_instance;
    }

    /**
     * Creates a new DiceChess game.
     */
    public ChessMain() {

        uiUpdateTimer(1000 / FPS);
    }

    /**
     * A manual fix to the way swing handles single-threads.
     *
     * @param t The delay.
     */
    public void uiUpdateTimer(int t) {
        if (task_timer != null)
            task_timer.cancel();

        task_timer = new Timer();
        task_timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (game != null) {}
                    //game.run();
            }
        }, 0, t);
    }

    /**
     * Starts a game between two players.
     *
     * @param player1_human A boolean indicating whether the first player is human.
     * @param player2_human A boolean indicating whether the second player is human.
     * @param hints A boolean indicating whether the player has chosen to use hints.
     */
    public static void startGame(boolean player1_human, boolean player2_human, boolean hints) {
        Player player1;
        Player player2;

        player1 = (player1_human ? new Human(true, hints) : new SearchAI(false,hints));
        player2 = (player2_human ? new Human(false, hints) : new SearchAI(false, hints));

        ChessMain dc = ChessMain.getInstance();
        dc.game = new Game(dc, player1, player2);
    }

    /**
     * The method from which the game is started and a new menu will be launched.
     *
     * @param args The arguments to be handled by the main method.
     */
    public static void main(String[] args) {
        new GuiMenu();
    }
}
