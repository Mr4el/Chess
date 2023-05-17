package com.oop.chess.gui;

import javax.swing.*;

public class GuiGame extends GUI {
    public GuiGame() {
        setTitle("OOP Chess");

        setSize(550,550);

        JPanel board = ChessBoard.createBoard();
        new Pieces(board);
        add(board);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
