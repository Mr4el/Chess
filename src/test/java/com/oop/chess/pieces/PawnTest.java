package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;

public class PawnTest extends BaseJUnitTest {

    private Pawn subject;

    @BeforeEach
    private void setUp() {
        subject = new Pawn(true, 3, 3);
        Game.board[3][3] = subject;
    }
}
