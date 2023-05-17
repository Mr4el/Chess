package com.oop.chess.gui;

import javax.swing.*;
import java.awt.event.MouseListener;
import com.oop.chess.Game.PieceEnum;

public class Pieces {

    public static VisualPiece[] blackPieces = new VisualPiece[16];
    public static VisualPiece[] whitePieces = new VisualPiece[16];
    private static int[] blackPieceTiles = { 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15};
    private static int[] whitePieceTiles = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};
    public boolean pieceSelected = false;


    public Pieces(){

    }

    public Pieces(JPanel board) {
        addPieces(board);
    }

    public void addPieces(JPanel board) {
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
            blackPieces[i] = new VisualPiece(new ImageIcon("src/main/resources/blackPawn.png"), false, PieceEnum.PAWN);
        }
        for (int i = 0; i < 8; i++) {
            whitePieces[i] = new VisualPiece(new ImageIcon("src/main/resources/whitePawn.png"), true, PieceEnum.PAWN);
        }

        blackPieces[2] = new VisualPiece(new ImageIcon("src/main/resources/blackBishop.png"), false, PieceEnum.BISHOP);
        blackPieces[5] = new VisualPiece(new ImageIcon("src/main/resources/blackBishop.png"), false, PieceEnum.BISHOP);

        blackPieces[0] = new VisualPiece(new ImageIcon("src/main/resources/blackRook.png"), false, PieceEnum.ROOK);
        blackPieces[7] = new VisualPiece(new ImageIcon("src/main/resources/blackRook.png"), false, PieceEnum.ROOK);

        blackPieces[4] = new VisualPiece(new ImageIcon("src/main/resources/blackKing.png"), false, PieceEnum.KING);
        blackPieces[3] = new VisualPiece(new ImageIcon("src/main/resources/blackQueen.png"), false, PieceEnum.QUEEN);

        blackPieces[1] = new VisualPiece(new ImageIcon("src/main/resources/blackKnight.png"), false, PieceEnum.KNIGHT);
        blackPieces[6] = new VisualPiece(new ImageIcon("src/main/resources/blackKnight.png"), false, PieceEnum.KNIGHT);

        whitePieces[10] = new VisualPiece(new ImageIcon("src/main/resources/whiteBishop.png"), true, PieceEnum.BISHOP);
        whitePieces[13] = new VisualPiece(new ImageIcon("src/main/resources/whiteBishop.png"), true, PieceEnum.BISHOP);

        whitePieces[8] = new VisualPiece(new ImageIcon("src/main/resources/whiteRook.png"), true, PieceEnum.ROOK);
        whitePieces[15] = new VisualPiece(new ImageIcon("src/main/resources/whiteRook.png"), true, PieceEnum.ROOK);

        whitePieces[12] = new VisualPiece(new ImageIcon("src/main/resources/whiteKing.png"), true, PieceEnum.KING);
        whitePieces[11] = new VisualPiece(new ImageIcon("src/main/resources/whiteQueen.png"), true,  PieceEnum.QUEEN);

        whitePieces[9] = new VisualPiece(new ImageIcon("src/main/resources/whiteKnight.png"), true, PieceEnum.KNIGHT);
        whitePieces[14] = new VisualPiece(new ImageIcon("src/main/resources/whiteKnight.png"), true, PieceEnum.KNIGHT);
    }
}
