package ie.atu.sw.utilities;

import ie.atu.sw.io.InputDirectoryManager;
import ie.atu.sw.io.OutputDirectoryManager;
import ie.atu.sw.io.LexiconDirectoryManager;

import java.util.Map;

/**
 * ConfigurationValidator provides methods to validate configurations for sentiment analysis.
 * This includes checks for directory paths and lexicon configurations.
 */
public class ConfigurationValidator {

    /**
     * Checks if the necessary configurations for sentiment analysis are correctly set.
     * Validates the settings for input directory, output directory, and lexicons.
     * Big-O Notation: O(1) - The method performs a series of checks, each of which is a constant-time operation.
     *
     * @param inputDirManager  Manager for the input directory configuration.
     * @param outputDirManager Manager for the output directory configuration.
     * @param combinedLexicon  A map containing the combined lexicons.
     * @return A string containing error messages if any configurations are missing or invalid; otherwise, an empty string.
     */
    public static String checkSentimentAnalysisConfigurations(
            InputDirectoryManager inputDirManager,
            OutputDirectoryManager outputDirManager,
            Map<Integer, Double> combinedLexicon) {

        StringBuilder errorMessageBuilder = new StringBuilder();

        if (inputDirManager.getCurrentDirectoryPath() == null) {
            errorMessageBuilder.append("Input directory not set. Please set input directory first.\n");
        }

        if (outputDirManager.getCurrentDirectoryPath() == null) {
            errorMessageBuilder.append("Output directory not set. Please set output directory first.\n");
        }

        if (combinedLexicon == null || combinedLexicon.isEmpty()) {
            errorMessageBuilder.append("Lexicons not loaded. Please load lexicons first.\n");
        }
        // Return accumulated error messages, if any
        return errorMessageBuilder.toString();
    }
}
