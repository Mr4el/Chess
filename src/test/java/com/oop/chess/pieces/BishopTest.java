package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Bishop;
import com.oop.chess.model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTest extends BaseJUnitTest {

    private Bishop bishop;
    private final int x = 2;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        bishop = new Bishop(true, x, y);
        Game.board[y][x] = bishop;
    }

    @Test
    public void check() {
        printInitialState();
        System.out.println(bishop.getLegalMoves(x, y));
    }

    @Test
    public void getLegalMovesForABishop() {
        // Given
        ArrayList<int[]> expectedLegalMoves = legalMovesForBishopOnBlankBoard();
        BishopTest.LegalMovesComparator comparator = new BishopTest.LegalMovesComparator();
        Collections.sort(expectedLegalMoves, comparator);
        printInitialState();

        // When
        final ArrayList<int[]> moves = bishop.getLegalMoves(x, y);
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(11, moves.size());
        for (int i = 0; i < moves.size(); i++) {
            assertArrayEquals(expectedLegalMoves.get(i), moves.get(i));
        }
    }

    @Test
    public void getLegalMovesForABishopLimitedByWhitePieces() {
        // Given
        Game.board[2][0] = new Pawn(true, 0, 2);
        Game.board[6][4] = new Pawn(true, 4, 6);
        Game.board[5][1] = new Pawn(true, 1, 5);
        Game.board[1][5] = new Pawn(true, 5, 1);
        printInitialState();

        // When
        final ArrayList<int[]> moves = bishop.getLegalMoves(x, y);
        BishopTest.LegalMovesComparator comparator = new BishopTest.LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(4, moves.size());
        assertArrayEquals(new int[]{1, 3}, moves.get(0));
        assertArrayEquals(new int[]{3, 3}, moves.get(1));
        assertArrayEquals(new int[]{3, 5}, moves.get(2));
        assertArrayEquals(new int[]{4, 2}, moves.get(3));
    }

    @Test
    public void getLegalMovesForAKingLimitedByBlackPieces() {
        // Given
        Game.board[2][0] = new Pawn(false, 0, 2);
        Game.board[3][1] = new Pawn(false, 1, 3);
        Game.board[7][5] = new Pawn(false, 5, 7);
        Game.board[6][0] = new Pawn(false, 0, 6);
        Game.board[5][1] = new Pawn(false, 1, 5);
        Game.board[2][4] = new Pawn(false, 4, 2);
        Game.board[0][6] = new Pawn(false, 6, 0);
        printInitialState();

        // When
        final ArrayList<int[]> moves = bishop.getLegalMoves(x, y);
        BishopTest.LegalMovesComparator comparator = new BishopTest.LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(7, moves.size());
        assertArrayEquals(new int[]{1, 3}, moves.get(0));
        assertArrayEquals(new int[]{1, 5}, moves.get(1));
        assertArrayEquals(new int[]{3, 3}, moves.get(2));
        assertArrayEquals(new int[]{3, 5}, moves.get(3));
        assertArrayEquals(new int[]{4, 2}, moves.get(4));
        assertArrayEquals(new int[]{4, 6}, moves.get(5));
        assertArrayEquals(new int[]{5, 7}, moves.get(6));
    }

    private static ArrayList<int[]> legalMovesForBishopOnBlankBoard() {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        legalMoves.add(new int[]{0, 2});
        legalMoves.add(new int[]{1, 3});
        legalMoves.add(new int[]{3, 5});
        legalMoves.add(new int[]{4, 6});
        legalMoves.add(new int[]{5, 7});

        legalMoves.add(new int[]{0, 6});
        legalMoves.add(new int[]{1, 5});
        legalMoves.add(new int[]{3, 3});
        legalMoves.add(new int[]{4, 2});
        legalMoves.add(new int[]{5, 1});
        legalMoves.add(new int[]{6, 0});

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
