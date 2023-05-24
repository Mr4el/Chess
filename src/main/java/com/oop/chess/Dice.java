package com.oop.chess;

import java.util.Random;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

/**
 * This class represents the dice of the chess program.
 */
public class Dice {

    /**
     * Rolls a dice.
     *
     * @param white A boolean indicating whether the player is white or black.
     * @return
     */
    public static PieceEnum roll(boolean white) {
        Piece[][] currentBoard = Game.board;
        boolean pieceIsWhite = white;
        boolean aPieceIsMoveable = findMoveablePiece(currentBoard, pieceIsWhite);

        if (aPieceIsMoveable) {
            boolean pieceFound = false;

            // Re-rolls until a piece has been found that can be moved by the player.
            while (!pieceFound) {
                PieceEnum random = getRandomPiece();
                if (canPieceMove(currentBoard, random, pieceIsWhite)) {
                    pieceFound = true;
                    return random;
                }
            }
        }
        return null;
    }

    /**
     * Creates a random dice roll.
     *
     * @return A piece that corresponds to the dice roll.
     */
    static PieceEnum getRandomPiece() {
        PieceEnum[] values = PieceEnum.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    /**
     * Finds whether the player has any moveable pieces left.
     *
     * @param currentBoard The current chess board.
     * @param pieceIsWhite A boolean indicating whether the player is white or black.
     * @return Whether there exists a moveable piece for the current player.
     */
    static boolean findMoveablePiece(Piece[][] currentBoard, boolean pieceIsWhite) {
        for (Piece p : Game.getPlayerPieces(Game.current_player)) {
            Piece foundPiece = p;
            if (foundPiece != null && (foundPiece.isWhite == pieceIsWhite)) {
                if (!foundPiece.getLegalMoves(p.x, p.y).isEmpty()) {

                    System.out.println(foundPiece.getLegalMoves(p.x,p.y));

                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Checks whether the passed on piece can move.
     *
     * @param currentBoard The current chess board.
     * @param piece The piece to be checked whether it can move.
     * @param pieceIsWhite A boolean indicating whether the piece is white or black.
     * @return Whether the piece can be moved or not.
     */
    static boolean canPieceMove(Piece[][] currentBoard, PieceEnum piece, boolean pieceIsWhite) {
        for (Piece p : Game.getPlayerPieces(Game.current_player)) {
            Piece foundPiece = p;
            if (foundPiece == null)
                continue;

            if ((foundPiece.getType() == piece) && (foundPiece.isWhite == pieceIsWhite)) {
                if (!foundPiece.getLegalMoves(p.x,p.y).isEmpty()) {
                    return true;
                }
            }

        }

        return false;
    }
}
