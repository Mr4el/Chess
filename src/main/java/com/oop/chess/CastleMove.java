package com.oop.chess;

import com.oop.chess.model.pieces.Rook;
import com.oop.chess.model.pieces.King;

/**
 * This class is responsible for setting the variables castleRight and castleLeft of both the rook and the king to false.
 */
public class CastleMove {

    /**
     * This class is responsible for setting the variables castleRight and castleLeft of both the rook and the king to false.
     */
    public static void cantCastleRight() {
        Rook.castleRight = false;
        King.castleRight = false;
    }


    /**
     * Sets the variable castleLeft of both the rook and the king to false.
     */
    public static void cantCastleLeft() {
        Rook.castleLeft = false;
        King.castleLeft = false;
    }
}
