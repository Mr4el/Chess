package com.oop.chess.gui;

import javax.swing.*;
import java.awt.*;

public class VisualBoard {

    /**
     * Method to instantiate a chessboard.
     * @return a JPanel component containing all the tiles with the correct colour.
     */
    public static JFrame frame;

    public static JPanel createBoard(JFrame f) {
        frame = f;

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8,8));
        JPanel tile;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                tile = new JPanel();
                board.add(tile);

                if (row % 2 == 0) {
                    if (column % 2 == 0) {
                        tile.setBackground(Color.decode("#ffe6b3"));
                    }
                    else {
                        tile.setBackground(Color.decode("#804000"));
                    }
                }
                else {
                    if (column % 2 == 0 ) {
                        tile.setBackground(Color.decode("#804000"));
                    }
                    else {
                        tile.setBackground(Color.decode("#ffe6b3"));
                    }
                }
            }
        }
        return board;
    }
}
