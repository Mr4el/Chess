package com.oop.chess;

import com.oop.chess.model.pieces.*;
import com.oop.chess.model.player.*;
import com.oop.chess.gui.*;
import com.oop.chess.model.search.FEN;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a single game.
 */
public class Game {
    public static Piece[][] board;
    ChessMain parent;

    // store player 1 and 2 in array, players[current_player_index] accesses the current player (with current_player_index being either 0 or 1.)
    // current_player is a static reference to the current player, mainly useful for GUI stuff
    Player[] players = {null, null};
    public static Player current_player;
    public static Player other_player;
    int current_player_index;

    // How many moves since the last piece capture/pawn advance?
    static int half_moves;

    // How many completed moves/turns total? (Increments after black player)
    static int full_moves;

    static boolean king_captured = false;

    // The game's GUI;
    public static GuiGame gui;

    // the different values which the turn state can have.
    public enum TURN_STATES {
        START,              // basic initialization
        DICE_ROLL,          // rolling dice & determining legal pieces
        CHOOSING_MOVE,      // player choosing a move
        SWITCH_TURN,         // switching turns (so player 2 plays after player 1)
        GAME_END
    }

    // what is the current state of the turn?
    static TURN_STATES turn_state;

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
    public static PieceEnum legal_piece;

    /**
     * Creates a new game of dicechess.
     *
     * @param parent  The dicechess object to be utilized.
     * @param player1 The first player of the game.
     * @param player2 The second player of the game, which allows for switching between a human and AI player.
     */
    public Game(ChessMain parent, Player player1, Player player2) {
        this.parent = parent;

        this.players[0] = player1;
        this.players[1] = player2;

        full_moves = 0;
        half_moves = 0;

        current_player_index = 0;
        current_player = this.players[current_player_index];
        other_player = this.players[1];

        initializeBoard();
        gui = new GuiGame(board);

        System.out.println(current_player + " " + getPlayerPieces(current_player));
    }


    /**
     * This is the main loop of the program where the state of the program is checked.
     */
    public void run() {
        switch (turn_state) {
            case START:
                gui.setTitle((current_player_index == 0 ? "Dice Chess! - White's Turn" : "Dice Chess! - Black's Turn"));

                GuiGame.setWhoseTurn(current_player_index);
                runNextState(TURN_STATES.DICE_ROLL);

                // String fen = FEN.encode(board, current_player_index, half_moves, full_moves);
                // System.out.println(fen);
                // System.out.println(Arrays.deepToString(FEN.decode(fen)));

                // System.out.println(getEveryLegalMoveOfPlayer(is_white);

                if (ChessMain.debug)
                    System.out.println("GAME STATE - Start turn for " + current_player);
                gui.repaint();
                break;

            // roll the dice to determine which pieces the player is allowed to move
            case DICE_ROLL:
                legal_piece = Dice.roll(current_player.isWhite());
//                while (!(legal_piece == PieceEnum.PAWN || legal_piece == PieceEnum.KING || legal_piece == PieceEnum.KNIGHT)) {
//                    legal_piece = Dice.roll(current_player.isWhite());
//                }
                //legal_piece = PieceEnum.ANY;
                if (current_player_index == 1 && GuiMenu.playingAI) {
                    RandomBot.randomPlay(getLegalPiece());
                }
                GuiGame.frame.revalidate();
                GuiGame.frame.repaint();

                if (legal_piece != null) {
                    gui.setLegalPiece(legal_piece, current_player_index);
                    gui.setTitle(gui.getTitle() + " - Legal piece: " + legal_piece);

                    runNextState(TURN_STATES.CHOOSING_MOVE);


                    break;
                }

                // the player is currently making a move
            case CHOOSING_MOVE:
                if (ChessMain.debug)
                    System.out.println("GAME STATE - " + current_player + " can make a move.");

                // if this returns true, then the player has successfully finished a round
                current_player.turn(legal_piece);
                break;


            // next player's turn
            case SWITCH_TURN:
                if (king_captured) {
                    runNextState(TURN_STATES.GAME_END);
                    break;
                }

                current_player_index++;
                if (current_player_index > 1) {
                    full_moves++;
                    current_player_index = 0;
                }

                System.out.println(current_player.pieces);

                other_player = current_player;
                current_player = players[current_player_index];

                runNextState(TURN_STATES.START);

                // System.out.println(current_player + " " + getPlayerPieces(current_player));
                break;

            // King captured
            case GAME_END:
                String title = "";
                if (current_player.isWhite())
                    title = "White captured Black's King!";
                else
                    title = "Black captured White's King!";

                gui.setTitle(title);
                break;

            default:
                break;
        }
    }

