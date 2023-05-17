package com.oop.chess.gui;

import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.pieces.Piece;

import javax.swing.*;

public class GuiGame extends GUI {
    public static JPanel visualBoard;
    public static JFrame frame;

    public GuiGame(Piece[][] board) {
        setTitle("Dice Chess!");

        setSize(550,550);

        visualBoard = VisualBoard.createBoard(this);
        new Pieces(visualBoard);
        add(visualBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false);
        setVisible(true);

        frame = this;
    }

    public static void removeVisualPiece(int x, int y) {
        int i = convertXYtoIndex(x, y);
        JPanel tile = (JPanel)visualBoard.getComponent(i);
        tile.removeAll();

        System.out.println();
        tile.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public static void addVisualPiece(int x, int y, PieceEnum type, boolean white) {
        int i = convertXYtoIndex(x, y);

        JPanel tile = (JPanel)visualBoard.getComponent(i);

        String colour_string = (white ? "white" : "black");

        System.out.println("A" + type + "B" + " C" + PieceEnum.KNIGHT);

        String type_string = "";
        switch(type){
            case ANY:
                type_string = "Pawn";
                break;
            case PAWN:
                type_string = "Pawn";
                break;
            case KNIGHT:
                type_string = "Knight";
                break;
            case ROOK:
                type_string = "Rook";
                break;
            case BISHOP:
                type_string = "Bishop";
                break;
            case QUEEN:
                type_string = "Queen";
                break;
            case KING:
                type_string = "King";
                break;
        }

        String image_file = "src/main/resources/" + colour_string + "" + type_string + ".png";

        System.out.println(type + " " + image_file);

        VisualPiece visual_piece;
        visual_piece = new VisualPiece(new ImageIcon(image_file), white, type);

        tile.add(visual_piece);

        tile.revalidate();
        frame.revalidate();
        tile.repaint();
        visualBoard.repaint();
    }

    // convert x y coordinates into index of visualBoard components
    public static int convertXYtoIndex(int x, int y) {
        return x + 8*y;
    }
}
