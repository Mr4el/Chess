package com.oop.chess.debug;

// Debug class used to log certain information as text/csv files

import com.oop.chess.ChessMain;
import com.oop.chess.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used as a debug class to log certain information as text/csv files.
 */
public class GameLogger {
    final static String directoryName = "debug-output";

    final static boolean LOG_INDIVIDUAL_GAMES = false;
    final static boolean LOG_WINS = true;

    static AnalyticsWindow analyticsWindow;

    static int averageStatesEvaluated = 0;
    static int totalEvaluations = 0;

    static int whiteWins = 0;
    static int blackWins = 0;
    static int whiteWinsLast_100 = 0;
    static int blackWinsLast_100 = 0;

    /**
     * Writes to the file which was the best score found and how many states were evaluated.
     *
     * @param statesEvaluated The number of states evaluated.
     * @param bestScore       The best score found.
     */
    public static void logStatesSearched(int statesEvaluated, double bestScore) {
        String dirname = "debug-output/games";
        File directory = new File(dirname);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = dirname + "/SearchAI__" + ChessMain.gameLaunchTime + ".csv";

        averageStatesEvaluated *= (totalEvaluations);
        averageStatesEvaluated += statesEvaluated;
        averageStatesEvaluated /= (++totalEvaluations);

        if (LOG_INDIVIDUAL_GAMES)
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.write(statesEvaluated + "," + bestScore + "\n");

                writer.close();
            } catch (IOException ignored) {

            }
    }

    /**
     * Writes to the file the current weights of the evaluation function components.
     *
     * @param weights The current weights of the evaluation function components.
     */
    public static void logWeights(double[] weights) {
        String dirname = "debug-output/games";
        File directory = new File(dirname);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = dirname + "/weight__" + ChessMain.sessionLaunchTime + ".csv";

        StringBuilder s = new StringBuilder("" + weights[0]);
        for (int i = 1; i < weights.length; i++)
            s.append(",").append(weights[i]);

        s.append("\n");


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(s.toString());

            writer.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Writes to the file which player won.
     *
     * @param whiteWon Whether white or black won.
     */
    public static void logWin(boolean whiteWon) {
        if (analyticsWindow == null)
            analyticsWindow = new AnalyticsWindow();

        analyticsWindow.update(whiteWon ? 1 : 0, whiteWon ? 0 : 1);

        if (whiteWon) {
            whiteWins++;
            whiteWinsLast_100++;
        } else {
            blackWins++;
            blackWinsLast_100++;
        }
        ;

        if (whiteWinsLast_100 > 100)
            whiteWinsLast_100 = Math.max(whiteWinsLast_100 - 1, 0);
        if (blackWinsLast_100 > 100)
            blackWinsLast_100 = Math.max(blackWinsLast_100 - 1, 0);

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = directoryName + "/" + ChessMain.sessionLaunchTime + "___wins.csv";

        String[] gameData = Game.lastFen.split(" ");
        String whoWon = (gameData[1].equals("w") ? "white" : "black");
        String turns = gameData[5];

        if (LOG_WINS)
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.write(ChessMain.sessionGamesPlayed + "," + whoWon + "," + turns + "," + averageStatesEvaluated + "," + whiteWins + "," + blackWins + "\n");
                writer.close();
            } catch (IOException ignored) {

            }

        averageStatesEvaluated = 0;
        totalEvaluations = 0;
    }

    public static void disposeAnalytics() {
        if (analyticsWindow != null) {
            analyticsWindow.dispose();
            analyticsWindow = null;
        }
    }
}
