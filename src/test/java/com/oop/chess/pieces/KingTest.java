package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.King;
import org.junit.jupiter.api.BeforeEach;

public class KingTest extends BaseJUnitTest {

    private King subject;

    @BeforeEach
    private void setUp() {
        subject = new King(true, 3, 3);
        Game.board[3][3] = subject;
    }
}