    public static void runNextState(TURN_STATES st) {
        turn_state = st;
        ChessMain.getInstance().game.run();
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
        if (x < 0 || x >= board.length || y < 0 || y >= board.length)
            return false;
        return true;
    }

    public static void movePieceTo(int from_x, int from_y, int to_x, int to_y) {
        movePieceTo(from_x, from_y, to_x, to_y, true, true);

    }
    /**
     * Moves a piece from the first two parameter to the second two parameters.
     *
     * @param from_x The X-coordinate from where the piece will be moved.
     * @param from_y The Y-coordinate from where the piece will be moved.
     * @param to_x   The X-coordinate to which the piece will be moved.
     * @param to_y   The Y-coordinate to which the piece will be moved.
     */
    public static void movePieceTo(int from_x, int from_y, int to_x, int to_y, boolean update_gui, boolean play) {
        Piece p = getPiece(from_x, from_y);

        if (p != null) {
            p.makeMove(from_x, from_y, to_x, to_y);

            p = getPiece(from_x, from_y);

            // check if tile on to_x,to_y is king
            Piece p2 = getPiece(to_x, to_y);
            if (p2 != null) {
                half_moves = 0;

                if (p2.piece_type == PieceEnum.KING && play)
                    king_captured = true;
            }

            if (p != null) {
                if (p.piece_type == PieceEnum.PAWN)
                    half_moves = 0;
            }

            // if (getPlayerPieces(other_player).contains(p2))
            //     other_player.pieces.remove(p2);

            board[to_y][to_x] = p;
            p.x = to_x;
            p.y = to_y;
            board[from_y][from_x] = null;

            half_moves++;

            if (update_gui) {
                rebuildBoard();
            }

            if (play)
                runNextState(TURN_STATES.SWITCH_TURN);
        }
    }

    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public static Player getCurrentPlayer() {
        return current_player;
    }

    /**
     * Returns the type of the piece that the user has to move.
     *
     * @return The type of the piece the user has to move.
     */
    public static PieceEnum getLegalPiece() {
        return legal_piece;
    }

