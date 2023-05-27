package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.King;
import com.oop.chess.model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KingTest extends BaseJUnitTest {

    private King king;
    private final int x = 2;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        king = new King(true, x, y);
        Game.board[y][x] = king;
    }

    @Test
    public void getLegalMovesForAKing() {
        // Given
        ArrayList<int[]> expectedLegalMoves = legalMovesForKingOnBlankBoard();
        printInitialState();

        // When
        final ArrayList<int[]> moves = king.getLegalMoves(x, y);
        LegalMovesComparator comparator = new LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(8, moves.size());
        for (int i = 0; i < moves.size(); i++) {
            assertArrayEquals(expectedLegalMoves.get(i), moves.get(i));
        }
    }

    @Test
    public void getLegalMovesForAKingLimitedByWhitePieces() {
        // Given
        Game.board[3][1] = new Pawn(true, 1, 3);
        Game.board[5][1] = new Pawn(true, 1, 5);
        Game.board[3][2] = new Pawn(true, 2, 3);
        Game.board[4][3] = new Pawn(true, 3, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = king.getLegalMoves(x, y);
        LegalMovesComparator comparator = new LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(4, moves.size());
        assertArrayEquals(new int[]{1, 4}, moves.get(0));
        assertArrayEquals(new int[]{2, 5}, moves.get(1));
        assertArrayEquals(new int[]{3, 3}, moves.get(2));
        assertArrayEquals(new int[]{3, 5}, moves.get(3));
    }

    @Test
    public void getLegalMovesForAKingLimitedByBlackPieces() {
        // Given
        Game.board[3][1] = new Pawn(false, 1, 3);
        Game.board[5][1] = new Pawn(false, 1, 5);
        Game.board[3][2] = new Pawn(false, 2, 3);
        Game.board[4][3] = new Pawn(false, 3, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = king.getLegalMoves(x, y);
        LegalMovesComparator comparator = new LegalMovesComparator();
        Collections.sort(moves, comparator);

        // Then
        markLegalMoves(true, moves);
        assertEquals(8, moves.size());
        assertArrayEquals(new int[]{1, 3}, moves.get(0));
        assertArrayEquals(new int[]{1, 4}, moves.get(1));
        assertArrayEquals(new int[]{1, 5}, moves.get(2));
        assertArrayEquals(new int[]{2, 3}, moves.get(3));
        assertArrayEquals(new int[]{2, 5}, moves.get(4));
        assertArrayEquals(new int[]{3, 3}, moves.get(5));
        assertArrayEquals(new int[]{3, 4}, moves.get(6));
        assertArrayEquals(new int[]{3, 5}, moves.get(7));
    }

    private static ArrayList<int[]> legalMovesForKingOnBlankBoard() {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        legalMoves.add(new int[]{1, 3});
        legalMoves.add(new int[]{1, 4});
        legalMoves.add(new int[]{1, 5});

        legalMoves.add(new int[]{2, 3});
        legalMoves.add(new int[]{2, 5});

        legalMoves.add(new int[]{3, 3});
        legalMoves.add(new int[]{3, 4});
        legalMoves.add(new int[]{3, 5});

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
