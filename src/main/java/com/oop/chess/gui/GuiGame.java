package com.oop.chess.gui;

import com.oop.chess.model.pieces.Piece;

import javax.swing.*;

public class GuiGame extends GUI {
    public GuiGame(Piece[][] board) {
        setTitle("Dice Chess!");

        setSize(550,550);

        JPanel visualBoard = VisualBoard.createBoard();
        new Pieces(visualBoard);
        add(visualBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void updateBoard(Piece[][] board) {

    }
}
