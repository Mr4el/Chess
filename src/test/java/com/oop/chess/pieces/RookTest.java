package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Pawn;
import com.oop.chess.model.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RookTest extends BaseJUnitTest {

    private Rook rook;
    private final int x = 3;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        rook = new Rook(true, x, y);
        Game.board[y][x] = rook;
    }

    @Test
    public void getLegalMovesForARook() {
        // Given
        ArrayList<int[]> expectedLegalMoves = legalMovesForRookOnBlankBoard();
        printInitialState();

        // When
        final ArrayList<int[]> moves = rook.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(14, moves.size());
        for (int i = 0; i < moves.size(); i++) {
            assertArrayEquals(expectedLegalMoves.get(i), moves.get(i));
        }
    }

    @Test
    public void getLegalMovesForARookLimitedByWhitePieces() {
        // Given
        Game.board[2][3] = new Pawn(true, 3, 2);
        Game.board[4][5] = new Pawn(true, 5, 4);
        Game.board[5][3] = new Pawn(true, 3, 5);
        Game.board[4][1] = new Pawn(true, 1, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = rook.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(3, moves.size());
        assertArrayEquals(new int[]{4, 4}, moves.get(0));
        assertArrayEquals(new int[]{2, 4}, moves.get(1));
        assertArrayEquals(new int[]{3, 3}, moves.get(2));
    }

    @Test
    public void getLegalMovesForARookLimitedByBlackPieces() {
        // Given
        Game.board[3][3] = new Pawn(false, 3, 3);
        Game.board[4][4] = new Pawn(false, 4, 4);
        Game.board[5][3] = new Pawn(false, 3, 5);
        Game.board[4][2] = new Pawn(false, 2, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = rook.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(4, moves.size());
        assertArrayEquals(new int[]{4, 4}, moves.get(0));
        assertArrayEquals(new int[]{2, 4}, moves.get(1));
        assertArrayEquals(new int[]{3, 5}, moves.get(2));
        assertArrayEquals(new int[]{3, 3}, moves.get(3));
    }

    private static ArrayList<int[]> legalMovesForRookOnBlankBoard() {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        legalMoves.add(new int[]{4, 4});
        legalMoves.add(new int[]{5, 4});
        legalMoves.add(new int[]{6, 4});
        legalMoves.add(new int[]{7, 4});

        legalMoves.add(new int[]{2, 4});
        legalMoves.add(new int[]{1, 4});
        legalMoves.add(new int[]{0, 4});

        legalMoves.add(new int[]{3, 5});
        legalMoves.add(new int[]{3, 6});
        legalMoves.add(new int[]{3, 7});

        legalMoves.add(new int[]{3, 3});
        legalMoves.add(new int[]{3, 2});
        legalMoves.add(new int[]{3, 1});
        legalMoves.add(new int[]{3, 0});

        return legalMoves;
    }
}
