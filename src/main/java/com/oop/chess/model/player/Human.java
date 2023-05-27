package com.oop.chess.model.player;

import com.oop.chess.Game;
import com.oop.chess.Game.PieceEnum;
import com.oop.chess.gui.GuiGame;
import com.oop.chess.gui.HumanClicking;
import com.oop.chess.gui.VisualBoard;
import com.oop.chess.model.config.Configuration;
import com.oop.chess.model.integrations.chat_gpt.ChatGPT;
import com.oop.chess.model.integrations.obj.ChatGPTAdvice;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a human player.
 */
public class Human extends Player {

    boolean move;
    boolean isWhite;
    boolean help;
    int oldTileX = 0;
    int oldTileY = 0;
    int newTileX = 0;
    int newTileY = 0;
    HumanClicking clicker = null;


    /**
     * Creates a new human player.
     *
     * @param isWhite A boolean indicating whether the player is white or black.
     * @param help    A boolean indicating whether the player wants any help, which is to present the player the different moves it can make.
     */
    public Human(boolean isWhite, boolean help) {
        this.isWhite = isWhite;
        this.help = help;

        pieces = new ArrayList<>();
    }


    /**
     * Determines the logic for the player's turn.
     *
     * @param piece The piece the player is allowed to move.
     * @return Whether the player has made a move and thus ended their turn.
     */
    public boolean turn(PieceEnum piece) {
        askChatGptToHelpTheUser();

        // Add the human's clicker to the board
        if (clicker == null) {
            clicker = new HumanClicking(this);
        }
        clicker.enabled = true;

        return false;
    }


    /**
     * Sets the move of the player from the first two parameters to the second two parameters.
     *
     * @param ox The X-coordinate from where the piece will be moved.
     * @param oy The Y-coordinate from where the piece will be moved.
     * @param nx The X-coordinate to which the piece will be moved.
     * @param ny The Y-coordinate to which the piece will be moved.
     */
    public void setMove(int ox, int oy, int nx, int ny) {
        this.oldTileX = ox;
        this.oldTileY = oy;
        this.newTileX = nx;
        this.newTileY = ny;

        clicker.enabled = false;

        Game.movePieceTo(ox, oy, nx, ny);

        this.move = true;
    }


    /**
     * Returns whether the player has selected the help option.
     *
     * @return Whether the player has selected the help option.
     */
    public boolean hasHelp() {
        return help;
    }


    /**
     * Returns whether the current player is white or black.
     *
     * @return Whether the current player is white or black.
     */
    public boolean isWhite() {
        return isWhite;
    }


    /**
     * Gets a String of information about the current human player.
     *
     * @return A String of information about the current human player.
     */
    public String toString() {
        return "(Human Player," + (isWhite ? "White" : "Black") + ")";
    }


    /**
     * Makes an asynchronous ChatGPT request to get a hint for the user.
     */
    private void askChatGptToHelpTheUser() {
        if (Configuration.enableChatGptIntegration) {
            CompletableFuture<ChatGPTAdvice> future = ChatGPT.askAdvice(isWhite);

            future.thenAccept(advice -> {
                GuiGame.updateWhoseTurnOnChatGptResponse(advice.isEmpty());

                if (!advice.isEmpty()) {
                    JPanel fromTile = (JPanel) GuiGame.visualBoard.getComponent(advice.getFromTileX() + advice.getFromTileY() * 8);
                    JPanel toTile = (JPanel) GuiGame.visualBoard.getComponent(advice.getToTileX() + advice.getToTileY() * 8);
                    fromTile.setBackground(Color.decode("#5764F1"));
                    toTile.setBackground(Color.decode("#5764F1"));
                    VisualBoard.recoloredCells.add(advice.getFromTile());
                    VisualBoard.recoloredCells.add(advice.getToTile());
                }
            });
        }
    }
}
