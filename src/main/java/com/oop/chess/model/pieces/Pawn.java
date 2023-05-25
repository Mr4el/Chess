package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.gui.GuiGame;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a Pawn piece of the chess board.
 */
public class Pawn extends Piece {

    // This variable 'enPassantPossible' defines that the most recent move of the pawn is two squares and it is thus possible that it is captured.
    public boolean enPassantPossible;


    /**
     * Constructs a Pawn piece using the fact whether it is white or black and its initial location on the board.
     *
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i     The initial x-position on the board starting.
     * @param j     The initial y-position on the board starting.
     */
    public Pawn(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        enPassantPossible = false;
        super.pieceType = PieceEnum.PAWN;
    }


    /**
     * Returns an identical Pawn piece.
     *
     * @return a new identical piece.
     */
    @Override
    public Pawn clone() {
        Pawn p = new Pawn(isWhite, x, y);
        p.enPassantPossible = enPassantPossible;
        return p;
    }


    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        // In which direction can the pawn move?
        int direction = (isWhite() ? -1 : 1);

        // All possible directions the pawn can move in
        int[][] positionsToCheck = {
            {-1, direction}, // 1 forward, left
            {0, direction}, // 1 forward
            {1, direction}, // 1 forward, right
            {0, 2 * direction}  // 2 forward (first move)
        };

        // Loop through the positions to check, add to legalMoves if legal
        for (int[] p : positionsToCheck) {
            int finalX = initialX + p[0];
            int finalY = initialY + p[1];

            if (Game.xyInBounds(finalX, finalY)) {
                ArrayList<int[]> moves = getLegalMoves(initialX, initialY, finalX, finalY);
                if (!moves.isEmpty())
                    legalMoves.addAll(moves);
            }
        }

