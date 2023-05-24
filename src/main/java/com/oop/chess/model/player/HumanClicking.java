package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.gui.VisualPiece;
import com.oop.chess.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 * This class serves as the communication channel between the human player and the GUI.
 */
public class HumanClicking implements MouseListener {
    JPanel board, oldTile, newTile;
    VisualPiece selectedPiece;
    int oldtile_x, oldtile_y;
    boolean pieceSelected;
    public boolean appliedToBoard = false;
    public boolean enabled = false;
    ArrayList<int[]> moves, legalMoves;
    Piece[][] currentBoard = Game.board;
    ArrayList<Color> initialColorCodes;
    Color initialBGColor;
    boolean recolor;

    Human human;

    /**
     * Creates a new communication channel between the human player and the GUI.
     *
     * @param human The human player which will interact with the GUI.
     */
    public HumanClicking(Human human) {
        board = GuiGame.visualBoard;
        board.addMouseListener(this);
        this.human = human;
    }

    /**
     /**
     * Handles what happens whenever the mouse has clicked.
     *
     * @param e The mouse event of the mouse clicking.
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Resets all the previously defined variables to null, which happens when for example the player selects a piece which is not allowed to move.
     */
    private void resetVars() {
        pieceSelected = false;
        oldTile = null;
        newTile = null;
        selectedPiece = null;
        initialBGColor = null;
        legalMoves = null;
        initialColorCodes = null;
    }

    /**
     * Handles what happens whenever the mouse is pressed.
     *
     * @param e The mouse event of the mouse being pressed.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        if (!enabled)
            return;

        // What tile is being clicked on?
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent) board);

        oldTile = (JPanel) e.getComponent().getComponentAt(p);

        Dimension board_dimensions = board.getSize();
        double w = board_dimensions.getWidth();
        double h = board_dimensions.getHeight();

        oldtile_x = (int) Math.round(oldTile.getX() * 8 / w);
        oldtile_y = (int) Math.round(oldTile.getY() * 8 / h);

        // If tile has a piece on it
        if (oldTile.getComponents().length >= 1) {
            VisualPiece chosen_piece = (VisualPiece) oldTile.getComponent(0);

            Piece[][] currentBoard = Game.board;
            // Checks whether any white pieces are eligible for pawn promotion.
            if (Game.getCurrentPlayer().isWhite()) {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 1);
                    if (foundPiece != null && foundPiece.isWhite() && foundPiece.getType() == Game.PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            }
            // Checks whether any black pieces are eligible for pawn promotion.
            else {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 6);
                    if (foundPiece != null && !foundPiece.isWhite() && foundPiece.getType() == Game.PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            }

            //Checks whether the player can actually move the piece selected, otherwise resets the variables.
            if (!(Game.getLegalPiece() == Game.PieceEnum.ANY ||
                    Game.getLegalPiece() == chosen_piece.piece_type ||
                    (Game.getPiece(oldtile_x, oldtile_y) != null &&
                            Game.getPiece(oldtile_x, oldtile_y).getType() == Game.PieceEnum.PAWN &&
                            Game.getPiece(oldtile_x, oldtile_y).pawnPromotion == true &&
                            Game.getLegalPiece() != Game.PieceEnum.KING))) {
                resetVars();
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

            initialBGColor = oldTile.getBackground();
            oldTile.setBackground(Color.decode("#6ff2ac"));
            recolor = true;

            Piece chosenPiece = Game.getPiece(oldtile_x, oldtile_y);
            legalMoves = chosenPiece.getLegalMoves(oldtile_x, oldtile_y);

            // Checks whether the human player has help and then color the tiles which represent possible moves of the current selected piece.
            if (((Human)Game.current_player).hasHelp()) {
                initialColorCodes = new ArrayList<>();

                for (int i = 0; i < legalMoves.size(); i++) {
                    int tileX = legalMoves.get(i)[0];
                    int tileY = legalMoves.get(i)[1];
                    JPanel tile = (JPanel) board.getComponent(tileY * 8 + tileX);
                    initialColorCodes.add(tile.getBackground());
                    tile.setBackground(Color.decode("#ed8080"));
                }
            }


        } else {
            resetVars();
            return;
        }
    }

    /**
     * Handles what happens whenever the mouse is released.
     *
     * @param e The mouse event of the mouse being released.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        if (!enabled)
            return;
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, (JComponent) board);


        if (recolor) {
            oldTile.setBackground(initialBGColor);

            if (((Human)Game.current_player).hasHelp()) {
                for (int i = 0; i < legalMoves.size(); i++) {
                    int tileX = legalMoves.get(i)[0];
                    int tileY = legalMoves.get(i)[1];
                    JPanel tile = (JPanel) board.getComponent(tileY * 8 + tileX);
                    tile.setBackground(initialColorCodes.get(i));
                }
            }
            recolor = false;
        }


        if (!pieceSelected)
            return;

        newTile = (JPanel) e.getComponent().getComponentAt(p);

        if (newTile.getComponents().length >= 1) {
            VisualPiece destination_piece = (VisualPiece) newTile.getComponent(0);

            if (destination_piece.white == Game.current_player.isWhite()) {
                resetVars();
                return;
            }
        }

        Dimension board_dimensions = board.getSize();
        double w = board_dimensions.getWidth();
        double h = board_dimensions.getHeight();

        int newtile_x = (int) Math.round(newTile.getX() * 8 / w);
        int newtile_y = (int) Math.round(newTile.getY() * 8 / h);

        int[] final_pos = {newtile_x, newtile_y};
        moves = Game.getPiece(oldtile_x, oldtile_y).getLegalMoves(oldtile_x, oldtile_y);

        if (moves == null) {
            resetVars();
            return;
        }

        boolean possible = false;
        check_if_move_is_legal:
        for (int[] pos : moves) {
            if (pos[0] == final_pos[0] && pos[1] == final_pos[1]) {
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

    /**
     * Handles what happens whenever the mouse enters the board.
     *
     * @param e The mouse event of the mouse enters the board.
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Handles what happens whenever the mouse exits the board.
     *
     * @param e The mouse event of the mouse exits the board.
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

