package com.oop.chess.gui;

import javax.swing.*;
import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

public class Pieces {

    public static VisualPiece[] blackPieces = new VisualPiece[16];
    public static VisualPiece[] whitePieces = new VisualPiece[16];
    public boolean pieceSelected = false;


    public Pieces(){

    }

    public Pieces(JPanel board) {
        addPieces(board);
    }

    public void addPieces(JPanel board) {
    	for(int i = 0; i < 8; i++){
            for(int ii = 0; ii < 8; ii++) {
                Piece p = Game.getPiece(i,ii);
                if (p != null) {
                    GuiGame.addVisualPiece(i, ii, p.piece_type, p.isWhite());
                }
            }
        }
    }
}
