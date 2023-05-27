package com.oop.chess.gui;

import com.oop.chess.ChessMain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents the visual board.
 */
public class VisualBoard {

    public static JFrame frame;
    public static ArrayList<int[]> recoloredCells = new ArrayList<>();


    /**
     * Creates the visual chess board.
     *
     * @param f A JFrame object which is set to the frame variables of this class.
     * @return The visual board.
     */
    public static JPanel createBoard(JFrame f) {
        if (ChessMain.debug)
            System.out.println("Create visual board");
        frame = f;

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        JPanel tile;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                tile = new JPanel();
                board.add(tile);

                setInitialTileColor(row, column, tile);
            }
        }
        return board;
    }

    /**
     * Changes the color of recolored fields to the original color
     */
    public static void discardRecoloredCells() {
        if (recoloredCells != null) {
            for (int[] cell : recoloredCells) {
                JPanel tile = (JPanel) GuiGame.visualBoard.getComponent(cell[0] + cell[1] * 8);
                setInitialTileColor(cell[0], cell[1], tile);
            }
        }
    }

    /**
     * Sets the proper background color of the tile.
     *
     * @param row    Row ID for color definition
     * @param column Column ID for color definition
     * @param tile   Tile to change color for
     */
    private static void setInitialTileColor(int row, int column, JPanel tile) {
        if (row % 2 == 0) {
            if (column % 2 == 0) {
                tile.setBackground(Color.decode("#ffe6b3"));
            } else {
                tile.setBackground(Color.decode("#804000"));
            }
        } else {
            if (column % 2 == 0) {
                tile.setBackground(Color.decode("#804000"));
            } else {
                tile.setBackground(Color.decode("#ffe6b3"));
            }
        }
    }
}
