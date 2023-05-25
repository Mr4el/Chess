package com.oop.chess.model.machine.learning;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.debug.GameLogger;
import com.oop.chess.model.player.SearchAI;
import com.oop.chess.model.search.FEN;

import java.util.ArrayList;

public class TemporalDifferenceLeaf {

    // All the weights of the evaluation functions.
    static double[] new_weights;

    public static final double LAMBDA = 0.7;
    public static final double ALPHA = 0.05;


    /**
     * Updates the weights of all the evaluation functions.
     *
     * The TDLEAF formula can be found here (page 2, equation 4): https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.117.9094&rep=rep1&type=pdf.
     * @param all_states The states of the board after the agent has made a move.
     * @param isWhite Whether the player is white or black.
     * @param weights The current weights of the evaluation functions.
     */
    public static void updateWeights(ArrayList<Object> all_states, boolean isWhite, double[] weights) {

        //double partialDerivative = 0;

        double[] weightsDerivative;

        double[] new_weights = new double[weights.length];

        // Performing the computation for each part of the evaluation functions such that each of the weights corresponding to these components is updated.
        for (int i = 0; i < weights.length; i++) {
            // Defining a weights array which will later be used to calculate the value of the evaluation function and setting every value to 0 expect for the weight that is currently updated.
            weightsDerivative = new double[8];
            weightsDerivative[i] = 1.0;

            double final_value = 0;

            // Looping over all the states of a game played.
            for (int t = 0; t < all_states.size() - 1; t++) {

                double partialDerivative = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) all_states.get(t)), isWhite, weightsDerivative, false);
                double partialDerivative_conversion = tanh(partialDerivative);

                double next_state = 0;
                double next_state_conversion = 0;
                // If the next state to be visited is the last state in the all_states ArrayList, only the reward of the game will be retrieved (since no leaf nodes are present in the final board).
                if (t == all_states.size() - 2) {
                    next_state = (double) all_states.get(t+1);
                }
                else {
                    next_state = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) all_states.get(t+1)), isWhite, weights, false);
                    // Converting the next_state number to fit between -1 and 1.
                    next_state_conversion = tanh(next_state);
                }
                double current_state = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) all_states.get(t)), isWhite, weights, false);
                // Converting the current_state number to fit between -1 and 1.
                double current_state_conversion = tanh(current_state);

                // The temporal difference between the recently calculated next_state and current_state.
                double dt = next_state_conversion - current_state_conversion;

                // Since positive values can occur because the opponent made a blunder, like moving the King to a vulnerable position, dt is set to 0 such that our algorithm does not learn the blunders of opponents.
                //TODO: We can ignore this if our algorithm predicted the bad move (I think we can actually skip it all times if we are going to play against minimax algorithms)
                if (dt > 0) {
                    // When dt is equal to 0, it does not learn anything from that state meaning that the opponent made a blunder there.
                    dt = 0;
                }

                double lambda_dt_sum = 0;

                // Looping over all the states of a game played starting at the value of t.
                for (int j = t; j < all_states.size() - 1; j++) {
                    // The following line forms the part in between the square brackets of equation 4 on page 2 (link in Javadoc).
                    lambda_dt_sum += Math.pow(LAMBDA, j-t) * dt;
                }
                // Combining the part in between the square brackets and the calculated evaluation value of equation 4 on page 2 (link in Javadoc).
                final_value += partialDerivative_conversion * lambda_dt_sum;
            }
            // Finally, updating each weight by calculating the whole equation 4 on page 2 (link in Javadoc).
            new_weights[i] = weights[i] + ALPHA * final_value;
        }
        // Changing the "old" weights to the newly calculated weights.
        GameLogger.logWeights(new_weights);
        SearchAI.changeWeights(new_weights);
    }

    /**
     * Converts the passed evaluation_value into a number between -1 and 1.
     *
     * @param evaluation_value The result of the evaluation function.
     * @return The number between -1 and 1.
     */
    public static double tanh(double evaluation_value) {

        //TODO: Look at how to include the beta values.

        double sinh = Math.exp(evaluation_value) - Math.exp(-evaluation_value);
        double cosh = Math.exp(evaluation_value) + Math.exp(-evaluation_value);
        double result = sinh / cosh;
        return result;
    }
}
