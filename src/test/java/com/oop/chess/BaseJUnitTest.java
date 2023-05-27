package com.oop.chess;

import com.oop.chess.model.pieces.Pawn;
import com.oop.chess.model.pieces.Piece;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class BaseJUnitTest {

    private static int testCounter = 1;

    @BeforeEach
    public void setUpBase() {
        Game.board = new Piece[8][8];
    }

    @AfterEach
    private void printResult() {
        System.out.println();
        System.out.println("Result:");
        System.out.println();
        System.out.print(Game.getBoardStringRepresentation(false));
        System.out.println();
        testCounter++;
    }

    public void printInitialState() {
        System.out.println("Test number " + testCounter + " " + StringUtils.repeat(".", 76));
        System.out.println("Initial state:");
        System.out.println();
        System.out.print(Game.getBoardStringRepresentation(false));
    }

    public void markLegalMoves(boolean isWhite, ArrayList<int[]> legalMoves) {
        for (int[] item: legalMoves) {
            int x = item[0];
            int y = item[1];
            Pawn marker = new Pawn(isWhite, x, y);
            marker.pieceType = Game.PieceEnum.ANY;
            Game.board[y][x] = marker;
        }
    }

    public void forcePieceMove(Piece piece, int toX, int toY) {
        Game.board[piece.y][piece.x] = null;
        piece.y = toY;
        piece.x = toX;
        Game.board[toY][toX] = piece;
    }
}
