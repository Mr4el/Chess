package com.oop.chess.pieces;

import com.oop.chess.BaseJUnitTest;
import com.oop.chess.Game;
import com.oop.chess.model.pieces.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BishopTest extends BaseJUnitTest {

    private Bishop subject;

    @BeforeEach
    private void setUp() {
        subject = new Bishop(true, 3, 3);
        Game.board[3][3] = subject;
    }

    @Test
    public void check() {
        printInitialState();
        System.out.println(subject.getLegalMoves(subject.x, subject.y));
    }
}
