package com.oop.chess.model.search;

import com.oop.chess.Game;
import com.oop.chess.gui.GuiGame;

import java.util.ArrayList;

public class GameSearchTree {
    // General idea:
    // Get list of legal moves for current player.
    //
    // For each move, execute the move, then perform the search again with depth - 1. After that, undo the move.
    //

    // Display the search visually? (Slower if true)
    static boolean visual_search = false;

    // Store the best move found by the GST (from_x,from_y,to_x,to_y)
    // Change this only for root node once minimax scores are known?
    static int[] bestMove;


    // Method that does the search and then returns the static bestmove
    public static int[] search(int depth, boolean is_white) {
        // the root node will always be max, so set the maximizer's colour to is_white
        maximizing_colour = is_white;

        // Start searching at the root
        depthSearch(depth, true, true);
        Game.rebuildBoard();

        // return the best move (from_x, from_y, to_x, to_y)
        return bestMove;
    }

    static boolean maximizing_colour;


    // this is the AI's brain
    // TODO: Minimax & alpha-beta, Evaluation function calls
    public static int depthSearch(int depth, boolean maximizing, boolean root) {
        if (depth == 0)
            return 0; // break recursion at lowest depth by simply returning the value of the evaluation function

        // Get every legal move for the game's current player
        ArrayList<int[]> moves;
        if (root)
            moves = Game.getEveryLegalMoveOfPlayer(maximizing ? maximizing_colour : !maximizing_colour, Game.getLegalPiece());
        else
            moves = Game.getEveryLegalMoveOfPlayer(maximizing ? maximizing_colour : !maximizing_colour, Game.PieceEnum.ANY);

        if (visual_search)
            GuiGame.frame.repaint();

        for(int[] move : moves) {
            // Store state before executing a move

            boolean colour = (maximizing ? maximizing_colour : !maximizing_colour);
            String node = FEN.encode(Game.board, colour  ? 0 : 1);

            // Execute a specific move
            Game.movePieceTo(move[0], move[1], move[2], move[3], visual_search, false);

            // Search with depth-1 for other player
            depthSearch(depth-1, !maximizing, false);

            // Undo move (by rolling back to state before piece move)
            Game.board = FEN.decode(node);
        }

        // This is purely for testing purposes, remove this later
        if (root)
            bestMove = moves.get(0);

        return 0;   // Return best move based on score
    }
}
