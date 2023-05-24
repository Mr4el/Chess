package com.oop.chess.model;

import com.oop.chess.Game;

/**
 * This class represents a single tile of the chess board.
 */
public class Cell {

    private Game g;
    private final boolean isWhite;
    private final int xCoord;
    private final int yCoord;

    /**
     * Constructs a new cell.
     *
     * @param g The game the new cell belongs to.
     * @param isWhite The boolean whether the piece in the tile is white or black.
     * @param xCoord The X-coordinate of the cell.
     * @param yCoord The Y-coordinate of the cell.
     */
    public Cell(Game g, boolean isWhite, int xCoord, int yCoord) {
        this.g = g;
        this.isWhite = isWhite;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Gets the color of the piece in the cell.
     *
     * @return A boolean indicating whether the piece in the cell is white or black.
     */
    public boolean getColor() {
        return this.isWhite;
    }

    /**
     * Gets the X-coordinate of the cell.
     *
     * @return The X-coordinate of the cell.
     */
    public int getXCoord() {
        return this.xCoord;
    }

    /**
     * Gets the Y-coordinate of the cell.
     *
     * @return The Y-coordinate of the cell.
     */
    public int getYCoord() {
        return this.yCoord;
    }
}
