package ie.atu.sw.lexicon;

import ie.atu.sw.processing.SentimentAnalyser;

import java.math.*;
import java.util.Map;

/**
 * LexiconSentimentAnalyser is an implementation of the SentimentAnalyser interface.
 * It uses a pre-loaded lexicon to analyse the sentiment of text, such as tweets.
 */
public class LexiconSentimentAnalyser implements SentimentAnalyser {
    // A map containing word hashes and their corresponding sentiment scores
    private final Map<Integer, Double> lexicon;

    /**
     * Constructor for LexiconSentimentAnalyser.
     * Initialises the class with a pre-loaded lexicon.
     * Big-O Notation: O(1) - Initialisation of the class with the lexicon map is a constant-time
     * operation.
     *
     * @param lexicon The lexicon map with integer hashes of words and their double sentiment scores.
     */
    public LexiconSentimentAnalyser(Map<Integer, Double> lexicon) {
        this.lexicon = lexicon;
    }

    /**
     * Analyses the sentiment of a given tweet based on the lexicon.
     * Splits the tweet into words, processes each word, and sums up their sentiment scores.
     * Big-O Notation: O(n) - Where n is the number of words in the tweet. Each word is processed and looked up in the lexicon.
     *
     * @param tweet The tweet text to analyze.
     * @return A string indicating the overall sentiment of the tweet ("Positive", "Negative", or "Neutral").
     */
    @Override
    public String analyseSentiment(String tweet) {
        double sentimentScore = 0;
        for (String word : tweet.split("\\s+")) {
            // Process each word in the tweet and compute its sentiment score
            String processedWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            int wordHash = processedWord.hashCode(); // Get hash code of the word
            sentimentScore += lexicon.getOrDefault(wordHash, 0.0); // Use hash code for lookup
        }
        // Determine the overall sentiment based on the aggregated score
        return sentimentScore > 0 ? "Positive" : sentimentScore < 0 ? "Negative" : "Neutral";
    }

    /**
     * Calculates the sentiment score of a given tweet.
     * The score is computed by summing the sentiment scores of individual words in the tweet,
     * based on a pre-loaded lexicon. Words not found in the lexicon contribute a score of zero.
     * This implementation includes rounding to mitigate floating-point precision errors.
     * <p>
     * Big-O Notation: O(m * k) - Where m is the number of words in the tweet, and k is the time complexity
     * of lexicon lookup for each word.
     *
     * @param tweet The tweet text to be analyzed for sentiment.
     * @return The cumulative sentiment score of the tweet.
     */
    @Override
    public double calculateSentimentScore(String tweet) {
        double score = 0;
        for (String word : tweet.split("\\s+")) {
            String processedWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            int wordHash = processedWord.hashCode();
            score += lexicon.getOrDefault(wordHash, 0.0);
        }
        return BigDecimal.valueOf(score)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
