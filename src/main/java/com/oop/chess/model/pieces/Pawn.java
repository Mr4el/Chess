package com.oop.chess.model.pieces;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;

import java.util.ArrayList;

/**
 * This class represents a Pawn piece of the chess board.
 */
public class Pawn extends Piece {

    // This variable 'enPassantPossible' defines that the most recent move of the pawn is two squares and it is thus possible that it is captured.
    boolean enPassantPossible;

    /**
     * Constructs a Pawn piece using the fact whether it is white or black and its initial location on the board.
     * @param white The boolean determining whether the piece is black or white (true = white and false = black).
     * @param i The initial x-position on the board starting at the bottom left corner.
     * @param j The initial y-position on the board starting at the bottom left corner.
     */
    public Pawn(boolean white, int i, int j) {
        super.isWhite = white;
        super.x = i;
        super.y = j;
        enPassantPossible = false;

        super.piece_type = PieceEnum.PAWN;
    }

    /**
     * Returns an identical Pawn piece.
     * @return a new identical piece.
     */
    @Override
    public Pawn clone() {
        Pawn p = new Pawn(isWhite, x, y);
        p.enPassantPossible = enPassantPossible;
        return p;
    }

    @Override
    public ArrayList<int[]> getLegalMoves(int initialX, int initialY, int finalX, int finalY) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        if (finalX == initialX && finalY == initialY) {
            return null; //cannot move nothing
        }
        if (finalX < 0 || finalX > 7 || initialX < 0 || initialX > 7 || finalY < 0 || finalY > 7 || initialY < 0 || initialY > 7) {
            return null;
        }

        // This is the direction in which a pawn can move
        // For white, it's -1 because white pieces move  upwards, and for black it's 1 because black pieces move downwards
        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction =  1; // down

        // Check if there's a piece already at the final position
        Piece piece = Game.getPiece(finalX, finalY);
        
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

                } else if (foundPiece == null) {
                    System.out.println("");
                }
            }

        } else if (Game.getPiece(initialX, initialY) != null && initialY == 6 && !Game.getPiece(initialX, initialY).isWhite){
            for (int col = 0; col < Game.board.length; col++) {
                Piece foundPiece = Game.getPiece(col, 6);
                System.out.println(foundPiece);
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

                } else if (foundPiece == null) {
                    System.out.println("");
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
            if (enPassantPiece != null && enPassantPiece instanceof Pawn) {
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
        
        Piece piece_in_the_way = Game.getPiece(initialX,initialY + direction);

        if (b == true && piece_in_the_way == null) {
            if (finalY - initialY == 2*direction) {
                if (finalX == initialX && piece == null) {
                    int[] move = {finalX, finalY};
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }


    // Execute specific actions on a move
    public void makeMove(int initialX, int initialY, int finalX, int finalY) {
        Piece piece = Game.getPiece(finalX, finalY);
        
        if (isWhite) {
            if (finalY == 0) {
                createNewPiece(initialX, initialY, isWhite);
            }
        }

        else {
            if (finalY == 7) {
                createNewPiece(initialX, initialY, isWhite);
            }
        }

        int direction;
        if (isWhite)
            direction = -1; // up
        else
            direction =  1; // down

        // en passant is not possible (only moved by 1 tile)
        if (finalY - initialY == direction) {
            if (finalX == initialX && piece == null) {
                enPassantPossible = false;
            }
        }

        // check if enpassant is possible
        boolean b;
        if (isWhite)
            b = (initialY == 6);
        else
            b = (initialY == 1);

        if (b) {
            if (finalY - initialY == 2*direction) {
                if (finalX == initialX && piece == null) {
                    enPassantPossible = true;
                }
            }
        }

        // execute en passant
        Piece enPassantPiece = Game.getPiece(finalX, finalY - direction);
        if (enPassantPiece != null && enPassantPiece instanceof Pawn) {
            if (Math.abs(initialX - finalX) == 1 && ((Pawn) enPassantPiece).enPassantPossible && enPassantPiece.isWhite != isWhite) {
                // delete the en passant piece
                Game.board[finalY - direction][finalX] = null;
                Game.GUIdeletePiece(finalX, finalY - direction);
            }
        }      
    }
    
    public void createNewPiece(int initialX, int initialY, boolean isWhite) {
        switch (Game.getLegalPiece()) {
            case ANY: {
                Game.board[initialY][initialX] = new Pawn(isWhite, initialX, initialY);
                break;
            }
            case PAWN: {
            	//TODO: Create functionality that the player can change it to any other piece, except king.
                Game.board[initialY][initialX] = new Pawn(isWhite, initialX, initialY);
                break;
            }
            case KNIGHT: {
                Game.board[initialY][initialX] = new Knight(isWhite, initialX, initialY);
                break;
            }
            case BISHOP: {
                Game.board[initialY][initialX] = new Bishop(isWhite, initialX, initialY);
                break;
            }
            case ROOK: {
                Game.board[initialY][initialX] = new Rook(isWhite, initialX, initialY);
                break;
            }
            case QUEEN: {
                Game.board[initialY][initialX] = new Queen(isWhite, initialX, initialY);
                break;
            }
        }
    }
}
