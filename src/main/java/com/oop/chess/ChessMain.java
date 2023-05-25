package com.oop.chess;

import com.oop.chess.gui.*;
import com.oop.chess.model.player.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static String session_launch_time;
    public static String game_launch_time;
    public static int session_games_played;
    public static boolean SearchBotWhite = false;

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
     * Creates a new Chess game.
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
                if (game != null) {
                }
                //game.run();
            }
        }, 0, t);
    }

    /**
     * Starts a game between two players.
     *
     * @param hints A boolean indicating whether the player has chosen to use hints.
     */
    public static void startGame(boolean hints) {
        Player player1 = null;
        Player player2 = null;

        if (GuiMenu.AIGame) {
            switch (GuiMenu.aiPlayer0ComboBox.getItemAt(GuiMenu.aiPlayer0ComboBox.getSelectedIndex())) {
                case "Random Moves Bot":
                    SearchBotWhite = false;
                    player1 = new RandomAI(true, false);
                    break;
                case "Minimax Bot":
                    SearchBotWhite = true;
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                    player1 = new SearchAI(true, false, false);
                    break;
                case "Minimax with alpha-beta Bot":
                    SearchBotWhite = true;
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player1 = new SearchAI(true, false, false);
                    break;
                case "Minimax with alpha-beta and TDLeaf Bot":
                    SearchBotWhite = true;
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player1 = new SearchAI(true, false, true);
                    break;
                case "Expectimax Bot":
                    SearchBotWhite = true;
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                    player1 = new SearchAI(true, false, false);
                    break;
            }

            switch (GuiMenu.aiPlayer1ComboBox.getItemAt(GuiMenu.aiPlayer1ComboBox.getSelectedIndex())) {
                case "Random Moves Bot":
                    player2 = new RandomAI(false, false);
                    break;
                case "Minimax Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                    player2 = new SearchAI(false, false, false);
                    break;
                case "Minimax with alpha-beta Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player2 = new SearchAI(false, false, false);
                    break;
                case "Minimax with alpha-beta and TDLeaf Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player2 = new SearchAI(false, false, true);
                    break;
                case "Expectimax Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                    player2 = new SearchAI(false, false, false);
                    break;
            }
        } else if (GuiMenu.playingAI) {
            player1 = new Human(true, hints);
            switch (GuiMenu.aiComboBox.getItemAt(GuiMenu.aiComboBox.getSelectedIndex())) {
                case "Random Moves Bot":
                    player2 = new RandomAI(false, false);
                    break;
                case "Minimax Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                    player2 = new SearchAI(false, false, false);
                    break;
                case "Minimax with alpha-beta Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player2 = new SearchAI(false, false, false);
                    break;
                case "Minimax with alpha-beta and TDLeaf Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    player2 = new SearchAI(false, false, true);
                    break;
                case "Expectimax Bot":
                    SearchAI.setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                    player2 = new SearchAI(false, false, false);
                    break;
            }
        } else {
            player1 = new Human(true, hints);
            player2 = new Human(false, hints);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
        LocalDateTime ldt = LocalDateTime.now();

        game_launch_time = ldt.format(formatter).toString();

        Game.initializeGame(player1, player2);
        Game.runNextState(Game.TURN_STATES.START);
    }

    /**
     * The method from which the game is started and a new menu will be launched.
     *
     * @param args The arguments to be handled by the main method.
     */
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
        LocalDateTime ldt = LocalDateTime.now();

        session_launch_time = ldt.format(formatter).toString();

        new GuiMenu();
    }
}
