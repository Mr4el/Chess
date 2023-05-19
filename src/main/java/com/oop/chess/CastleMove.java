package com.oop.chess;

import com.oop.chess.model.pieces.Rook;
import com.oop.chess.model.pieces.King;

public class CastleMove {
    public static void cantCastleRight() {
        Rook.castleRight = false;
        King.castleRight = false;
    }

    public static void cantCastleLeft() {
        Rook.castleLeft = false;
        King.castleLeft = false;
    }
}