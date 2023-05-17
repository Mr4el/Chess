package com.oop.chess.gui;

import com.oop.chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.player.Human;

// The communication channel between player and the GUI
public class HumanClicking implements MouseListener {

    JPanel board, oldTile, newTile;
    VisualPiece selectedPiece;
    int oldtile_x, oldtile_y;
    boolean pieceSelected;
    public boolean appliedToBoard = false;
    public boolean enabled = false;
    ArrayList<int[]> moves;

    Human human;

    public HumanClicking(Human human) {
        board = GuiGame.visualBoard;
        board.addMouseListener(this);
        this.human = human;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private void resetVars() {
        pieceSelected = false;
        oldTile = null;
        newTile = null;
        selectedPiece = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!enabled)
            return;

        // What tile is being clicked on?
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent)board);

        oldTile = (JPanel) e.getComponent().getComponentAt(p);

        Dimension board_dimensions = board.getSize();
        double w = board_dimensions.getWidth();
        double h = board_dimensions.getHeight();

        oldtile_x = (int)Math.round(oldTile.getX()*8/w);
        oldtile_y = (int)Math.round(oldTile.getY()*8/h);

        // If tile has a piece on it
        if (oldTile.getComponents().length >= 1) {
            VisualPiece chosen_piece = (VisualPiece)oldTile.getComponent(0);

            // TODO: Account for pawn promotion

            if (!(Game.getLegalPiece() == PieceEnum.ANY || Game.getLegalPiece() == chosen_piece.piece_type)) {
                resetVars();
                //TODO: Maybe display a message on the side of the board displaying that we cannot choose this piece.
                return;
            }

            // If piece colour matches current player colour
            if (chosen_piece.white == Game.getCurrentPlayer().isWhite()) {
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
        if (!enabled)
            return;
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

        Dimension board_dimensions = board.getSize();
        double w = board_dimensions.getWidth();
        double h = board_dimensions.getHeight();

        int newtile_x = (int)Math.round(newTile.getX()*8/w);
        int newtile_y = (int)Math.round(newTile.getY()*8/h);

        int[] final_pos = {newtile_x, newtile_y};
        moves = Game.getPiece(oldtile_x,oldtile_y).getLegalMoves(oldtile_x, oldtile_y, newtile_x, newtile_y);

        if (moves == null) {
            resetVars();
            return;
        }

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

        // tell the game that the player has moved
        Game.getCurrentPlayer().setMove(oldtile_x, oldtile_y, newtile_x, newtile_y);

        //if clicked tile is occupied, remove the piece.
        resetVars();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
