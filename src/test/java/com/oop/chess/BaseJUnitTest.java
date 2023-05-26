package com.oop.chess;

import com.oop.chess.model.pieces.Piece;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseJUnitTest {

    private static int testCounter = 1;

    @BeforeEach
    public void setUpBase() {
        Game.board = new Piece[8][8];

    }

    @AfterEach
    private void printResult() {
        System.out.println();
        System.out.println("Result:");
        System.out.println();
        System.out.print(Game.getBoardStringRepresentation());
        System.out.println();
        testCounter++;
    }

    public void printInitialState() {
        System.out.println("Test number " + testCounter + " " + StringUtils.repeat(".", 76));
        System.out.println("Initial state:");
        System.out.println();
        System.out.print(Game.getBoardStringRepresentation());
    }
}
