package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.King;
import org.junit.jupiter.api.BeforeEach;

public class KingTest extends BaseJUnitTest {

    private King king;
    private final int x = 2;
    private final int y = 4;

    @BeforeEach
    private void setUp() {
        king = new King(true, x, y);
        Game.board[y][x] = king;
    }
}
