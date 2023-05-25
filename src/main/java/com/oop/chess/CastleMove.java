package com.oop.chess;

import com.oop.chess.model.pieces.Rook;
import com.oop.chess.model.pieces.King;

/**
 * This class is responsible for setting the variables castleright and castleleft of both the rook and the king to false.
 */
public class CastleMove {

    /**
     * Sets the variable castleright of both the rook and the king to false.
     */
    public static void cantCastleRight() {
        Rook.castleRight = false;
        King.castleRight = false;
    }

    /**
     * Sets the variable castleleft of both the rook and the king to false.
     */
    public static void cantCastleLeft() {
        Rook.castleLeft = false;
        King.castleLeft = false;
    }
}