    /**
     * Ensures that the visual piece is deleted from the visual board.
     *
     * @param x The X-coordinate at which a piece will be deleted visually.
     * @param y The Y-coordinate at which a piece will be deleted visually.
     */
    public static void GUIdeletePiece(int x, int y) {
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
    public static void GUIsetPiece(int x, int y, PieceEnum type, Boolean white) {
        GuiGame.addVisualPiece(x, y, type, white);
    }

    // PHASE 2
    // Ensure correctness of the board both logically and visually
    public static void rebuildBoard() {

        current_player.pieces.clear();
        other_player.pieces.clear();

        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board.length; y++) {
                GUIdeletePiece(x,y);
                Piece p = getPiece(x,y);
                if (p != null) {
                    GUIsetPiece(x,y,p.piece_type,p.isWhite);

                    if (p.isWhite == current_player.isWhite())
                        current_player.pieces.add(p);
                    else if (p.isWhite == other_player.isWhite())
                        other_player.pieces.add(p);
                }
            }
        }
    }

    public static void resetTurnState() {
//        this.turn_state = turn_state;
        king_captured = false;
        turn_state = TURN_STATES.START;
    }

    /**
     * Gets every legal move of a player with 4 integer elements (first two: current piece position, last two: second piece position)
     *
     * @param is_white     The X-coordinate at which a piece will be added visually.
     * @param piece_type   Restrict the query to a certain piece type
     * @return An arraylist of integer arrays
     */
    public static ArrayList<int[]> getEveryLegalMoveOfPlayer(boolean is_white) {
        return getEveryLegalMoveOfPlayer(is_white, PieceEnum.ANY);
    }


    /**
     * Gets every legal move of a player with 4 integer elements with a required piece type
     *
     * @param is_white     The X-coordinate at which a piece will be added visually.
     * @param piece_type   Restrict the query to a certain piece type
     * @return An arraylist of integer arrays
     */
    public static ArrayList<int[]> getEveryLegalMoveOfPlayer(boolean is_white, PieceEnum piece_type) {
        ArrayList<int[]> moves = new ArrayList<int[]>();

        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board.length; y++) {
                Piece p = getPiece(x,y);
                ArrayList<int[]> legal_moves = new ArrayList<int[]>();

                if (p != null && p.isWhite() == is_white && (piece_type == PieceEnum.ANY || piece_type == p.piece_type)) {
                    legal_moves = p.getLegalMoves(p.x, p.y);
                }else
                    continue;

                for(int[] pos : legal_moves) {
                    int[] i = {p.x,p.y,pos[0],pos[1]};
                    moves.add(i);
                }
            }
        }

        return moves;
    }


    /**
     * Initializing the board with all the different pieces such that the board represents the actual starting positions for all the pieces.
     */
    public void initializeBoard() {
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
                if (!getPiece(x,y).isWhite)
                    other_player.pieces.add(getPiece(x, y));
            }
        }

        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (getPiece(x,y).isWhite)
                    current_player.pieces.add(getPiece(x, y));
            }
        }
    }

    // Maybe later transform into a switch case (would not work if we separate white and black pieces --> Then make one big switch case)
    public double evaluationFunction(String fen, boolean isWhite) {

        int whiteKings = 0;
        int blackKings = 0;
        int whiteBishops = 0;
        int blackBishops = 0;
        int whitePawns = 0;
        int blackPawns = 0;
        int whiteKnights = 0;
        int blackKnights = 0;
        int whiteRooks = 0;
        int blackRooks = 0;
        int whiteQueens = 0;
        int blackQueens = 0;

        if (isWhite) {
            for (int i = 0; i < fen.length(); i++) {
                if ((fen.charAt(i)) == ('K')) { whiteKings++; }

                if ((fen.charAt(i)) == ('B')) { whiteBishops++; }

                if ((fen.charAt(i)) == ('P')) { whitePawns++; }

                if ((fen.charAt(i)) == ('N')) { whiteKnights++; }

                if ((fen.charAt(i)) == ('R')) { whiteRooks++; }

                if ((fen.charAt(i)) == ('Q')) { whiteQueens++; }
            }
        }

        else {
            for (int i = 0; i < fen.length(); i++) {
                if ((fen.charAt(i)) == ('k')) { blackKings++; }

                if ((fen.charAt(i)) == ('b')) { blackBishops++; }

                if ((fen.charAt(i)) == ('p')) { blackPawns++; }

                if ((fen.charAt(i)) == ('n')) { blackKnights++; }

                if ((fen.charAt(i)) == ('r')) { blackRooks++; }

                if ((fen.charAt(i)) == ('q')) { blackQueens++; }
            }
        }



        /** TODO:
         * Possible situations to consider:
         * Number of squares per player that cannot be protected by pawns
         * King safety (number of squares where king is not protected)
         * Pawn structure --> Information about doubled, blocked and isolated pawns
         */

        double kingsDifference = whiteKings - blackKings;
        double queensDifference = whiteQueens - blackQueens;
        double rooksDifference = whiteRooks - blackRooks;
        double bishopsDifference = whiteBishops - blackBishops;
        double knightsDifference = whiteKnights - blackKnights;
        double pawnsDifference = whitePawns - blackPawns;

        double kingsCoefficient = 200;
        double queensCoefficient = 9;
        double rooksCoefficient = 5;
        double bishopsCoefficient = 3;
        double knightsCoefficient = 3;
        double pawnsCoefficient = 1;

        double evaluationFunctionMaterial = kingsCoefficient*kingsDifference + queensCoefficient*queensDifference + rooksCoefficient*rooksDifference + bishopsCoefficient*bishopsDifference + knightsCoefficient*knightsDifference + pawnsCoefficient*pawnsDifference;
        double evaluationFunctionMobility = getEveryLegalMoveOfPlayer(isWhite).size()-getEveryLegalMoveOfPlayer(!isWhite).size();


        double evaluationFunctionChosen = 0;


        return evaluationFunctionChosen;
    }
}

/* basic idea for game loop ( update() ):

    - dice roll, check if any legal moves are possible with the piece chosen by the dice, roll again if needed
    - call players[current_player].turn method with specific piece type
    - player can choose a piece of the type chosen by the dice
    - chosen piece checks all legal moves, displays them to the player
    - player chooses a movie, moved piece calls its move() method

    at the end of update(), if the current player has successfully made a legal move, set current_player to 0 if it's 1, and 1 if it's 0.
*/
