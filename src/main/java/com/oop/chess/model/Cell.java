package com.oop.chess.model;

import com.oop.chess.Game;
import com.oop.chess.model.pieces.Piece;

public class Cell {

    private Game g;
    private final boolean isWhite;
    private final int xCoord;
    private final int yCoord;

    public Cell(Game g, boolean isWhite, int xCoord, int yCoord) {

        this.g = g;
        this.isWhite = isWhite;
        this.xCoord = xCoord;
        this.yCoord = yCoord;


    }

    public boolean getColor() {
        return this.isWhite;
    }

    public int getXCoord() {
        return this.xCoord;
    }

    public int getYCoord() {
        return this.yCoord;
    }

    public Piece remove() {

        return null;
    }

    public void capture(Piece piece) {

    }

    public void putPiece() {

    }

}
