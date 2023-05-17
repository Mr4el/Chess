package com.oop.chess.gui;

import com.oop.chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

public class PieceMovement extends Pieces implements MouseListener {

    JPanel board, oldTile, newTile;
    VisualPiece selectedPiece;

    public PieceMovement(JPanel board) {
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*Point p = e.getLocationOnScreen();
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
        }*/
    }

    private void resetVars() {
        pieceSelected = false;
        oldTile = null;
        newTile = null;
        selectedPiece = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent)board);

        oldTile = (JPanel) e.getComponent().getComponentAt(p);
        //if tile has a piece on it
        if (oldTile.getComponents().length >= 1) {
            if (((VisualPiece)oldTile.getComponent(0)).white == Game.current_player.isWhite()) {

                selectedPiece = (VisualPiece)oldTile.getComponent(0);
                pieceSelected = true;
            }
        } else {
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent)board);

        if (!pieceSelected)
            return;

        newTile = (JPanel) e.getComponent().getComponentAt(p);

        // tell the game that the player has moved
        Game.getCurrentPlayer().setMoved(true);

        //if clicked tile is occupied, remove the piece.
        if (newTile.getComponents().length >= 1) {
            newTile.removeAll();
        }
        newTile.add(selectedPiece);
        oldTile.repaint();
        newTile.repaint();
        resetVars();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
