package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;

public class RookTest extends BaseJUnitTest {

    private Rook subject;

    @BeforeEach
    private void setUp() {
        subject = new Rook(true, 3, 3);
        Game.board[3][3] = subject;
    }
}
