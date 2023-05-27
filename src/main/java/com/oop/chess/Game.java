package com.oop.chess;

import com.oop.chess.debug.AnalyticsWindow;
import com.oop.chess.debug.GameLogger;
import com.oop.chess.model.machine.learning.TemporalDifferenceLeaf;
import com.oop.chess.model.pieces.*;
import com.oop.chess.model.player.*;
import com.oop.chess.gui.*;
import com.oop.chess.model.search.FEN;
import com.oop.chess.model.search.GameSearchTree;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * This class represents a single game.
 */
public class Game {
    public static Piece[][] board;


    // store player 1 and 2 in array, players[current_player_index] accesses the current player (with current_player_index being either 0 or 1.)
    // current_player is a static reference to the current player, mainly useful for GUI stuff
    public static Player[] players = {null, null};
    public static Player currentPlayer;
    public static Player otherPlayer;
    public static int currentPlayerIndex;

    // How many moves since the last piece capture/pawn advance?
    static int halfMoves;

    // How many completed moves/turns total? (Increments after black player)
    static int fullMoves;

    static int completedMoves = 0;

    static boolean kingCaptured = false;

    static int gamesToBePlayed;

    static int sleepTime;

    static boolean gameStart = true;

    public static final int LOOP_THRESHOLD = 140;

    // The game's GUI;
    public static GuiGame gui;

    public static String lastFen;


    // the different values which the turn state can have.
    public enum TURN_STATES {
        START,              // basic initialization
        DICE_ROLL,          // rolling dice & determining legal pieces
        CHOOSING_MOVE,      // player choosing a move
        SWITCH_TURN,         // switching turns (so player 2 plays after player 1)
        GAME_END
    }


    // what is the current state of the turn?
    static TURN_STATES turnState;


    /**
     * Enum for the different piece types (used as identifiers)
     */
    public enum PieceEnum {
        ANY,    // mainly used for debugging
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
    }


    // the piece type chosen by the dice
    public static PieceEnum legalPiece;

    /**
     * Creates a new game of chess.
     *
     * @param player1 The first player of the game.
     * @param player2 The second player of the game, which allows for switching between a human and AI player.
     */
    public static void initializeGame(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;

        fullMoves = 0;
        halfMoves = 0;

        currentPlayerIndex = 0;
        currentPlayer = players[currentPlayerIndex];
        otherPlayer = players[1];

        System.out.println(currentPlayer.getClass());
        System.out.println(otherPlayer.getClass());

        initializeBoard();
        gui = new GuiGame();
    }


    /**
     * Restarts the game.
     */
    public static void restart() {
        GuiGame.frame.dispose();

        ChessMain.sessionGamesPlayed++;
        resetTurnState();
        if (GuiMenu.playingAI) {
            ChessMain.startGame(GuiMenu.getShowPossibleMoves(true));
        } else if (GuiMenu.AIGame) {
            ChessMain.startGame(false);
        } else {
            ChessMain.startGame(GuiMenu.getShowPossibleMoves(false));
        }
    }


    /**
     * Sets the number of games to be played to the passed on value.
     *
     * @param playGames The value to which the games to be played will be set.
     */
    public static void setGamesToBePlayed(int playGames) {
        gamesToBePlayed = playGames;
    }


    /**
     * Sets the amount of time the game pauses between moves in an AI vs AI match.
     *
     * @param timeToSleep The amount of time to pause in milliseconds.
     */
    public static void setSleepTime(int timeToSleep) {
        sleepTime = timeToSleep;
    }


