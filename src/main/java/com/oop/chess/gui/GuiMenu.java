package com.oop.chess.gui;

import com.oop.chess.ChessMain;
import com.oop.chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * This class represents the menu the user is presented when running the program.
 */
public class GuiMenu extends JFrame {

    static JFrame frame;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JLabel gameTitle;
    private JLabel gameTitle1;
    private Font markerFelt;

    private JButton backButton;
    private static JCheckBox showPossibleMovesHuman;
    private static JCheckBox showPossibleMovesAI;
    public static boolean playingAI;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    /**
     * Constructs a new menu.
     */
    public GuiMenu() {
        frame = this;

        this.createComponents();
        super.setSize(WIDTH, HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Creates the different components of the menu.
     */
    private void createComponents() {
        this.mainPanel = new JPanel(new CardLayout());
        JPanel menuPanel = this.createMenuPanel();
        JPanel start1Panel = this.createStart1Panel();
        JPanel start2Panel = this.createStart2Panel();
        this.mainPanel.add(menuPanel, "Main Menu");
        this.mainPanel.add(start1Panel, "Player vs Computer");
        this.mainPanel.add(start2Panel, "Pass & Play");
        this.cardLayout = (CardLayout) this.mainPanel.getLayout();
        this.cardLayout.show(this.mainPanel, "Main Menu");
        super.add(this.mainPanel);
    }

    /**
     * Creates a menu panel.
     *
     * @return The menu panel.
     */
    private JPanel createMenuPanel() {
        setTitle("Main Menu");
        JPanel panel = new JPanel(null);
        java.net.URL resource = GuiGame.class.getResource("/menu_bg.jpg");
        JLabel background = new JLabel(new ImageIcon(resource));
        background.setBounds(0, 0, 800, 600);
        panel.add(background);
        validate();
        background.setLayout(null);
        resource = GuiGame.class.getResource("/logo.png");
        this.gameTitle = new JLabel(new ImageIcon(resource));
        gameTitle.setBounds(120, 5, 150, 200);

        try {
            resource = GuiGame.class.getResource("/font/MarkerFelt.ttc");
            markerFelt = Font.createFont(Font.TRUETYPE_FONT, resource.openStream()).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(markerFelt);
        } catch (IOException | FontFormatException e) {
            System.out.println("Cannot load the font");
        }

        this.gameTitle1 = new JLabel("DICE       CHESS");
        gameTitle1.setBounds(45, 50, 400, 100);
        gameTitle1.setForeground(Color.WHITE);
        gameTitle1.setFont(markerFelt);

        JButton playButton = new JButton("Human vs AI");
        playButton.setFont(new Font("Serif", Font.PLAIN, 50));
        playButton.setForeground(Color.WHITE);
        playButton.setBounds(45, 220, 275, 50);
        initButton(playButton);
        playButton.addActionListener(e -> cardLayout.show(mainPanel, "Player vs Computer"));

        JButton multiplayerButton = new JButton("Human vs Human");
        multiplayerButton.setForeground(Color.WHITE);
        multiplayerButton.setFont(new Font("Serif", Font.PLAIN, 50));
        multiplayerButton.setBounds(45, 300, 370, 50);
        initButton(multiplayerButton);
        multiplayerButton.addActionListener(e -> cardLayout.show(mainPanel, "Pass & Play"));

        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(new Font("Serif", Font.PLAIN, 50));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBounds(45, 380, 135, 50);
        initButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));

        background.add(gameTitle);
        background.add(gameTitle1);
        background.add(playButton);
        background.add(multiplayerButton);
        background.add(quitButton);
        return panel;
    }

    /**
     * Adds a mouse listener to the passed on button.
     *
     * @param button The button to which a mouse listener will be added.
     */
    private void initButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {

            /**
             * Handles what happens whenever the mouse enters the button.
             *
             * @param e The mouse event of the mouse entering the button.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.LIGHT_GRAY);
            }

            /**
             * Handles what happens whenever the mouse exits the button.
             *
             * @param e The mouse event of the mouse exiting the button.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Creates the first start panel.
     *
     * @return The first start panel.
     */
    private JPanel createStart1Panel() {
        JPanel panel = new JPanel(null);
        java.net.URL resource = GuiGame.class.getResource("/menu_bg.jpg");
        JLabel background = new JLabel(new ImageIcon(resource));
        background.setBounds(0, 0, 800, 600);
        panel.add(background);
        validate();
        background.setLayout(null);
        resource = GuiGame.class.getResource("/logo.png");
        JLabel gameTitle = new JLabel(new ImageIcon(resource));
        gameTitle.setBounds(120, 5, 150, 200);

        try {
            resource = GuiGame.class.getResource("/font/MarkerFelt.ttc");
            markerFelt = Font.createFont(Font.TRUETYPE_FONT, resource.openStream()).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(markerFelt);
        } catch (IOException | FontFormatException e) {
            System.out.println("Cannot load the font");
        }

        this.gameTitle1 = new JLabel("DICE       CHESS");
        gameTitle1.setBounds(45, 50, 400, 100);
        gameTitle1.setForeground(Color.WHITE);
        gameTitle1.setFont(markerFelt);

        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("Serif", Font.PLAIN, 50));
        playButton.setForeground(Color.WHITE);
        playButton.setBounds(45, 220, 150, 50);
        initButton(playButton);
        playButton.addActionListener(e -> {
//        Start game here (human vs computer)
            playingAI = true;
            Game.resetTurnState();
            frame.setVisible(false);
            ChessMain.startGame(true, false, showPossibleMovesAI.isSelected());
        });

        this.backButton = backButton();
        showPossibleMovesAI = showPossibleMoves();

        JComboBox<String> aiComboBox = new JComboBox<>();
        aiComboBox.addItem("Random Moves Bot");
        aiComboBox.setFont(new Font("Serif", Font.PLAIN, 12));
        aiComboBox.setForeground(Color.WHITE);
        aiComboBox.setBounds(30, 380, 430, 50);
        aiComboBox.setBorder(BorderFactory.createEmptyBorder());
        aiComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        background.add(gameTitle);
        background.add(gameTitle1);
        background.add(playButton);
        background.add(showPossibleMovesAI);
        background.add(aiComboBox);
        background.add(backButton);
        return panel;
    }

