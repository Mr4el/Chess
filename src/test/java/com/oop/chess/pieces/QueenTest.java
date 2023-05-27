package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Pawn;
import com.oop.chess.model.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueenTest extends BaseJUnitTest {

    private Queen queen;
    private final int x = 3;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        queen = new Queen(true, x, y);
        Game.board[y][x] = queen;
    }

    @Test
    public void getLegalMovesForAQueen() {
        // Given
        ArrayList<int[]> expectedLegalMoves = legalMovesForQueenOnBlankBoard();
        printInitialState();

        // When
        final ArrayList<int[]> moves = queen.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(27, moves.size());
        for (int i = 0; i < moves.size(); i++) {
            assertArrayEquals(expectedLegalMoves.get(i), moves.get(i));
        }
    }

    @Test
    public void getLegalMovesForAQueenLimitedByWhitePieces() {
        // Given
        Game.board[2][1] = new Pawn(true, 1, 2);
        Game.board[2][3] = new Pawn(true, 3, 2);
        Game.board[2][5] = new Pawn(true, 5, 2);
        Game.board[4][5] = new Pawn(true, 5, 4);
        Game.board[5][4] = new Pawn(true, 4, 5);
        Game.board[5][3] = new Pawn(true, 3, 5);
        Game.board[5][2] = new Pawn(true, 2, 5);
        Game.board[4][1] = new Pawn(true, 1, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = queen.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(5, moves.size());
        assertArrayEquals(new int[]{4, 3}, moves.get(0));
        assertArrayEquals(new int[]{2, 3}, moves.get(1));
        assertArrayEquals(new int[]{4, 4}, moves.get(2));
        assertArrayEquals(new int[]{2, 4}, moves.get(3));
        assertArrayEquals(new int[]{3, 3}, moves.get(4));
    }

    @Test
    public void getLegalMovesForAQueenLimitedByBlackPieces() {
        // Given
        Game.board[3][2] = new Pawn(false, 2, 3);
        Game.board[3][3] = new Pawn(false, 3, 3);
        Game.board[3][4] = new Pawn(false, 4, 3);
        Game.board[4][4] = new Pawn(false, 4, 4);
        Game.board[5][4] = new Pawn(false, 4, 5);
        Game.board[5][3] = new Pawn(false, 3, 5);
        Game.board[5][2] = new Pawn(false, 2, 5);
        Game.board[4][2] = new Pawn(false, 2, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = queen.getLegalMoves(x, y);

        // Then
        markLegalMoves(true, moves);
        assertEquals(8, moves.size());
        assertArrayEquals(new int[]{4, 5}, moves.get(0));
        assertArrayEquals(new int[]{2, 5}, moves.get(1));
        assertArrayEquals(new int[]{4, 3}, moves.get(2));
        assertArrayEquals(new int[]{2, 3}, moves.get(3));
        assertArrayEquals(new int[]{4, 4}, moves.get(4));
        assertArrayEquals(new int[]{2, 4}, moves.get(5));
        assertArrayEquals(new int[]{3, 5}, moves.get(6));
        assertArrayEquals(new int[]{3, 3}, moves.get(7));
    }

    private static ArrayList<int[]> legalMovesForQueenOnBlankBoard() {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        legalMoves.add(new int[]{4, 5});
        legalMoves.add(new int[]{5, 6});
        legalMoves.add(new int[]{6, 7});

        legalMoves.add(new int[]{2, 5});
        legalMoves.add(new int[]{1, 6});
        legalMoves.add(new int[]{0, 7});

        legalMoves.add(new int[]{4, 3});
        legalMoves.add(new int[]{5, 2});
        legalMoves.add(new int[]{6, 1});
        legalMoves.add(new int[]{7, 0});

        legalMoves.add(new int[]{2, 3});
        legalMoves.add(new int[]{1, 2});
        legalMoves.add(new int[]{0, 1});

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
