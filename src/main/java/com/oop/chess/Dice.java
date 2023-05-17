package com.oop.chess;

import java.util.Random;
import com.oop.chess.ChessMain.PieceEnum;

public class Dice {
    // placeholder, return -1 for any piece
    public PieceEnum roll(boolean white) {

        // idea: roll dice, check if possible pieces of colour "white" have any legal moves. if not, roll dice again. if yes, return the random piece type.
        PieceEnum random = getRandomPiece();
        random = PieceEnum.ANY;
        return random;
    }

    static PieceEnum getRandomPiece() {
        PieceEnum[] values = PieceEnum.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
