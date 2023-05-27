package com.oop.chess.model.machine.learning;

import com.oop.chess.EvaluationFunction;
import com.oop.chess.debug.GameLogger;
import com.oop.chess.model.config.Configuration;
import com.oop.chess.model.player.SearchAI;
import com.oop.chess.model.search.FEN;

import java.util.ArrayList;

/**
 * This class is used to update the weights of the evaluation function components.
 */
public class TemporalDifferenceLeaf {

    // This array determines which elements in the weights array are fixed.
    static boolean[] fixed = {false, true, false, false, false, false, false, false};

    static int games = 0;


    /**
     * Updates the weights of all the evaluation functions.
     *
     * @param allStates The states of the board after the agent has made a move.
     * @param isWhite   Whether the player is white or black.
     * @param weights   The current weights of the evaluation function components.
     */
    public static void updateWeights(ArrayList<Object> allStates, boolean isWhite, double[] weights) {
        System.out.println(allStates.toString());

        double[] newWeights = new double[weights.length];

        double[] weightChange = new double[weights.length];

        // Performing the computation for each part of the evaluation functions such that each of the weights corresponding to these components is updated.
        for (int i = 0; i < weights.length; i++) {
            if (fixed[i]) {
                newWeights[i] = weights[i];
                continue;
            }

            // Defining a weights array which will later be used to calculate the value of the evaluation function and setting every value to 0 expect for the weight that is currently updated.
            double[] weightsDerivative = new double[weights.length];
            weightsDerivative[i] = 1.0;

            double finalValue = 0;

            // Looping over all the states of a game played.
            for (int t = 0; t < allStates.size() - 1; t++) {

                double partialDerivative = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) allStates.get(t)), isWhite, weightsDerivative, false);
                double partialDerivative_conversion = tanhDerivative(partialDerivative);

                double lambdaDtSum = 0;

                // Looping over all the states of a game played starting at the value of t.
                for (int j = t; j < allStates.size() - 1; j++) {

                    double nextState;
                    double nextStateConversion = 0;
                    // If the next state to be visited is the last state in the allStates ArrayList, only the reward of the game will be retrieved (since no leaf nodes are present in the final board).
                    if (j == allStates.size() - 2) {
                        nextState = (double) allStates.get(j + 1);
                    } else {
                        nextState = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) allStates.get(j + 1)), isWhite, weights, false);
                        // Converting the nextState number to fit between -1 and 1.
                        nextStateConversion = tanh(nextState);
                    }
                    double currentState = EvaluationFunction.evaluationFunctionsCombined(FEN.decode((String) allStates.get(j)), isWhite, weights, false);
                    // Converting the currentState number to fit between -1 and 1.
                    double currentStateConversion = tanh(currentState);

                    // The temporal difference between the recently calculated nextState and currentState.
                    double dj = nextStateConversion - currentStateConversion;
                    lambdaDtSum += Math.pow(Configuration.lambda, j - t) * dj;
                }
                // Combining the part in between the square brackets and the calculated evaluation value.
                finalValue += partialDerivative_conversion * lambdaDtSum;
            }
            // Finally, updating each weight by calculating the whole equation.
            weightChange[i] = Configuration.alpha * finalValue;
            newWeights[i] = weights[i] + weightChange[i];
        }

        games++;

        // Decreasing the learning rate.
        if ((double) (allStates.get(allStates.size() - 1)) == 1.0) {
            Configuration.alpha = clamp(Configuration.alpha - Configuration.alphaDecreaseRate, Configuration.minAlpha, Configuration.maxAlpha);
        }

        // Changing the "old" weights to the newly calculated weights.
        GameLogger.logWeights(newWeights);
        SearchAI.changeWeights(newWeights);
    }


    /**
     * Converts the passed evaluationValue into a number between -1 and 1 with an advantage being that it is zero-centred.
     *
     * @param evaluationValue The result of the evaluation function.
     * @return The number between -1 and 1.
     */
    public static double tanh(double evaluationValue) {

        evaluationValue *= Configuration.beta;

        double sinh = Math.exp(evaluationValue) - Math.exp(-evaluationValue);
        double cosh = Math.exp(evaluationValue) + Math.exp(-evaluationValue);

        if (Double.isNaN(sinh) || Double.isNaN(cosh)) {
            return 0;
        }

        return sinh / cosh;
    }


    /**
     * Computes the derivative of the tanh function with x being equal to evaluationValue.
     *
     * @param evaluationValue The result of the evaluation function.
     * @return The derivative of the tanh function at x=evaluationValue times the evaluationValue itself.
     */
    public static double tanhDerivative(double evaluationValue) {
        double tanhEvaluation = tanh(evaluationValue);
        return (1 - Math.pow(tanhEvaluation, 2)) * evaluationValue;
    }


    /**
     * Returns the maximum value of the passed min and the minimum of the passed on value and max. This is done to check whether the value is still above min and otherwise taking the min value.
     *
     * @param value The value which is examined.
     * @param min   The minimum the value is allowed to go.
     * @param max   The maximum the value is allowed to go.
     * @return The maximum value of the passed min and the minimum of the passed on value and max.
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
