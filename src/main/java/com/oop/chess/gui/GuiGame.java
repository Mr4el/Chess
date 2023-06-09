package com.oop.chess.gui;

import com.oop.chess.ChessMain;
import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.model.config.Configuration;
import com.oop.chess.model.player.Human;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents the graphical interface part of the game.
 */
public class GuiGame extends JFrame {
    public static JPanel visualBoard;
    public static JFrame frame;
    public static JLabel legalPiece;
    private static JLabel whoseTurn;
    public static JComboBox<String> blackComboBox;
    public static JComboBox<String> whiteComboBox;


    /**
     * Constructs a new graphical interface with the given chess board.
     *
     */
    public GuiGame() {

        if (ChessMain.debug)
            System.out.println("Initialize GUI");

        frame = this;
        setTitle("OOP - Random Chess!");
        setSize(715, 590);
        setResizable(false);

        visualBoard = VisualBoard.createBoard(this);
        visualBoard.setBounds(0, 0, 550, 550);
        new Pieces();

        JPanel dicePanel = new JPanel();
        dicePanel.setBackground(Color.decode("#ffe6b3"));
        dicePanel.setBounds(550, 0, 150, 570);
        legalPiece = new JLabel();
        legalPiece.setBounds(600, 250, 50, 50);
        whoseTurn = new JLabel("White's Turn");
        whoseTurn.setFont(new Font("Sarif", Font.BOLD, 20));
        whoseTurn.setBounds(560, 150, 150, 100);

        JLabel blackPromotion = new JLabel("Black Promotion");
        blackPromotion.setBounds(570, 80, 105, 20);
        blackPromotion.setBorder(BorderFactory.createEmptyBorder());
        blackComboBox = new JComboBox<>();
        blackComboBox.addItem("Queen");
        blackComboBox.addItem("Knight");
        blackComboBox.addItem("Bishop");
        blackComboBox.addItem("Rook");
        blackComboBox.addItem("Pawn");
        blackComboBox.setSelectedIndex(0);
        blackComboBox.setBounds(565, 100, 120, 25);
        blackComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        blackComboBox.setFocusable(false);

        JLabel whitePromotion = new JLabel("White Promotion");
        whitePromotion.setBounds(570, 360, 105, 20);
        whitePromotion.setBorder(BorderFactory.createEmptyBorder());
        whiteComboBox = new JComboBox<>();
        whiteComboBox.addItem("Queen");
        whiteComboBox.addItem("Knight");
        whiteComboBox.addItem("Bishop");
        whiteComboBox.addItem("Rook");
        whiteComboBox.addItem("Pawn");
        whiteComboBox.setSelectedIndex(0);
        whiteComboBox.setBounds(565, 380, 120, 25);
        whiteComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        whiteComboBox.setFocusable(false);

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Serif", Font.BOLD, 20));
        homeButton.setBorder(BorderFactory.createEmptyBorder());
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusable(false);
        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeButton.setBounds(625, 475, 75, 75);
        homeButton.addActionListener(e -> {
            frame.dispose();
            GuiMenu.frame.setVisible(true);
        });

        JButton restartGame = new JButton("Replay");
        restartGame.setFont(new Font("Serif", Font.BOLD, 20));
        restartGame.setBorder(BorderFactory.createEmptyBorder());
        restartGame.setContentAreaFilled(false);
        restartGame.setFocusable(false);
        restartGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        restartGame.setBounds(550, 475, 75, 75);
        restartGame.addActionListener(e -> Game.restart());

        if (!GuiMenu.AIGame && (!GuiMenu.playingAI || GuiMenu.playAsBlack.isSelected())) {
            frame.add(blackPromotion);
            frame.add(blackComboBox);
        }
        if (!GuiMenu.AIGame && !(GuiMenu.playingAI && GuiMenu.playAsBlack.isSelected())) {
            frame.add(whitePromotion);
            frame.add(whiteComboBox);
        }
        frame.add(homeButton);
        frame.add(restartGame);
        frame.add(whoseTurn);
        frame.add(legalPiece);
        frame.add(visualBoard);
        frame.add(dicePanel);

        java.net.URL resource = GuiGame.class.getResource("/chess_icon.png");
        ImageIcon iconImg = new ImageIcon(resource);
        frame.setIconImage(iconImg.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    /**
     * Removes a piece from the presented board visually.
     *
     * @param x The X-coordinate of the piece to be removed visually.
     * @param y The Y-coordinate of the piece to be removed visually.
     */
    public static void removeVisualPiece(int x, int y) {
        int i = convertXYtoIndex(x, y);
        JPanel tile = (JPanel) visualBoard.getComponent(i);
        tile.removeAll();

        tile.repaint();
        frame.revalidate();
        frame.repaint();
    }


    /**
     * Adds a piece to the presented board visually.
     *
     * @param x     The X-coordinate at which a piece will be added visually.
     * @param y     The Y-coordinate at which a piece will be added visually.
     * @param type  The type of the piece to be added visually.
     * @param white Whether the piece is white or black.
     */
    public static void addVisualPiece(int x, int y, PieceEnum type, boolean white) {
        int i = convertXYtoIndex(x, y);

        JPanel tile = (JPanel) visualBoard.getComponent(i);

        String colour_string = (white ? "white" : "black");

        String type_string = switch (type) {
            case ANY, PAWN -> "Pawn";
            case KNIGHT -> "Knight";
            case ROOK -> "Rook";
            case BISHOP -> "Bishop";
            case QUEEN -> "Queen";
            case KING -> "King";
        };

        URL imageFile = GuiGame.class.getResource("/" + colour_string + type_string + ".png");

        VisualPiece visual_piece;
        visual_piece = new VisualPiece(new ImageIcon(imageFile), white, type);

        tile.add(visual_piece);

        tile.revalidate();
        frame.revalidate();
        tile.repaint();
        visualBoard.repaint();
    }


    /**
     * Convert x and y coordinates into an index of the visual board.
     *
     * @param x The X-coordinate to be converted.
     * @param y The Y-coordinate to be converted.
     * @return The converted index.
     */
    public static int convertXYtoIndex(int x, int y) {
        return x + 8 * y;
    }


    /**
     * Sets the piece which is allowed to move in the visual board to the passed legal_piece
     *
     * @param type                 The type the visual piece will be set to.
     * @param current_player_index The index of the current player.
     */
    public void setLegalPiece(PieceEnum type, int current_player_index) {

        String colour_string = (current_player_index == 1 ? "black" : "white");

        String type_string = switch (type) {
            case ANY, PAWN -> "Pawn";
            case KNIGHT -> "Knight";
            case ROOK -> "Rook";
            case BISHOP -> "Bishop";
            case QUEEN -> "Queen";
            case KING -> "King";
        };

        java.net.URL resource = GuiGame.class.getResource("/" + colour_string + type_string + ".png");
        legalPiece.setIcon(new ImageIcon(resource));
    }


    /**
     * Sets whose turn it currently is.
     *
     * @param turn An integer indicating whose turn it is, where 1 denotes black and 0 denotes white.
     */
    public static void setWhoseTurn(int turn) {
        if (turn == 1) {
            whoseTurn.setText("Black's Turn");

            // Thinking text if black player is AI
            if (!(Game.currentPlayer instanceof Human))
                whoseTurn.setText("<html>Black's Turn<br>(Thinking...)</html>");

            // Asking text if black player is Human
            if (Game.currentPlayer instanceof Human && Configuration.enableChatGptIntegration)
                whoseTurn.setText("<html>Black's Turn<br>(Asking ChatGpt...)</html>");
        } else {
            whoseTurn.setText("White's Turn");

            // Asking text if white player is Human
            if (Game.currentPlayer instanceof Human && Configuration.enableChatGptIntegration)
                whoseTurn.setText("<html>White's Turn<br>(Asking ChatGpt...)</html>");
        }
    }

    /**
     * Updates whose turn is currently on ChatGPT's response.
     */
    public static void updateWhoseTurnOnChatGptResponse(boolean isAdviceEmpty) {
        if (Game.currentPlayerIndex == 1) {
            whoseTurn.setText("Black's Turn");

            // Thinking text if black player is AI
            if (!(Game.currentPlayer instanceof Human))
                whoseTurn.setText("<html>Black's Turn<br>(Thinking...)</html>");

            // Error text if black player is Human and ChatGPT answered in the wrong way
            if (Game.currentPlayer instanceof Human && isAdviceEmpty)
                whoseTurn.setText("<html>Black's Turn<br>(I have no advice)</html>");
        } else {
            whoseTurn.setText("White's Turn");

            // Error text if white player is Human and ChatGPT answered in the wrong way
            if (Game.currentPlayer instanceof Human && isAdviceEmpty)
                whoseTurn.setText("<html>White's Turn<br>(I have no advice)</html>");
        }
    }
}
