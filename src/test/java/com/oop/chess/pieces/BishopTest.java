package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
