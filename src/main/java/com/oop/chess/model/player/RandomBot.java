package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.model.pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomBot {

    /*
     * TODO In main menu, new button AI vs AI
     */

    JPanel board;
    static int oldTile_x, oldTile_y, newTile_x, newTile_y;
    static ArrayList<int[]> legalMoves;

    RandomAI ai;

    public RandomBot(RandomAI ai) {
        board = GuiGame.visualBoard;
        this.ai = ai;
    }

    public static void randomPlay(Game.PieceEnum type) {
        ArrayList<Piece> possiblePieces = new ArrayList<>();
        ArrayList<int[]> pieceLocations = new ArrayList<>();
        Piece foundPiece;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (Game.getPiece(column, row) != null) {
                    foundPiece = Game.getPiece(column, row);
                    if (foundPiece.getType() == type && (foundPiece.getLegalMoves(column, row).size() >= 1) && (foundPiece.isWhite() == Game.current_player.isWhite())) {
                        possiblePieces.add(foundPiece);
                        pieceLocations.add(new int[]{column, row});
                    }
                }
            }
        }
        Random random = new Random();
        int randy = random.nextInt(possiblePieces.size());
        foundPiece = possiblePieces.get(randy);

        oldTile_x = pieceLocations.get(randy)[0];
        oldTile_y = pieceLocations.get(randy)[1];

        legalMoves = foundPiece.getLegalMoves(oldTile_x, oldTile_y);
        int rando = random.nextInt(legalMoves.size());

        newTile_x = legalMoves.get(rando)[0];
        newTile_y = legalMoves.get(rando)[1];
        Game.movePieceTo(oldTile_x, oldTile_y, newTile_x, newTile_y);
    }
}
