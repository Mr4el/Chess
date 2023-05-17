package com.oop.chess.gui;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.oop.chess.Game.PieceEnum;

public class VisualPiece extends JLabel {
    public boolean white;
    public PieceEnum piece_type;

    public VisualPiece(ImageIcon icon, boolean white, PieceEnum piece_type) {
        super(icon);
        this.white = white;
        this.piece_type = piece_type;
    }
}
