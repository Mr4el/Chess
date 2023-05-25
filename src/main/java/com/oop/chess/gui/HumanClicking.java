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
import com.oop.chess.model.pieces.Piece;

/**
 * This class serves as the communication channel between the human player and the GUI.
 */
public class HumanClicking implements MouseListener {

    JPanel board, oldTile, newTile;
    VisualPiece selectedPiece;
    int oldTileX, oldTileY;
    boolean pieceSelected;
    public boolean enabled = false;
    ArrayList<int[]> moves, legalMoves;
    ArrayList<Color> initialColorCodes;
    Color initialBgColor;
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
        initialBgColor = null;
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

        if (!enabled) return;

        // What tile is being clicked on?
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, board);

        oldTile = (JPanel) e.getComponent().getComponentAt(p);

        Dimension boardDimensions = board.getSize();
        double w = boardDimensions.getWidth();
        double h = boardDimensions.getHeight();

        oldTileX = (int) Math.round(oldTile.getX() * 8 / w);
        oldTileY = (int) Math.round(oldTile.getY() * 8 / h);

        // If tile has a piece on it
        if (oldTile.getComponents().length >= 1) {
            VisualPiece chosenVisualPiece = (VisualPiece) oldTile.getComponent(0);

            Piece[][] currentBoard = Game.board;
            if (Game.getCurrentPlayer().isWhite()) {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 1);
                    if (foundPiece != null && foundPiece.isWhite() && foundPiece.getType() == PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            } else {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 6);
                    if (foundPiece != null && !foundPiece.isWhite() && foundPiece.getType() == PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            }

            if (
                !(Game.getLegalPiece() == PieceEnum.ANY ||
                    Game.getLegalPiece() == chosenVisualPiece.pieceType ||
                    (
                        Game.getPiece(oldTileX, oldTileY) != null &&
                        Game.getPiece(oldTileX, oldTileY).getType() == PieceEnum.PAWN &&
                        Game.getPiece(oldTileX, oldTileY).pawnPromotion &&
                        Game.getLegalPiece() != PieceEnum.KING
                    )
                )
            ) {
                resetVars();
                return;
            }

            // If piece colour matches current player colour
            if (chosenVisualPiece.white == Game.getCurrentPlayer().isWhite()) {
                selectedPiece = chosenVisualPiece;
                pieceSelected = true;
            } else {
                resetVars();
                return;
            }

            initialBgColor = oldTile.getBackground();
            oldTile.setBackground(Color.decode("#6ff2ac"));
            recolor = true;

            Piece chosenPhysicalPiece = Game.getPiece(oldTileX, oldTileY);
            legalMoves = chosenPhysicalPiece.getLegalMoves(oldTileX, oldTileY);

            System.out.println(legalMoves.size());

            initialColorCodes = new ArrayList<>();

            for (int[] legalMove : legalMoves) {
                int tileX = legalMove[0];
                int tileY = legalMove[1];
                System.out.println(tileX + "," + tileY);
                JPanel tile = (JPanel) board.getComponent(tileY * 8 + tileX);
                initialColorCodes.add(tile.getBackground());
                tile.setBackground(Color.decode("#ed8080"));
            }

        } else {
            resetVars();
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

        if (!enabled) return;
        Point p = e.getLocationOnScreen();
        SwingUtilities.convertPointFromScreen(p, board);

        if (recolor) {
            oldTile.setBackground(initialBgColor);
            for (int i = 0; i < legalMoves.size(); i++) {
                int tileX = legalMoves.get(i)[0];
                int tileY = legalMoves.get(i)[1];
                JPanel tile = (JPanel) board.getComponent(tileY * 8 + tileX);
                tile.setBackground(initialColorCodes.get(i));
            }
            recolor = false;
        }

        if (!pieceSelected)
            return;

        newTile = (JPanel) e.getComponent().getComponentAt(p);

        if (newTile.getComponents().length >= 1) {
            VisualPiece destinationPiece = (VisualPiece) newTile.getComponent(0);

            if (destinationPiece.white == Game.currentPlayer.isWhite()) {
                resetVars();
                return;
            }
        }

        Dimension boardDimensions = board.getSize();
        double w = boardDimensions.getWidth();
        double h = boardDimensions.getHeight();

        int newTileX = (int) Math.round(newTile.getX() * 8 / w);
        int newTileY = (int) Math.round(newTile.getY() * 8 / h);

        int[] finalPosition = {newTileX, newTileY};
        System.out.println(Game.getPiece(oldTileX, oldTileY));
        moves = Game.getPiece(oldTileX, oldTileY).getLegalMoves(oldTileX, oldTileY);

        if (moves == null) {
            resetVars();
            return;
        }

        boolean possible = false;
        for (int[] pos : moves) {
            if (pos[0] == finalPosition[0] && pos[1] == finalPosition[1]) {
                possible = true;
                break;
            }
        }
        if (!possible) return;

        // tell the game that the player has moved
        Game.getCurrentPlayer().setMove(oldTileX, oldTileY, newTileX, newTileY);

        // if clicked tile is occupied, remove the piece.
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