    /**
     * This is the main loop of the program where the state of the program is checked.
     */
    public static void run() {
        switch (turnState) {
            case START:
                gui.setTitle((currentPlayerIndex == 0 ? "OOP - Random Chess! - White's Turn" : "OOP - Random Chess! - Black's Turn"));

                GuiGame.setWhoseTurn(currentPlayerIndex);

                runNextState(TURN_STATES.DICE_ROLL);

                if (ChessMain.debug)
                    System.out.println("GAME STATE - Start turn for " + currentPlayer);
                gui.repaint();
                break;

            // roll the dice to determine which pieces the player is allowed to move
            case DICE_ROLL:
                legalPiece = Dice.roll(currentPlayer.isWhite());

                if (legalPiece != null) {
                    gui.setLegalPiece(legalPiece, currentPlayerIndex);
                    gui.setTitle(gui.getTitle() + " - Legal piece: " + legalPiece);

                    runNextState(TURN_STATES.CHOOSING_MOVE);

                    break;
                }

                // the player is currently making a move
            case CHOOSING_MOVE:
                if (ChessMain.debug)
                    System.out.println("GAME STATE - " + currentPlayer + " can make a move.");

                VisualBoard.discardRecoloredCells();
                currentPlayer.turn(legalPiece);
                break;


            // next player's turn
            case SWITCH_TURN:
                completedMoves++;

                if (completedMoves > LOOP_THRESHOLD) {
                    restart();
                    System.out.println("The agents have entered a loop and the game ends in a draw");
                }

                if (kingCaptured) {
                    runNextState(TURN_STATES.GAME_END);
                    break;
                }

                if (!gameStart) {
                    try {
                        System.out.println("Sleeping");
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else
                    gameStart = false;

                currentPlayerIndex++;
                if (currentPlayerIndex > 1) {
                    fullMoves++;
                    currentPlayerIndex = 0;
                }

                otherPlayer = currentPlayer;
                currentPlayer = players[currentPlayerIndex];

                runNextState(TURN_STATES.START);

                break;

            // King captured (Game end)
            case GAME_END:
                String title;
                if (currentPlayer.isWhite()) {
                    title = "White captured Black's King!";
                    currentPlayerIndex = 0;
                } else {
                    title = "Black captured White's King!";
                    currentPlayerIndex = 1;
                }
                gui.setTitle(title);

                if (currentPlayer instanceof SearchAI && ((SearchAI) currentPlayer).ML_component)
                    GameSearchTree.bestLeafNodes.add(1.0);

                else if (otherPlayer instanceof SearchAI && ((SearchAI) otherPlayer).ML_component)
                    GameSearchTree.bestLeafNodes.add(-1.0);


                if (((currentPlayer instanceof SearchAI) && (((SearchAI) currentPlayer).ML_component)) || ((otherPlayer instanceof SearchAI) && (((SearchAI) otherPlayer).ML_component))) {
                    TemporalDifferenceLeaf.updateWeights(GameSearchTree.bestLeafNodes, false, SearchAI.weights);
                }

                lastFen = FEN.encode(board, currentPlayerIndex, halfMoves, fullMoves);

                if (GuiMenu.AIGame) {
                    GameLogger.logWin(currentPlayer.isWhite());
                }
                if (GuiMenu.AIGame && gamesToBePlayed > 1) {
                    gamesToBePlayed--;
                    restart();
                    if (gamesToBePlayed > 0) break;
                }

                //Create Game over frame
                JFrame frame = new JFrame("Game Over!");
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setSize(600, 300);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);

                java.net.URL resourceIcon = GuiGame.class.getResource("/chess_icon.png");
                ImageIcon iconImg = new ImageIcon(resourceIcon);
                frame.setIconImage(iconImg.getImage());

                // Create a panel to hold the buttons
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(Color.black);

                java.net.URL resource;
                Font markerFelt = null;
                Font markerFelt2 = null;

                try {
                    resource = GuiGame.class.getResource("/font/MarkerFelt.ttc");
                    markerFelt = Font.createFont(Font.TRUETYPE_FONT, resource.openStream()).deriveFont(60f);
                    markerFelt2 = Font.createFont(Font.TRUETYPE_FONT, resource.openStream()).deriveFont(30f);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(markerFelt);
                } catch (IOException | FontFormatException e) {
                    System.out.println("Cannot load the font");
                }

                JLabel endTitle = new JLabel("GAME IS OVER!");
                endTitle.setBounds(100, 50, 400, 100);
                endTitle.setForeground(Color.GREEN);
                if (markerFelt != null)
                    endTitle.setFont(markerFelt);

                JLabel brLabel = new JLabel("<html><br></html>");

                // Game result
                JLabel endRes = new JLabel(title);
                endRes.setBounds(100, 150, 400, 100);
                endRes.setForeground(Color.WHITE);
                if (markerFelt2 != null)
                    endRes.setFont(markerFelt2);

                // Create a separate panel for buttons with FlowLayout
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(Color.BLACK);
                JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                emptyPanel.setBackground(Color.BLACK);

                // Create the buttons
                JButton homeButton = new JButton("Home ");
                homeButton.setFont(new Font("Serif", Font.BOLD, 30));
                homeButton.setBorder(BorderFactory.createEmptyBorder());
                homeButton.setBackground(Color.white);
                homeButton.setForeground(Color.decode("#ffe6b3")); // Set button text color to white
                homeButton.setContentAreaFilled(false);
                homeButton.setFocusable(false);
                homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                homeButton.setBounds(625, 475, 75, 75);
                homeButton.addActionListener(e -> {
                    frame.dispose();
                    gui.dispose();
                    GameLogger.disposeAnalytics();
                    new GuiMenu();
                });

                JButton restartGame = new JButton("Replay");
                restartGame.setFont(new Font("Serif", Font.BOLD, 30));
                restartGame.setBorder(BorderFactory.createEmptyBorder());
                restartGame.setContentAreaFilled(false);
                restartGame.setBackground(Color.white);
                restartGame.setForeground(Color.decode("#ffe6b3")); // Set button text color to white
                restartGame.setFocusable(false);
                restartGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                restartGame.setBounds(550, 475, 75, 75);
                restartGame.addActionListener(e -> Game.restart());

                // Add the buttons to the button panel
                buttonPanel.add(homeButton);
                buttonPanel.add(restartGame);

                // Add the labels and buttons to the panel
                panel.add(Box.createVerticalGlue());
                panel.add(endTitle);
                panel.add(brLabel);
                panel.add(endRes);
                panel.add(emptyPanel);
                panel.add(buttonPanel);
                panel.add(Box.createVerticalGlue());

                // Add the panel to the frame
                frame.getContentPane().add(panel);

                // Set the frame to be visible
                frame.setVisible(true);

                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                break;

            default:
                break;
        }
    }

    /**
     * Transfer from one to the next turn state.
     *
     * @param st The turn state we transfer to.
     */
    public static void runNextState(TURN_STATES st) {
        turnState = st;
        run();
        GuiGame.frame.repaint();
    }


    /**
     * Gets the piece at the given coordinates, where (x,y) = (0,0) -> top left corner of the board and (x,y) = (7,7) -> bottom right corner of the board.
     *
     * @param x The X-coordinate of the to be retrieved piece.
     * @param y The Y-coordinate of the to be retrieved piece.
     * @return The piece to be found at the coordinates or 'null' if no piece has those coordinates.
     */
    public static Piece getPiece(int x, int y) {
        if (xyInBounds(x, y)) {
            Piece foundPiece = board[y][x];
            if (Objects.nonNull(foundPiece)) {
                return foundPiece;
            }
        }

        return null;
    }


    /**
     * Gets all pieces of the current player
     *
     * @return ArrayList<Piece> of the current player's pieces on the board
     */
    public static ArrayList<Piece> getPlayerPieces(Player player) {
        return player.pieces;
    }


    /**
     * Returns whether the given coordinates are within the bounds of the board.
     *
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @return Whether the given coordinates are within the bounds of the board.
     */
    public static boolean xyInBounds(int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board.length;
    }


    /**
     * Moves a piece from the first two parameter to the second two parameters.
     *
     * @param fromX The X-coordinate from where the piece will be moved.
     * @param fromY The Y-coordinate from where the piece will be moved.
     * @param toX   The X-coordinate to which the piece will be moved.
     * @param toY   The Y-coordinate to which the piece will be moved.
     */
    public static void movePieceTo(int fromX, int fromY, int toX, int toY) {
        movePieceTo(fromX, fromY, toX, toY, true, true);
    }


    /**
     * Moves a piece from the first two parameter to the second two parameters.
     *
     * @param fromX The X-coordinate from where the piece will be moved.
     * @param fromY The Y-coordinate from where the piece will be moved.
     * @param toX   The X-coordinate to which the piece will be moved.
     * @param toY   The Y-coordinate to which the piece will be moved.
     */
    public static void movePieceTo(int fromX, int fromY, int toX, int toY, boolean updateGui, boolean play) {
        Piece p = getPiece(fromX, fromY);

        if (p != null) {
            p.makeMove(fromX, fromY, toX, toY);

            p = getPiece(fromX, fromY);

            // check if tile on toX,toY is king
            Piece p2 = getPiece(toX, toY);
            if (p2 != null) {
                halfMoves = 0;

                if (p2.pieceType == PieceEnum.KING && play)
                    kingCaptured = true;

                if (play) {
                    System.out.println(p2.pieceType + " of " + (p2.isWhite ? "white" : "black") + " captured!");
                }
            }

            if (p != null) {
                if (p.pieceType == PieceEnum.PAWN)
                    halfMoves = 0;
            }

            board[toY][toX] = p;
            p.x = toX;
            p.y = toY;
            board[fromY][fromX] = null;

            halfMoves++;

            if (updateGui) {
                rebuildBoard();
            }

            if (play) {
                runNextState(TURN_STATES.SWITCH_TURN);
            }
        }
    }


    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Returns the type of the piece that the user has to move.
     *
     * @return The type of the piece the user has to move.
     */
    public static PieceEnum getLegalPiece() {
        return legalPiece;
    }


    /**
     * Ensures that the visual piece is deleted from the visual board.
     *
     * @param x The X-coordinate at which a piece will be deleted visually.
     * @param y The Y-coordinate at which a piece will be deleted visually.
     */
    public static void deletePieceGUI(int x, int y) {
        GuiGame.removeVisualPiece(x, y);
    }


    /**
     * Ensures that the visual piece is added to the visual board.
     *
     * @param x     The X-coordinate at which a piece will be added visually.
     * @param y     The Y-coordinate at which a piece will be added visually.
     * @param type  The type of the piece to be added visually.
     * @param white Whether the piece is white or black.
     */
    public static void setPieceGUI(int x, int y, PieceEnum type, Boolean white) {
        GuiGame.addVisualPiece(x, y, type, white);
    }


    /**
     * Rebuilds the game board.
     */
    public static void rebuildBoard() {

        currentPlayer.pieces.clear();
        otherPlayer.pieces.clear();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                deletePieceGUI(x, y);
                Piece p = getPiece(x, y);
                if (p != null) {
                    setPieceGUI(x, y, p.pieceType, p.isWhite);

                    if (p.isWhite == currentPlayer.isWhite())
                        currentPlayer.pieces.add(p);
                    else if (p.isWhite == otherPlayer.isWhite())
                        otherPlayer.pieces.add(p);
                }
            }
        }
    }


    /**
     * Resets the turn state.
     */
    public static void resetTurnState() {
        kingCaptured = false;
        completedMoves = 0;
        GameSearchTree.bestLeafNodes.clear();

        turnState = TURN_STATES.START;
    }


    /**
     * Gets every legal move of a player with 4 integer elements with a required piece type
     *
     * @param isWhite whether the player is white or black
     * @return An arraylist of integer arrays
     */
    public static ArrayList<int[]> getEveryLegalMoveOfPlayer(Piece[][] board, boolean isWhite) {
        return getEveryLegalMoveOfPlayer(board, isWhite, PieceEnum.ANY);
    }


    /**
     * Gets every legal move of a player with 4 integer elements with a required piece type
     *
     * @param isWhite   Whether the player is white or black
     * @param pieceType Restrict the query to a certain piece type
     * @return An arraylist of integer arrays
     */
    public static ArrayList<int[]> getEveryLegalMoveOfPlayer(Piece[][] board, boolean isWhite, PieceEnum pieceType) {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                Piece p = getPiece(x, y);
                ArrayList<int[]> legalMoves;


                if (p != null && p.isWhite() == isWhite && (pieceType == PieceEnum.ANY || pieceType == p.pieceType)) {
                    legalMoves = p.getLegalMoves(p.x, p.y);
                } else
                    continue;

                for (int[] pos : legalMoves) {
                    int[] i = {p.x, p.y, pos[0], pos[1]};
                    moves.add(i);
                }
            }
        }

        return moves;
    }


    /**
     * Initializing the board with all the different pieces such that the board represents the actual starting positions for all the pieces.
     */
    public static void initializeBoard() {
        board = new Piece[8][8];

        board[0][0] = new Rook(false, 0, 0);
        board[0][1] = new Knight(false, 1, 0);
        board[0][2] = new Bishop(false, 2, 0);
        board[0][3] = new Queen(false, 3, 0);
        board[0][4] = new King(false, 4, 0);
        board[0][5] = new Bishop(false, 5, 0);
        board[0][6] = new Knight(false, 6, 0);
        board[0][7] = new Rook(false, 7, 0);

        for (int j = 0; j < board.length; j++) {
            board[1][j] = new Pawn(false, j, 1);
        }

        for (int j = 0; j < board.length; j++) {
            board[6][j] = new Pawn(true, j, 6);
        }

        board[7][0] = new Rook(true, 0, 7);
        board[7][1] = new Knight(true, 1, 7);
        board[7][2] = new Bishop(true, 2, 7);
        board[7][3] = new Queen(true, 3, 7);
        board[7][4] = new King(true, 4, 7);
        board[7][5] = new Bishop(true, 5, 7);
        board[7][6] = new Knight(true, 6, 7);
        board[7][7] = new Rook(true, 7, 7);

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                if (!getPiece(x, y).isWhite)
                    otherPlayer.pieces.add(getPiece(x, y));
            }
        }

        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (getPiece(x, y).isWhite)
                    currentPlayer.pieces.add(getPiece(x, y));
            }
        }
    }

    /**
     * Returns the state of the entire board as a text.
     *
     * @return A string with a textual representation of the board with figures
     */
    public static String getBoardStringRepresentation(boolean newLinesAsText) {
        StringBuilder boardRepresentation = new StringBuilder();
        boardRepresentation.append(StringUtils.repeat("*", 89)).append((newLinesAsText) ? "\\n" : "\n");

        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {

                Piece p = board[y][x];
                if (x == 0) boardRepresentation.append("* ");
                else boardRepresentation.append(" | ");

                if (p != null) {
                    String color;
                    if (p.isWhite) color = "w ";
                    else color = "b ";
                    String pieceType = p.pieceType == PieceEnum.ANY ? "marker" : p.pieceType.toString();
                    boardRepresentation.append(String.format("%-8.8s", color + pieceType));
                } else {
                    boardRepresentation.append("_ ______");
                }

            }
            boardRepresentation.append((newLinesAsText) ? " *\\n" : " *\n");
        }

        boardRepresentation.append(StringUtils.repeat("*", 89)).append((newLinesAsText) ? "\\n" : "\n");
        return boardRepresentation.toString();
    }
}
