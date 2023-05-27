package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;

public class KnightTest extends BaseJUnitTest {

    private Knight knight;
    private final int x = 2;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        knight = new Knight(true, x, y);
        Game.board[y][x] = knight;
    }
}
