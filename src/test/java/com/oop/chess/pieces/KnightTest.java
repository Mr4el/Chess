package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;

public class KnightTest extends BaseJUnitTest {

    private Knight subject;

    @BeforeEach
    private void setUp() {
        subject = new Knight(true, 3, 3);
        Game.board[3][3] = subject;
    }
}
