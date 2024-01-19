package ie.atu.sw.processing;

/**
 * SentimentAnalyser interface contains methods for performing sentiment analysis.
 * It defines the contract for sentiment analysis implementations.
 */
public interface SentimentAnalyser {

    /**
     * Analyses the sentiment of a given tweet.
     * Implementations of this method should process the tweet text and determine its sentiment.
     * The sentiment can be classified as positive, negative, or neutral.
     *
     * @param tweet The tweet to be analysed.
     * @return A String representing the sentiment of the tweet.
     */
    String analyseSentiment(String tweet);

    double calculateSentimentScore(String tweet);
}
