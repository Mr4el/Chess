package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.model.pieces.Pawn;
import com.oop.chess.model.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PawnTest extends BaseJUnitTest {

    private Pawn whitePawn;
    private final int wX = 2;
    private final int wY = 4;

    private Pawn blackPawn;
    private final int bX = 5;
    private final int bY = 3;

    @BeforeEach
    private void setUp() {
        whitePawn = new Pawn(true, wX, wY);
        Game.board[wY][wX] = whitePawn;

        blackPawn = new Pawn(false, bX, bY);
        Game.board[bY][bX] = blackPawn;
    }

    @Test
    public void getLegalMovesForAWhitePawn() {
        // Given
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, wY);

        // Then
        markLegalMoves(true, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{2, 3}, moves.get(0));
    }

    @Test
    public void getLegalMovesForABlackPawn() {
        // Given
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, bY);

        // Then
        markLegalMoves(false, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{5, 4}, moves.get(0));
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThereIsABlackPieceDiagonally() {
        // Given
        Game.board[3][1] = new Queen(false, 1, 3);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, wY);

        // Then
        markLegalMoves(true, moves);
        assertEquals(2, moves.size());
        assertArrayEquals(new int[]{1, 3}, moves.get(0));
        assertArrayEquals(new int[]{2, 3}, moves.get(1));
    }

    @Test
    public void getLegalMovesForABlackPawnIfThereIsAWhitePieceDiagonally() {
        // Given
        Game.board[4][6] = new Queen(true, 6, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, bY);

        // Then
        markLegalMoves(false, moves);
        assertEquals(2, moves.size());
        assertArrayEquals(new int[]{5, 4}, moves.get(0));
        assertArrayEquals(new int[]{6, 4}, moves.get(1));
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThereIsAWhitePieceDiagonally() {
        // Given
        Game.board[3][1] = new Queen(true, 1, 3);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, wY);

        // Then
        markLegalMoves(true, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{2, 3}, moves.get(0));
    }

    @Test
    public void getLegalMovesForABlackPawnIfThereIsABlackPieceDiagonally() {
        // Given
        Game.board[4][6] = new Queen(false, 6, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, bY);

        // Then
        markLegalMoves(false, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{5, 4}, moves.get(0));
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThereIsAFigureNextToIt() {
        // Given
        Game.board[4][1] = new Queen(false, 1, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, wY);

        // Then
        markLegalMoves(true, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{2, 3}, moves.get(0));
    }

    @Test
    public void getLegalMovesForABlackPawnIfThereIsAFigureNextToIt() {
        // Given
        Game.board[3][6] = new Queen(true, 6, 3);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, bY);

        // Then
        markLegalMoves(false, moves);
        assertEquals(1, moves.size());
        assertArrayEquals(new int[]{5, 4}, moves.get(0));
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThereIsAFigureInFrontOfIt() {
        // Given
        Game.board[3][2] = new Queen(false, 2, 3);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, wY);

        // Then
        markLegalMoves(true, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void getLegalMovesForABlackPawnIfThereIsAFigureInFrontOfIt() {
        // Given
        Game.board[4][5] = new Queen(true, 5, 4);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, bY);

        // Then
        markLegalMoves(false, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThePieceHasReachedTheEndOfTheBoard() {
        // Given
        forcePieceMove(whitePawn, wX, 0);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, 0);

        // Then
        markLegalMoves(true, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void getLegalMovesForABlackPawnIfThePieceHasReachedTheEndOfTheBoard() {
        // Given
        forcePieceMove(blackPawn, bX, 7);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, 7);

        // Then
        markLegalMoves(false, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void getLegalMovesForAWhitePawnIfThePieceIsAtTheStartingPoint() {
        // Given
        forcePieceMove(whitePawn, wX, 6);
        printInitialState();

        // When
        final ArrayList<int[]> moves = whitePawn.getLegalMoves(wX, 6);

        // Then
        markLegalMoves(true, moves);
        assertEquals(2, moves.size());
        assertArrayEquals(new int[]{2, 5}, moves.get(0));
        assertArrayEquals(new int[]{2, 4}, moves.get(1));
    }

    @Test
    public void getLegalMovesForABlackPawnIfThePieceIsAtTheStartingPoint() {
        // Given
        forcePieceMove(blackPawn, bX, 1);
        printInitialState();

        // When
        final ArrayList<int[]> moves = blackPawn.getLegalMoves(bX, 1);

        // Then
        markLegalMoves(false, moves);
        assertEquals(2, moves.size());
        assertArrayEquals(new int[]{5, 2}, moves.get(0));
        assertArrayEquals(new int[]{5, 3}, moves.get(1));
    }

    @Test
    public void theWhitePawnBecomesAQueenWhenThePlayerIntendsToReachTheEndOfTheBoard() {
        // Given
        forcePieceMove(whitePawn, wX, 1);
        Game.legalPiece = Game.PieceEnum.PAWN;
        mockJComboBox("Queen");

        printInitialState();

        // When
        whitePawn.makeMove(whitePawn.x, whitePawn.y, wX, 0);

        // Then
        assertNotNull(Game.board[1][wX]);
        assertEquals(Game.board[1][wX].pieceType, Game.PieceEnum.QUEEN);
        assertTrue(Game.board[1][wX].isWhite);
    }

    @Test
    public void theBlackPawnBecomesAKnightWhenThePlayerIntendsToReachTheEndOfTheBoard() {
        // Given
        forcePieceMove(blackPawn, bX, 6);
        Game.legalPiece = Game.PieceEnum.PAWN;
        mockJComboBox("Knight");

        printInitialState();

        // When
        blackPawn.makeMove(blackPawn.x, blackPawn.y, bX, 7);

        // Then
        assertNotNull(Game.board[6][bX]);
        assertEquals(Game.board[6][bX].pieceType, Game.PieceEnum.KNIGHT);
        assertFalse(Game.board[6][bX].isWhite);
    }

    private void mockJComboBox(String returnValue) {
        JComboBox<String> mockGui = mock(JComboBox.class);
        GuiGame.whiteComboBox = mockGui;
        GuiGame.blackComboBox = mockGui;
        when(mockGui.getSelectedItem())
            .thenReturn(returnValue);
    }
}
