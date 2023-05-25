package com.oop.chess.gui;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.oop.chess.Game.PieceEnum;

/**
 * This class represents a visual piece.
 */
public class VisualPiece extends JLabel {
    public boolean white;
    public PieceEnum piece_type;

    /**
     * Constructs a new visual type.
     *
     * @param icon The image of the piece to be created.
     * @param white Whether the piece to be created is white or black.
     * @param piece_type The type of the piece to be created.
     */
    public VisualPiece(ImageIcon icon, boolean white, PieceEnum piece_type) {
        super(icon);
        this.white = white;
        this.piece_type = piece_type;
    }
}
