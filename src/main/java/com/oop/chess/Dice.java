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
     */
    public static PieceEnum roll(boolean white) {
        boolean aPieceIsMovable = findMovablePiece(white);

        if (aPieceIsMovable) {
            // Re-rolls until a piece has been found that can be moved by the player.
            while (true) {
                PieceEnum random = getRandomPiece();
                if (canPieceMove(random, white)) {
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
     * Finds whether the player has any movable pieces left.
     *
     * @param pieceIsWhite A boolean indicating whether the player is white or black.
     * @return Whether there exists a movable piece for the current player.
     */
    static boolean findMovablePiece(boolean pieceIsWhite) {
        for (Piece p : Game.getPlayerPieces(Game.currentPlayer)) {
            if (p != null && (p.isWhite == pieceIsWhite)) {
                if (!p.getLegalMoves(p.x, p.y).isEmpty()) {
                    return true;
                }
            }

        }
        return false;
    }


    /**
     * Checks whether the passed on piece can move.
     *
     * @param piece        The piece to be checked whether it can move.
     * @param pieceIsWhite A boolean indicating whether the piece is white or black.
     * @return Whether the piece can be moved or not.
     */
    static boolean canPieceMove(PieceEnum piece, boolean pieceIsWhite) {
        for (Piece p : Game.getPlayerPieces(Game.currentPlayer)) {
            if (p == null)
                continue;

            if ((p.getType() == piece) && (p.isWhite == pieceIsWhite)) {
                if (!p.getLegalMoves(p.x, p.y).isEmpty()) {
                    return true;
                }
            }

        }
        return false;
    }
}
