package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;

public class QueenTest extends BaseJUnitTest {

    private Queen subject;

    @BeforeEach
    private void setUp() {
        subject = new Queen(true, 3, 3);
        Game.board[3][3] = subject;
    }
}
