package com.oop.chess.gui;

import com.oop.chess.ChessMain;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Piece;

/**
 * This class is responsible for adding the visual pieces to the board.
 */
public class Pieces {

    /**
     * Construct a new Pieces object, which just calls the addPieces method.
     */
    public Pieces() {
        addPieces();
    }


    /**
     * Adds the visual pieces to the board.
     */
    public void addPieces() {
        if (ChessMain.debug)
            System.out.println("Add visual pieces");
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece p = Game.getPiece(row, column);
                if (p != null) {
                    GuiGame.addVisualPiece(row, column, p.pieceType, p.isWhite());
                }
            }
        }
    }
}
