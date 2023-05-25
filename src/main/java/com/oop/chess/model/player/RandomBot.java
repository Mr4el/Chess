package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.model.pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a random bot that makes random moves.
 */
public class RandomBot {

    JPanel board;
    static int oldTileX, oldTileY, newTileX, newTileY;
    static ArrayList<int[]> legalMoves;

    RandomAI ai;


    /**
     * Creates a new random bot.
     *
     * @param ai The random AI player.
     */
    public RandomBot(RandomAI ai) {
        board = GuiGame.visualBoard;
        this.ai = ai;
    }


    /**
     * Makes a random move of the legal moves of the passed on piece type.
     *
     * @param type The type of the piece which has to perform a random move.
     */
    public static void randomPlay(Game.PieceEnum type) {
        ArrayList<Piece> possiblePieces = new ArrayList<>();
        ArrayList<int[]> pieceLocations = new ArrayList<>();
        Piece foundPiece;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (Game.getPiece(column, row) != null) {
                    foundPiece = Game.getPiece(column, row);
                    if (foundPiece.getType() == type && (foundPiece.getLegalMoves(column, row).size() >= 1) && (foundPiece.isWhite() == Game.currentPlayer.isWhite())) {
                        possiblePieces.add(foundPiece);
                        pieceLocations.add(new int[]{column, row});
                    }
                }
            }
        }
        Random random = new Random();
        int randy = random.nextInt(possiblePieces.size());
        foundPiece = possiblePieces.get(randy);

        oldTileX = pieceLocations.get(randy)[0];
        oldTileY = pieceLocations.get(randy)[1];

        legalMoves = foundPiece.getLegalMoves(oldTileX, oldTileY);
        int rando = random.nextInt(legalMoves.size());

        newTileX = legalMoves.get(rando)[0];
        newTileY = legalMoves.get(rando)[1];

        Game.movePieceTo(oldTileX, oldTileY, newTileX, newTileY);
    }
}