        return legalMoves;
    }


    /**
     * Retrieves all the legal moves of the current piece.
     *
     * @param initialX The initial X-coordinate of the piece.
     * @param initialY The initial Y-coordinate of the piece.
     * @param finalX   The final X-coordinate of the piece.
     * @param finalY   The final Y-coordinate of the piece.
     * @return Returns an array of all the legal moves.
     */
    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        if (finalX == initialX && finalY == initialY) {
            return null;
        }
        if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
            return null;
        }

        // This is the direction in which a pawn can move
        // For white, it's -1 because white pieces move upwards, and for black it's 1 because black pieces move downwards
        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction = 1; // down

        // Check if there's a piece already at the final position
        Piece piece = Game.getPiece(finalX, finalY);

        // Checks whether there exist white pawns on the board that can be promoted and adds the legal moves to the array list of all legal moves.
        // It also takes into account whether it can also conquer another piece, thus make a move to a left and one up tile or to a right and one up tile.
        if (Game.getPiece(initialX, initialY) != null && initialY == 1 && Game.getPiece(initialX, initialY).isWhite) {
            for (int col = 0; col < Game.board.length; col++) {
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

                }
            }
        }

        // Checks whether there exist white pawns on the board that can be promoted and adds the legal moves to the array list of all legal moves.
        // It also takes into account whether it can also conquer another piece, thus make a move to a left and one down tile or to a right and one down tile.
        else if (Game.getPiece(initialX, initialY) != null && initialY == 6 && !Game.getPiece(initialX, initialY).isWhite) {
            for (int col = 0; col < Game.board.length; col++) {
                Piece foundPiece = Game.getPiece(col, 6);
                if (foundPiece != null && foundPiece.getType() == PieceEnum.PAWN && !foundPiece.isWhite) {
                    Piece promotionSquare = Game.getPiece(col, 7);
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

                }
            }

        }

        // 1 tile movement
        if (finalY - initialY == direction) {
            // Same column, only move forwards if the space in front of the pawn is empty
            if (finalX == initialX && piece == null) {
                int[] move = {finalX, finalY};
                legalMoves.add(move);
            }

            // Capture a piece via diagonal movement
            if (Math.abs(initialX - finalX) == 1 && piece != null && piece.isWhite != isWhite) {
                int[] move = {finalX, finalY};
                legalMoves.add(move);
            }

            // En-passant movement
            Piece enPassantPiece = Game.getPiece(finalX, finalY - direction);
            if (enPassantPiece instanceof Pawn) {
                if (Math.abs(initialX - finalX) == 1 && ((Pawn) enPassantPiece).enPassantPossible && enPassantPiece.isWhite != isWhite) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }
            }
        }

        // 2 tile movement
        // Check if the pawn is on its first move
        boolean b;
        if (isWhite)
            b = (initialY == 6);
        else
            b = (initialY == 1);

        Piece pieceInTheWay = Game.getPiece(initialX, initialY + direction);

        if (b && pieceInTheWay == null) {
            if (finalY - initialY == 2 * direction) {
                if (finalX == initialX && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }


    /**
     * Makes a move from one to another tile.
     *
     * @param initialX The initial X-coordinate of the pawn.
     * @param initialY The initial Y-coordinate of the pawn.
     * @param finalX   The final X-coordinate of the pawn.
     * @param finalY   The final Y-coordinate of the pawn.
     */
    public void makeMove(int initialX, int initialY, int finalX, int finalY) {
        Piece piece = Game.getPiece(finalX, finalY);

        if (isWhite) {
            if (finalY == 0) {
                createNewPiece(initialX, initialY, isWhite);
            }
        } else {
            if (finalY == 7) {
                createNewPiece(initialX, initialY, isWhite);
            }
        }


        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction = 1; // down

        // en passant is not possible (only moved by 1 tile)
        if (finalY - initialY == direction) {
            if (finalX == initialX && piece == null) {
                enPassantPossible = false;
            }
        }

        // Check if en passant is possible
        boolean b;
        if (isWhite)
            b = (initialY == 6);
        else
            b = (initialY == 1);

        if (b) {
            if (finalY - initialY == 2 * direction) {
                if (finalX == initialX && piece == null) {
                    enPassantPossible = true;
                }
            }
        }

        // execute en passant
        Piece enPassantPiece = Game.getPiece(finalX, finalY - direction);
        if (enPassantPiece instanceof Pawn) {
            if (Math.abs(initialX - finalX) == 1 && ((Pawn) enPassantPiece).enPassantPossible && enPassantPiece.isWhite != isWhite) {
                // delete the en passant piece
                Game.board[finalY - direction][finalX] = null;
                Game.deletePieceGUI(finalX, finalY - direction);
            }
        }
    }


    /**
     * Creates a new piece whenever a pawn is promoted.
     *
     * @param initialX The initial X-coordinate of the pawn.
     * @param initialY The initial Y-coordinate of the pawn.
     * @param isWhite  A boolean to denote whether the pawn is white or black.
     */
    public void createNewPiece(int initialX, int initialY, boolean isWhite) {
        switch (Game.getLegalPiece()) {
            case ANY -> Game.board[initialY][initialX] = new Pawn(isWhite, initialX, initialY);
            case PAWN -> {
                if (isWhite) {
                    switch (Objects.requireNonNull(GuiGame.whiteComboBox.getSelectedItem()).toString()) {
                        case "Pawn" -> Game.board[initialY][initialX] = new Pawn(true, initialX, initialY);
                        case "Knight" -> Game.board[initialY][initialX] = new Knight(true, initialX, initialY);
                        case "Rook" -> Game.board[initialY][initialX] = new Rook(true, initialX, initialY);
                        case "Bishop" -> Game.board[initialY][initialX] = new Bishop(true, initialX, initialY);
                        case "Queen" -> Game.board[initialY][initialX] = new Queen(true, initialX, initialY);
                    }
                } else {
                    switch (Objects.requireNonNull(GuiGame.blackComboBox.getSelectedItem()).toString()) {
                        case "Pawn" -> Game.board[initialY][initialX] = new Pawn(false, initialX, initialY);
                        case "Knight" -> Game.board[initialY][initialX] = new Knight(false, initialX, initialY);
                        case "Rook" -> Game.board[initialY][initialX] = new Rook(false, initialX, initialY);
                        case "Bishop" -> Game.board[initialY][initialX] = new Bishop(false, initialX, initialY);
                        case "Queen" -> Game.board[initialY][initialX] = new Queen(false, initialX, initialY);
                    }
                }
            }
            case KNIGHT -> Game.board[initialY][initialX] = new Knight(isWhite, initialX, initialY);
            case BISHOP -> Game.board[initialY][initialX] = new Bishop(isWhite, initialX, initialY);
            case ROOK -> Game.board[initialY][initialX] = new Rook(isWhite, initialX, initialY);
            case QUEEN -> Game.board[initialY][initialX] = new Queen(isWhite, initialX, initialY);
        }
    }
}
