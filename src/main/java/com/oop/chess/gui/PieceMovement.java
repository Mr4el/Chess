package com.oop.chess.gui;

import com.oop.chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import com.oop.chess.Game.PieceEnum;

public class PieceMovement extends Pieces implements MouseListener {

    JPanel board, oldTile, newTile;
    VisualPiece selectedPiece;
    int oldtile_x, oldtile_y;

    ArrayList<int[]> moves;

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

        oldtile_x = (int)Math.round(oldTile.getX()*8/(550.0));
        oldtile_y = (int)Math.round(oldTile.getY()*8/(550.0));


        //if tile has a piece on it
        if (oldTile.getComponents().length >= 1) {
            VisualPiece chosen_piece = (VisualPiece)oldTile.getComponent(0);

            System.out.println(chosen_piece.piece_type);

            if (!(Game.getLegalPiece() == PieceEnum.ANY || Game.getLegalPiece() == chosen_piece.piece_type)) {
                resetVars();
                return;
            }

            if (chosen_piece.white == Game.current_player.isWhite()) {
                selectedPiece = chosen_piece;
                pieceSelected = true;
            } else {
                resetVars();
                return;
            }
        } else {
            resetVars();
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

        if (newTile.getComponents().length >= 1) {
            VisualPiece destination_piece = (VisualPiece)newTile.getComponent(0);

            if (destination_piece.white == Game.current_player.isWhite()) {
                resetVars();
                return;
            }
        }

        int newtile_x = (int)Math.round(newTile.getX()*8/(550.0));
        int newtile_y = (int)Math.round(newTile.getY()*8/(550.0));

        int[] final_pos = {newtile_x, newtile_y};
        moves = Game.getPiece(oldtile_x,oldtile_y).getLegalMoves(oldtile_x, oldtile_y, newtile_x, newtile_y);

        System.out.println(Game.getPiece(oldtile_x,oldtile_y));

        if (moves == null) {
            resetVars();
            return;
        }

        //System.out.println(moves.get(0)[0] + " " + moves.get(0)[1]);

        boolean possible = false;
        check_if_move_is_legal:
        for(int[] pos : moves) {
            if (pos[0] == final_pos[0] && pos[1] == final_pos[1]){
                possible = true;
                break check_if_move_is_legal;
            }
        }

        if (!possible)
            return;

        Game.movePieceTo(oldtile_x, oldtile_y, newtile_x, newtile_y);

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
