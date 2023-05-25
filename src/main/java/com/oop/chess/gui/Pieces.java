package com.oop.chess.gui;

import javax.swing.*;

import com.oop.chess.ChessMain;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Piece;

/**
 * This class is responsible for adding the visual pieces to the board.
 */
public class Pieces {

    public static VisualPiece[] blackPieces = new VisualPiece[16];
    public static VisualPiece[] whitePieces = new VisualPiece[16];
    public boolean pieceSelected = false;

    /**
     * Construct a new Pieces object, which just calls the addPieces method.
     *
     * @param board The board to which the pieces are added.
     */
    public Pieces(JPanel board) {
        addPieces(board);
    }

    /**
     * Adds the visual pieces to the board.
     *
     * @param board The board to which the visual pieces are added.
     */
    public void addPieces(JPanel board) {
        if (ChessMain.debug)
            System.out.println("Add visual pieces");
        for (int i = 0; i < 8; i++) {
            for (int ii = 0; ii < 8; ii++) {
                Piece p = Game.getPiece(i, ii);
                if (p != null) {
                    GuiGame.addVisualPiece(i, ii, p.piece_type, p.isWhite());
                }
            }
        }
    }
}
