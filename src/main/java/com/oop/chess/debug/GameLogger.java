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
    final static String directory_name = "debug-output";

    final static boolean LOG_INDIVIDUAL_GAMES = false;
    final static boolean LOG_WINS = true;

    static AnalyticsWindow analytics_window;

    static int average_states_evaluated = 0;
    static int total_evaluations = 0;

    static int white_wins = 0;
    static int black_wins = 0;
    static int white_wins_last_100 = 0;
    static int black_wins_last_100 = 0;

    /**
     * Writes to the file which was the best score found and how many states were evaluated.
     * @param states_evaluated The number of states evaluated.
     * @param best_score The best score found.
     */
    public static void logStatesSearched(int states_evaluated, double best_score) {
        String dirname = "debug-output/games";
        File directory = new File(dirname);
        if (! directory.exists()){
            directory.mkdir();
        }

        String f_name = dirname + "/SearchAI__" + ChessMain.game_launch_time + ".csv";

        average_states_evaluated *= (total_evaluations);
        average_states_evaluated += states_evaluated;
        average_states_evaluated /= (++total_evaluations);

        if (LOG_INDIVIDUAL_GAMES)
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f_name, true));
                writer.write(states_evaluated + "," + best_score + "\n");

                writer.close();
            }catch (IOException e) {

            }
    }

    public static void logWeights(double[] weights) {
        String dirname = "debug-output/games";
        File directory = new File(dirname);
        if (! directory.exists()){
            directory.mkdir();
        }

        String f_name = dirname + "/weight__" + ChessMain.session_launch_time + ".csv";

        StringBuilder s = new StringBuilder("" + weights[0]);
        for(int i = 1; i < weights.length; i++)
            s.append(",").append(weights[i]);

        s.append("\n");


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f_name, true));
            writer.write(s.toString());

            writer.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Writes to the file which player won.
     * @param white_won Whether white or black won.
     */
    public static void logWin(boolean white_won) {
        if (analytics_window == null)
            analytics_window = new AnalyticsWindow();

        analytics_window.update(white_won ? 1 : 0, white_won ? 0 : 1);

        if (white_won) {
            white_wins++;
            white_wins_last_100++;
        } else {
            black_wins++;
            black_wins_last_100++;
        };

        if (white_wins_last_100 > 100)
            white_wins_last_100 = Math.max(white_wins_last_100-1,0);
        if (black_wins_last_100 > 100)
            black_wins_last_100 = Math.max(black_wins_last_100-1,0);

        File directory = new File(directory_name);
        if (! directory.exists()){
            directory.mkdir();
        }

        String f_name = directory_name + "/" + ChessMain.session_launch_time + "___wins.csv";

        String[] game_data = Game.last_fen.split(" ");
        String who_won = (game_data[1].equals("w") ? "white" : "black");
        String turns = game_data[5];

        if (LOG_WINS)
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f_name, true));
                writer.write(ChessMain.session_games_played + "," + who_won + "," + turns + "," + average_states_evaluated + "," + white_wins + "," + black_wins + "\n");
                writer.close();
            } catch (IOException e) {

            }

        average_states_evaluated = 0;
        total_evaluations = 0;
    }
}
