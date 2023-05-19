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

// The communication channel between player and the GUI
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
        initialBGColor = null;
        legalMoves = null;
        initialColorCodes = null;
    }

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
            VisualPiece chosen_piece = (VisualPiece)oldTile.getComponent(0);

            // TODO: Account for pawn promotion

          //TODO: Add to the if statement below that a pawn to be promoted is also to be selected.
            Piece[][] currentBoard = Game.board;
            if (Game.getCurrentPlayer().isWhite()) {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = currentBoard[col][1];
                    if (foundPiece != null && foundPiece.getType() == PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            }
            else {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = currentBoard[col][6];
                    if (foundPiece != null && foundPiece.getType() == PieceEnum.PAWN) {
                        foundPiece.pawnPromotion = true;
                    }
                }
            }
            
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
            
            initialBGColor = oldTile.getBackground();
            oldTile.setBackground(Color.decode("#6ff2ac"));
            recolor = true;

            Piece chosenPiece = Game.getPiece(oldtile_x, oldtile_y);
            legalMoves = chosenPiece.getLegalMoves(oldtile_x, oldtile_y);
            
            if (chosen_piece.white) {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 1);
                    if (foundPiece != null && foundPiece.getType() == PieceEnum.PAWN && foundPiece.isWhite) {

                        Piece promotionSquare = Game.getPiece(col, 0);
                        Piece leftPromotion = Game.getPiece(col + 1, 0);
                        Piece rightPromotion = Game.getPiece(col - 1, 0);

                        if (promotionSquare == null) {
                            int[] move = {col, 0};
                            legalMoves.add(move);
                        }

                        if (leftPromotion != null && !leftPromotion.isWhite) {
                            int[] move = {col + 1, 0};
                            legalMoves.add(move);
                        }

                        if (rightPromotion != null && !rightPromotion.isWhite) {
                            int[] move = {col - 1, 0};
                            legalMoves.add(move);
                        }

                    } else if (foundPiece == null) {
                        System.out.println("");
                    }
                }

            } else {
                for (int col = 0; col < currentBoard.length; col++) {
                    Piece foundPiece = Game.getPiece(col, 6);
                    System.out.println(foundPiece);
                    if (foundPiece != null && foundPiece.getType() == PieceEnum.PAWN && !foundPiece.isWhite) {
                        Piece promotionSquare = Game.getPiece(col, 6 + 1);
                        Piece leftPromotion = Game.getPiece(col + 1, 7);
                        Piece rightPromotion = Game.getPiece(col - 1, 7);

                        if (promotionSquare == null) {
                            int[] move = {col, 6 + 1};
                            legalMoves.add(move);
                        }

                        if (leftPromotion != null && leftPromotion.isWhite) {
                            int[] move = {col + 1, 7};
                            legalMoves.add(move);
                        }

                        if (rightPromotion != null && rightPromotion.isWhite) {
                            int[] move = {col - 1, 7};
                            legalMoves.add(move);
                        }

                    } else if (foundPiece == null) {
                        System.out.println("");
                    }
                }

            }
            
            System.out.println(legalMoves.size());

            initialColorCodes = new ArrayList<>();

            for (int i = 0; i < legalMoves.size(); i++) {
                int tileX = legalMoves.get(i)[0];
                int tileY = legalMoves.get(i)[1];
                System.out.println(tileX + "," + tileY);
                JPanel tile = (JPanel) board.getComponent(tileY * 8 + tileX);
                initialColorCodes.add(tile.getBackground());
                tile.setBackground(Color.decode("#ed8080"));
            }
            
        } else {
            resetVars();
            return;
        }
    }

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
            VisualPiece destination_piece = (VisualPiece) newTile.getComponent(0);

            if (destination_piece.white == Game.current_player.isWhite()) {
                resetVars();
                return;
            }
        }

        Dimension board_dimensions = board.getSize();
        double w = board_dimensions.getWidth();
        double h = board_dimensions.getHeight();

        int newtile_x = (int)Math.round(newTile.getX() * 8 / w);
        int newtile_y = (int)Math.round(newTile.getY() * 8 /h);

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
