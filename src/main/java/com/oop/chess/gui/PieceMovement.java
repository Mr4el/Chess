package com.oop.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

public class PieceMovement extends Pieces implements MouseListener {

    JPanel board, oldTile, newTile;
    JLabel selectedPiece;

    public PieceMovement(JPanel board) {
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent)board);
        //no piece selected
        if (!pieceSelected) {
            oldTile = (JPanel) e.getComponent().getComponentAt(p);
            //if tile has a piece on it
            if (oldTile.getComponents().length >= 1) {
                selectedPiece = (JLabel) oldTile.getComponent(0);
                pieceSelected = true;
            } else {
                return;
            }
            //piece already selected
        } else {
            newTile = (JPanel) e.getComponent().getComponentAt(p);
            //if clicked tile is occupied, remove the piece.
            if (newTile.getComponents().length >= 1) {
                newTile.removeAll();
            }
            newTile.add(selectedPiece);
            oldTile.repaint();
            newTile.repaint();
            resetVars();
        }
    }

    private void resetVars() {
        pieceSelected = false;
        oldTile = null;
        newTile = null;
        selectedPiece = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
