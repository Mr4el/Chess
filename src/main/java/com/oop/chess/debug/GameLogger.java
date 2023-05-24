package com.oop.chess.debug;

// Debug class used to log certain information as text/csv files

import com.oop.chess.ChessMain;
import com.oop.chess.Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameLogger {
    public static void logWin(boolean white_won) {

        String f_name = "debug-output/" + ChessMain.session_launch_time + "___wins.csv";


        System.out.println(f_name);

        String[] game_data = Game.last_fen.split(" ");
        String final_board = game_data[0];
        String who_won = (game_data[1].equals("w") ? "white" : "black");
        String turns = game_data[5];

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f_name, true));
            writer.write(ChessMain.session_games_played + "," + who_won + "," + turns + "\n");
            writer.close();
        }catch (IOException e) {

        }
    }
}
