package com.oop.chess;

import java.util.Random;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

public class Dice {
    public static PieceEnum roll(boolean white) {
        Piece[][] currentBoard = Game.board;
        boolean pieceIsWhite = white;
        boolean aPieceIsMoveable = findMoveablePiece(currentBoard, pieceIsWhite);

        //TODO: Maybe here add an if-statement which first checks whether a pawn is to be promoted.
        if (aPieceIsMoveable) {
            boolean pieceFound = false;
            while (!pieceFound) {
                PieceEnum random = getRandomPiece();
                if (random == PieceEnum.PAWN) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }

                else if (random == PieceEnum.KNIGHT) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }

                else if (random == PieceEnum.BISHOP) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }

                else if (random == PieceEnum.ROOK) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }

                else if (random == PieceEnum.QUEEN) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }

                else if (random == PieceEnum.KING) {
                    if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                        pieceFound = true;
                        return random;
                    }
                }
            }
        }
        
		return null;
    }

    static PieceEnum getRandomPiece() {
        PieceEnum[] values = PieceEnum.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    static boolean findMoveablePiece(Piece[][] currentBoard, boolean pieceIsWhite) {
        for (int row = 0; row < currentBoard.length; row++) {
            for (int col = 0; col < currentBoard[row].length; col++) {
                Piece foundPiece = currentBoard[row][col];
                if (foundPiece != null && (foundPiece.isWhite == pieceIsWhite)) {
                    if (!foundPiece.getLegalMoves(col, row).isEmpty()) {
                        return true;

                    }
                }
            }
        }
        return false;
    }


    static boolean canPieceMove(Piece[][] currentBoard, PieceEnum piece, boolean pieceIsWhite) {
        for (int row = 0; row < currentBoard.length; row++) {
            for (int col = 0; col < currentBoard[row].length; col++) {
                Piece foundPiece = currentBoard[row][col];
                if (foundPiece == null)
                	continue;
                
                if ((foundPiece.getType() == piece) && (foundPiece.isWhite == pieceIsWhite)) {
                    if (!foundPiece.getLegalMoves(row, col).isEmpty()) {
                        return true;

                    }
                }
            }
        }

        return false;
    }
}
