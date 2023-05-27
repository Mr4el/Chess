package com.oop.chess.model.integrations.chat_gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.chess.Game;
import com.oop.chess.model.config.Configuration;
import com.oop.chess.model.integrations.obj.ChatGPTAdvice;
import com.oop.chess.model.pieces.Piece;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class makes ChatGPT requests and processes its response for internal use.
 */
public class ChatGPT {

    static CloseableHttpClient httpClient = HttpClients.createDefault();


    /**
     * Asks ChatGPT what move it would make for a chessboard situation.
     *
     * @param isWhite The color of the player for which the request is made
     * @return Returns a future object with the processed ChatGPT response
     */
    public static CompletableFuture<ChatGPTAdvice> askAdvice(boolean isWhite) {
        CompletableFuture<ChatGPTAdvice> future = new CompletableFuture<>();
        Thread thread = new Thread(() -> {
            String prompt = generateRequest(isWhite);
            String response = makeRequest(prompt);

            future.complete(parseResponse(response));
        });

        thread.start();

        return future;
    }


    /**
     * Creates a request for ChatGTP to which it will have to respond.
     *
     * @param isWhite The color of the player for which the request is made
     * @return Text prompt for ChatGPT
     */
    private static String generateRequest(boolean isWhite) {
        String boardRepresentation = Game.getBoardStringRepresentation(true);
        String possibleMoves = getPossibleMoves(isWhite);

        return "Imagine that you are a chess player with unusual rules.\\n" +
            "According to the rules of the game, the pieces can move as in classical chess, with the exception of " +
            "rake (rake cannot be used).\\nEach move, a piece is randomly determined, which the player can move, you " +
            "cannot randomly choose your piece.\\nYou play for the " + ((isWhite) ? "WHITE" : "BLACK") + " side and " +
            "according to the rules you can only play for the " + Game.legalPiece + " piece now.\\nThe goal of the " +
            "game is to eventually eat the opponent's king, after which the game ends and the player who ate the " +
            "king wins.\\nHere is a text representation of what the chessboard looks like now in the form of a " +
            "table:\\n" + boardRepresentation + "The coordinates of the cells on the board consist of a latin letter " +
            "and a number, where the letter is a column and the number is a row. The letter has a limit from a to h " +
            "and the number from 1 to 8. The numbering starts from the lower left corner, so at the beginning of the " +
            "game the whit king is on cell e1, and the black king is on cell e8.\\nThe figure on the board is " +
            "described in the following format: the colour of the figure, the name of the figure. The colour of the " +
            "figure is indicated as w - white or b - black. The name of the figure is shown in caps. If there is no " +
            "figure on the cell, then the cell has the following entry '_ ______'.\\nAt the moment you can make one " +
            "of the moves: " + possibleMoves + ". Choose the move you think is correct for the '" +
            ((isWhite) ? "w" : "b") + " " + Game.legalPiece + "' figure and return an answer without any " +
            "explanatory comments.\\n";
    }


