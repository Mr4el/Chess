package com.oop.chess.gui;

import javax.swing.*;

public class GuiMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("OOP Chess");

        frame.setSize(550,550);

        JPanel board = ChessBoard.createBoard();
        Pieces.addPieces(board);
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
