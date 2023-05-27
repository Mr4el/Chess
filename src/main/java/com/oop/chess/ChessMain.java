package com.oop.chess;

import com.oop.chess.gui.*;
import com.oop.chess.model.config.Configuration;
import com.oop.chess.model.player.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the implemented game.
 */
public class ChessMain {
    final static int FPS = 60;

    public final static boolean debug = false;

    Timer taskTimer = null;

    // game singleton
    static ChessMain chessInstance = null;

    public static String sessionLaunchTime;
    public static String gameLaunchTime;
    public static int sessionGamesPlayed;
    public static boolean SearchBotWhite = false;


    /**
     * Diverges a call to create a new game but only when no such game has been created already (using the singleton design pattern).
     *
     * @return chess main instance
     */
    public static ChessMain getInstance() {
        if (chessInstance == null) {
            chessInstance = new ChessMain();
        }
        return chessInstance;
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
        if (taskTimer != null)
            taskTimer.cancel();

        taskTimer = new Timer();
        taskTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

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
                case "Random Moves Bot" -> {
                    SearchBotWhite = false;
                    player1 = new RandomAI(true, false);
                }
                case "Minimax Bot" -> {
                    SearchBotWhite = true;
                    player1 = new SearchAI(true, false, false);
                    ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                }
                case "Minimax with alpha-beta Bot" -> {
                    SearchBotWhite = true;
                    player1 = new SearchAI(true, false, false);
                    ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                }
                case "Minimax with alpha-beta and TDLeaf Bot" -> {
                    SearchBotWhite = true;
                    player1 = new SearchAI(true, false, true);
                    ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                }
                case "Expectimax Bot" -> {
                    SearchBotWhite = true;
                    player1 = new SearchAI(true, false, false);
                    ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                }
            }

            switch (GuiMenu.aiPlayer1ComboBox.getItemAt(GuiMenu.aiPlayer1ComboBox.getSelectedIndex())) {
                case "Random Moves Bot" -> player2 = new RandomAI(false, false);
                case "Minimax Bot" -> {
                    player2 = new SearchAI(false, false, false);
                    ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                }
                case "Minimax with alpha-beta Bot" -> {
                    player2 = new SearchAI(false, false, false);
                    ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                }
                case "Minimax with alpha-beta and TDLeaf Bot" -> {
                    player2 = new SearchAI(false, false, true);
                    ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                }
                case "Expectimax Bot" -> {
                    player2 = new SearchAI(false, false, false);
                    ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                }
            }
        } else if (GuiMenu.playingAI) {
            if (!GuiMenu.playAsBlack.isSelected()) {
                player1 = new Human(true, hints);
                switch (GuiMenu.aiComboBox.getItemAt(GuiMenu.aiComboBox.getSelectedIndex())) {
                    case "Random Moves Bot" -> player2 = new RandomAI(false, false);
                    case "Minimax Bot" -> {
                        player2 = new SearchAI(false, false, false);
                        ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                    }
                    case "Minimax with alpha-beta Bot" -> {
                        player2 = new SearchAI(false, false, false);
                        ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    }
                    case "Minimax with alpha-beta and TDLeaf Bot" -> {
                        player2 = new SearchAI(false, false, true);
                        ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    }
                    case "Expectimax Bot" -> {
                        player2 = new SearchAI(false, false, false);
                        ((SearchAI) player2).setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                    }
                }
            } else {
                player2 = new Human(false, hints);
                switch (GuiMenu.aiComboBox.getItemAt(GuiMenu.aiComboBox.getSelectedIndex())) {
                    case "Random Moves Bot" -> {
                        SearchBotWhite = false;
                        player1 = new RandomAI(true, false);
                    }
                    case "Minimax Bot" -> {
                        SearchBotWhite = true;
                        player1 = new SearchAI(true, false, false);
                        ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX);
                    }
                    case "Minimax with alpha-beta Bot" -> {
                        SearchBotWhite = true;
                        player1 = new SearchAI(true, false, false);
                        ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    }
                    case "Minimax with alpha-beta and TDLeaf Bot" -> {
                        player1 = new SearchAI(false, false, true);
                        ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.MINIMAX_ALPHABETA);
                    }
                    case "Expectimax Bot" -> {
                        SearchBotWhite = true;
                        player1 = new SearchAI(true, false, false);
                        ((SearchAI) player1).setAlgorithm(SearchAI.ALGORITHMS.EXPECTIMAX);
                    }
                }
            }
        } else {
            player1 = new Human(true, hints);
            player2 = new Human(false, hints);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
        LocalDateTime ldt = LocalDateTime.now();

        gameLaunchTime = ldt.format(formatter);

        Game.initializeGame(player1, player2);
        Game.runNextState(Game.TURN_STATES.START);
    }


    /**
     * The method from which the game is started and a new menu will be launched.
     *
     * @param args The arguments to be handled by the main method.
     */
    public static void main(String[] args) {
        Configuration.loadConfiguration();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
        LocalDateTime ldt = LocalDateTime.now();

        sessionLaunchTime = ldt.format(formatter);

        new GuiMenu();
    }
}
