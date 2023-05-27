package com.oop.chess.model.integrations.obj;

/**
 * This class contains the required from and to fields for the move advised by ChatGPT.
 */
public class ChatGPTAdvice {
    private final int[] fromTile;
    private final int[] toTile;

    public ChatGPTAdvice() {
        this.fromTile = new int[0];
        this.toTile = new int[0];
    }

    public ChatGPTAdvice(int[] fromTile, int[] toTile) {
        this.fromTile = fromTile;
        this.toTile = toTile;
    }

    public int[] getFromTile() {
        return fromTile;
    }

    public int[] getToTile() {
        return toTile;
    }

    public int getToTileX() {
        return toTile[0];
    }

    public int getToTileY() {
        return toTile[1];
    }

    public int getFromTileX() {
        return fromTile[0];
    }

    public int getFromTileY() {
        return fromTile[1];
    }

    public boolean isEmpty() {
        return fromTile.length == 0 && toTile.length == 0;
    }
}
