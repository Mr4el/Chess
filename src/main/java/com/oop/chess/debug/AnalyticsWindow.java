package com.oop.chess.debug;

import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.model.player.SearchAI;

import javax.swing.*;

/**
 * This class is used to test our algorithms by keeping track of how many games each player has won.
 */
public class AnalyticsWindow extends JFrame {
    AnalyticsPanel panel;

    /**
     * Creates a new Analytics Window which will feature how many games each player has won.
     */
    public AnalyticsWindow() {
        setSize(200, 300);
        setVisible(true);

        panel = new AnalyticsPanel();
        java.net.URL resource = GuiGame.class.getResource("/chess_icon.png");
        ImageIcon iconImg = new ImageIcon(resource);
        this.setIconImage(iconImg.getImage());
        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Updates the Analytics Window.
     *
     * @param w The number of games white won.
     * @param b The number of games black won.
     */
    public void update(int w, int b) {
        panel.black_wins += b;
        panel.white_wins += w;

        double p_w = ((double) panel.white_wins) / (panel.black_wins + panel.white_wins) * 100;
        double p_b = ((double) panel.black_wins) / (panel.black_wins + panel.white_wins) * 100;

        panel.white_wins_label.setText("white wins: " + panel.white_wins + " (" + p_w + "%)");
        panel.black_wins_label.setText("black wins: " + panel.black_wins + " (" + p_b + "%)");
    }
}

/**
 * This class represents the panel that is created to test our algorithms by keeping track of how many games each player has won.
 */
class AnalyticsPanel extends JPanel {
    public int black_wins = 0;
    public int white_wins = 0;

    JLabel black_wins_label;
    JLabel white_wins_label;

    /**
     * Creates a new Analytics Panel featuring the search depth of the AI as well as the number of games won by each player.
     */
    public AnalyticsPanel() {
        add(new JLabel("Search AI depth: " + SearchAI.depth));

        black_wins_label = new JLabel();
        white_wins_label = new JLabel();

        add(new JLabel("White: " + Game.players[0]));
        add(new JLabel("Black: " + Game.players[1]));


        add(black_wins_label);
        add(white_wins_label);
    }
}
