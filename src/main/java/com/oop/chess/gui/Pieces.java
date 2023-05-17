package com.oop.chess.gui;

import javax.swing.*;
import java.awt.event.MouseListener;

public class Pieces {

    public static JLabel[] blackPieces = new JLabel[16];
    public static JLabel[] whitePieces = new JLabel[16];
    private static int[] blackPieceTiles = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static int[] whitePieceTiles = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65};
    public boolean pieceSelected = false;


    public static void addPieces(JPanel board) {
        MouseListener listener = new PieceMovement(board);
        board.addMouseListener(listener);
        loadImages();
        JPanel tile;

        for (int i = 0; i < blackPieces.length; i++) {
            tile = (JPanel) board.getComponent(blackPieceTiles[i]);
            tile.add(blackPieces[i]);
        }
        for (int i = 0; i < whitePieces.length; i++) {
            tile = (JPanel) board.getComponent(whitePieceTiles[i]);
            tile.add(whitePieces[i]);
        }
    }

    public static void loadImages() {
        for (int i = 8; i < 16; i++) {
            blackPieces[i] = new JLabel(new ImageIcon("src/main/resources/blackPawn.png"));
            blackPieces[i].setName("Black Pawn");
        }
        for (int i = 0; i < 8; i++) {
            whitePieces[i] = new JLabel(new ImageIcon("src/main/resources/whitePawn.png"));
            whitePieces[i].setName("White Pawn");
        }
        blackPieces[2] = new JLabel(new ImageIcon("src/main/resources/blackBishop.png"));
        blackPieces[2].setName("Black Bishop");
        blackPieces[5] = new JLabel(new ImageIcon("src/main/resources/blackBishop.png"));
        blackPieces[5].setName("Black Bishop");

        blackPieces[0] = new JLabel(new ImageIcon("src/main/resources/blackRook.png"));
        blackPieces[7] = new JLabel(new ImageIcon("src/main/resources/blackRook.png"));

        blackPieces[4] = new JLabel(new ImageIcon("src/main/resources/blackKing.png"));
        blackPieces[3] = new JLabel(new ImageIcon("src/main/resources/blackQueen.png"));

        blackPieces[1] = new JLabel(new ImageIcon("src/main/resources/blackKnight.png"));
        blackPieces[6] = new JLabel(new ImageIcon("src/main/resources/blackKnight.png"));

        whitePieces[10] = new JLabel(new ImageIcon("src/main/resources/whiteBishop.png"));
        whitePieces[13] = new JLabel(new ImageIcon("src/main/resources/whiteBishop.png"));

        whitePieces[8] = new JLabel(new ImageIcon("src/main/resources/whiteRook.png"));
        whitePieces[15] = new JLabel(new ImageIcon("src/main/resources/whiteRook.png"));

        whitePieces[12] = new JLabel(new ImageIcon("src/main/resources/whiteKing.png"));
        whitePieces[11] = new JLabel(new ImageIcon("src/main/resources/whiteQueen.png"));

        whitePieces[9] = new JLabel(new ImageIcon("src/main/resources/whiteKnight.png"));
        whitePieces[14] = new JLabel(new ImageIcon("src/main/resources/whiteKnight.png"));
    }
}