    /**
     * Generates text with all possible moves.
     *
     * @param isWhite The color of the player for which the request is made
     * @return Text with possible moves
     */
    private static String getPossibleMoves(boolean isWhite) {
        StringBuilder possibleMoves = new StringBuilder();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = Game.board[y][x];

                if (p != null && p.isWhite == isWhite && p.pieceType == Game.legalPiece) {
                    ArrayList<int[]> moves = p.getLegalMoves(p.x, p.y);
                    int[] moveFrom = new int[]{p.x, p.y};

                    for (int[] move : moves) {
                        possibleMoves
                            .append(convertToChatGptCords(moveFrom))
                            .append(" to ")
                            .append(convertToChatGptCords(move))
                            .append(", ");
                    }
                }
            }
        }

        return possibleMoves.toString();
    }


    /**
     * Makes a request to ChatGPT.
     *
     * @param prompt Request that ChatGPT should respond to
     * @return Returns a ChatGPT response
     */
    private static String makeRequest(String prompt) {
        String jsonRequest = "{\"model\": \"" + Configuration.chatGptApiModel + "\",\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}],\"temperature\": 0.7}";


        try {
            HttpPost httpPost = new HttpPost(Configuration.chatGptApiUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + Configuration.chatGptApiKey);
            httpPost.setEntity(new StringEntity(jsonRequest));
            HttpResponse response = httpClient.execute(httpPost);
            String jsonString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("OpenAI API returned " + response.getStatusLine().getStatusCode() + " code.");
                return "";
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode jsonArray = jsonNode.get("choices");

            // Returns first answer from response
            for (JsonNode jsonElement : jsonArray) {
                return jsonElement.get("message").get("content").asText();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Processes the ChatGPT response in several steps, since ChatGPT does not always respond in the required format and sometimes even refuses to advise a move.
     *
     * @param response ChatGPT response
     * @return Returns the processed response with the coordinates of the possible movement of the shape
     */
    private static ChatGPTAdvice parseResponse(String response) {
        System.out.println("ChatGPT response: " + response);
        // The pattern we expect
        Pattern firstPattern = Pattern.compile("[a-h][1-8] to [a-h][1-8]");
        Matcher firstMatcher = firstPattern.matcher(response);

        // Arbitrary pattern that sometimes returns
        Pattern secondPattern = Pattern.compile("[a-h][1-8]-[a-h][1-8]");
        Matcher secondMatcher = secondPattern.matcher(response);

        // Last chance to find the answer, while you have to guess from cords
        Pattern lastChancePattern = Pattern.compile("[a-h][1-8]");
        Matcher lastChanceMatcher = lastChancePattern.matcher(response);

        int[] from = new int[2];
        int[] to;
        if (firstMatcher.find()) {
            String advice = firstMatcher.group(0);

            from = convertToInternalCords(advice.substring(0, 2));
            to = convertToInternalCords(advice.substring(6, 8));
        } else if (secondMatcher.find()) {
            String advice = secondMatcher.group(0);

            from = convertToInternalCords(advice.substring(0, 2));
            to = convertToInternalCords(advice.substring(3, 5));
        } else if (lastChanceMatcher.find()) {
            String advice = lastChanceMatcher.group(0);

            to = convertToInternalCords(advice.substring(3, 5));

            // Trying to guess from
            boolean missingPointFound = false;
            for (int y = 0; y < 8; y++) {
                if (missingPointFound) break;
                for (int x = 0; x < 8; x++) {
                    Piece p = Game.board[y][x];
                    if (p != null && Game.board[y][x].pieceType == Game.legalPiece) {
                        if (legalMovesContainsMove(p.getLegalMoves(p.x, p.y), to)) {
                            from = new int[]{p.x, p.y};
                            missingPointFound = true;
                            break;
                        }
                    }
                }
            }

            if (!missingPointFound) {
                return new ChatGPTAdvice();
            }
        } else {
            return new ChatGPTAdvice();
        }

        Piece p = Game.getPiece(from[0], from[1]);
        if (p != null) {
            ArrayList<int[]> legalMoves = p.getLegalMoves(p.x, p.y);
            if (legalMovesContainsMove(legalMoves, to))
                return new ChatGPTAdvice(from, to);
        }
        System.out.println("ChatGPT returned an impossible move");
        return new ChatGPTAdvice();
    }


    /**
     * Converts the chess description of the pieces to the internal coordinates of the chessboard.
     *
     * @param input Text coordinates. Coordinate range: a1 - h8
     * @return Returns internal coordinates
     */
    private static int[] convertToInternalCords(String input) {
        Pattern pattern = Pattern.compile("^[a-h][1-8]$");
        if (pattern.matcher(input).matches()) {
            int x = input.charAt(0) - 'a';
            int y = 7 - (input.charAt(1) - '1');
            return new int[]{x, y};
        } else {
            return null;
        }
    }


    /**
     * Converts internal coordinates to common coordinates.
     *
     * @param input Internal coordinates.
     * @return Coordinate range: a1 - h8
     */
    private static String convertToChatGptCords(int[] input) {
        int reversedY = 7 - input[1];
        char x = (char) ('a' + input[0]);
        char y = (char) ('1' + reversedY);
        return String.valueOf(x) + y;
    }


    /**
     * Checks if the list of legal coordinates has the specified.
     *
     * @param legalMoves List of coordinates among which to find
     * @param move       The coordinate to be found
     * @return Returns true or false
     */
    private static boolean legalMovesContainsMove(ArrayList<int[]> legalMoves, int[] move) {
        for (int[] legalMove : legalMoves) {
            if (legalMove[0] == move[0] && legalMove[1] == move[1])
                return true;
        }
        return false;
    }
}
