package com.oop.chess.model.config;

import java.io.FileInputStream;
import java.util.Properties;

import static java.lang.System.exit;

public class Configuration {
    static Properties properties = new Properties();

    public static boolean enableChatGptIntegration;
    public static String chatGptApiUrl;
    public static String chatGptApiKey;
    public static String chatGptApiModel;
    public static boolean enableVisualSearch;
    public static double lambda;
    public static double alpha;
    public static double beta;
    public static double alphaDecreaseRate;
    public static double maxAlpha;
    public static double minAlpha;


    public static void loadConfiguration() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties");
            properties.load(fileInputStream);

            enableChatGptIntegration = Boolean.parseBoolean(properties.getProperty("chat-gpt.api.enable"));
            chatGptApiUrl = properties.getProperty("chat-gpt.api.url");
            chatGptApiKey = properties.getProperty("chat-gpt.api.key");
            chatGptApiModel = properties.getProperty("chat-gpt.api.model");
            enableVisualSearch = Boolean.parseBoolean(properties.getProperty("application.visual.search"));
            lambda = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.lambda"));
            alpha = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.alpha"));
            beta = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.beta"));
            alphaDecreaseRate = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.alpha-decrease-rate"));
            maxAlpha = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.max-alpha"));
            minAlpha = Double.parseDouble(properties.getProperty("application.temporal.different.leaf.min-alpha"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Most likely you didn't create an src/main/resources/application.properties file or " +
                "not all fields were specified. Follow the documentation instructions to create the configuration.");
            exit(1);
        }
    }
}