    /**
     * Creates the second start panel.
     *
     * @return The second start panel.
     */
    private JPanel createStart2Panel() {
        JPanel panel = new JPanel(null);
        java.net.URL resource = GuiGame.class.getResource("/menu_bg.jpg");
        JLabel background = new JLabel(new ImageIcon(resource));
        background.setBounds(0, 0, 800, 600);
        panel.add(background);
        validate();
        background.setLayout(null);
        resource = GuiGame.class.getResource("/logo.png");
        JLabel gameTitle = new JLabel(new ImageIcon(resource));
        gameTitle.setBounds(120, 5, 150, 200);

        try {
            resource = GuiGame.class.getResource("/font/MarkerFelt.ttc");
            markerFelt = Font.createFont(Font.TRUETYPE_FONT, resource.openStream()).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(markerFelt);
        } catch (IOException | FontFormatException e) {
            System.out.println("Cannot load the font");
        }

        this.gameTitle1 = new JLabel("DICE       CHESS");
        gameTitle1.setBounds(45, 50, 400, 100);
        gameTitle1.setForeground(Color.WHITE);
        gameTitle1.setFont(markerFelt);

        showPossibleMovesHuman = showPossibleMoves();

        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("Serif", Font.PLAIN, 50));
        playButton.setForeground(Color.WHITE);
        playButton.setBounds(45, 220, 150, 50);
        initButton(playButton);
        playButton.addActionListener(e -> {
//          Start game here (human vs human)
            playingAI = false;
            Game.resetTurnState();
            setVisible(false);
            ChessMain.startGame(true, true, showPossibleMovesHuman.isSelected());
        });

        this.backButton = backButton();

        background.add(gameTitle);
        background.add(gameTitle1);
        background.add(playButton);
        background.add(showPossibleMovesHuman);
        background.add(backButton);
        return panel;
    }

    /**
     * Creates the functionality to show the possible moves.
     *
     * @return The JCheckBox containing the possible moves.
     */
    private JCheckBox showPossibleMoves() {
        JCheckBox showPossibleMoves = new JCheckBox("Show Possible Moves");
        showPossibleMoves.setFont(new Font("Serif", Font.PLAIN, 45));
        showPossibleMoves.setForeground(Color.WHITE);
        showPossibleMoves.setBounds(30, 300, 430, 50);
        showPossibleMoves.setBorder(BorderFactory.createEmptyBorder());
        showPossibleMoves.setContentAreaFilled(false);
        showPossibleMoves.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        showPossibleMoves.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showPossibleMoves.isSelected()) {
                    showPossibleMoves.setForeground(Color.LIGHT_GRAY);
                } else
                    showPossibleMoves.setForeground(Color.WHITE);
            }
        });
        return showPossibleMoves;
    }

    /**
     * Creates the button to go back.
     *
     * @return The button to go back.
     */
    private JButton backButton() {
        java.net.URL resource = GuiMenu.class.getResource("/back_button.png");
        backButton = new JButton(new ImageIcon(resource));
        backButton.setBounds(5, 500, 100, 70);
        backButton.addMouseListener(new MouseAdapter() {

            /**
             * Handles what happens whenever the mouse enters the button.
             *
             * @param e The mouse event of the mouse entering the button.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                java.net.URL resource = GuiMenu.class.getResource("/back_button.png");
                backButton.setIcon(new ImageIcon(resource));
            }

            /**
             * Handles what happens whenever the mouse exits the button.
             *
             * @param e The mouse event of the mouse exiting the button.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                java.net.URL resource = GuiMenu.class.getResource("/back_button.png");
                backButton.setIcon(new ImageIcon(resource));
            }
        });
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setContentAreaFilled(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        return backButton;
    }

    /**
     * @return the selected outcome of the checkbox
     */
    public static boolean getShowPossibleMoves(boolean ai) {
        if (ai) {
            return showPossibleMovesAI.isSelected();
        }
        else
            return showPossibleMovesHuman.isSelected();
    }
}
