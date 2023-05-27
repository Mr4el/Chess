package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Knight;
import com.oop.chess.model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnightTest extends BaseJUnitTest {

    private Knight knight;
    private final int x = 2;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        knight = new Knight(true, x, y);
        Game.board[y][x] = knight;
    }

    @Test
    public void getLegalMovesForAKing() {
        // Given
        ArrayList<int[]> expectedLegalMoves = legalMovesForKnightOnBlankBoard();
        printInitialState();

        // When
        final ArrayList<int[]> moves = knight.getLegalMoves(x, y);
        KnightTest.LegalMovesComparator comparator = new KnightTest.LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(8, moves.size());
        for (int i = 0; i < moves.size(); i++) {
            assertArrayEquals(expectedLegalMoves.get(i), moves.get(i));
        }
    }

    @Test
    public void getLegalMovesForAKnightLimitedByWhitePieces() {
        // Given
        Game.board[3][0] = new Pawn(true, 0, 3);
        Game.board[2][3] = new Pawn(true, 3, 2);
        Game.board[3][4] = new Pawn(true, 4, 3);
        Game.board[1][0] = new Pawn(true, 0, 1);
        Game.board[6][6] = new Pawn(true, 6, 6);
        Game.board[7][3] = new Pawn(true, 3, 7);
        printInitialState();

        // When
        final ArrayList<int[]> moves = knight.getLegalMoves(x, y);
        KnightTest.LegalMovesComparator comparator = new KnightTest.LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(5, moves.size());
        assertArrayEquals(new int[]{0, 5}, moves.get(0));
        assertArrayEquals(new int[]{1, 2}, moves.get(1));
        assertArrayEquals(new int[]{1, 6}, moves.get(2));
        assertArrayEquals(new int[]{3, 6}, moves.get(3));
        assertArrayEquals(new int[]{4, 5}, moves.get(4));
    }

    @Test
    public void getLegalMovesForAKingLimitedByBlackPieces() {
        // Given
        Game.board[3][0] = new Pawn(false, 0, 3);
        Game.board[2][3] = new Pawn(false, 3, 2);
        Game.board[3][4] = new Pawn(false, 4, 3);
        Game.board[6][1] = new Pawn(false, 1, 6);
        Game.board[1][0] = new Pawn(false, 0, 1);
        Game.board[6][6] = new Pawn(false, 6, 6);
        Game.board[7][3] = new Pawn(false, 3, 7);
        printInitialState();

        // When
        final ArrayList<int[]> moves = knight.getLegalMoves(x, y);
        KnightTest.LegalMovesComparator comparator = new KnightTest.LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(8, moves.size());
        assertArrayEquals(new int[]{0, 3}, moves.get(0));
        assertArrayEquals(new int[]{0, 5}, moves.get(1));
        assertArrayEquals(new int[]{1, 2}, moves.get(2));
        assertArrayEquals(new int[]{1, 6}, moves.get(3));
        assertArrayEquals(new int[]{3, 2}, moves.get(4));
        assertArrayEquals(new int[]{3, 6}, moves.get(5));
        assertArrayEquals(new int[]{4, 3}, moves.get(6));
        assertArrayEquals(new int[]{4, 5}, moves.get(7));
    }

    private static ArrayList<int[]> legalMovesForKnightOnBlankBoard() {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        legalMoves.add(new int[]{0, 3});
        legalMoves.add(new int[]{0, 5});

        legalMoves.add(new int[]{1, 2});
        legalMoves.add(new int[]{1, 6});

        legalMoves.add(new int[]{3, 2});
        legalMoves.add(new int[]{3, 6});

        legalMoves.add(new int[]{4, 3});
        legalMoves.add(new int[]{4, 5});

        return legalMoves;
    }

    class LegalMovesComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] move1, int[] move2) {
            if (move1[0] != move2[0]) {
                return Integer.compare(move1[0], move2[0]);
            } else {
                return Integer.compare(move1[1], move2[1]);
            }
        }
    }
}
