package ie.atu.sw.processing;

import ie.atu.sw.utilities.ConsoleColour;
import ie.atu.sw.utilities.ConsoleDisplay;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * SentimentTweetProcessor extends TweetProcessor to analyse sentiment in tweets.
 * It uses a SentimentAnalyser to process each tweet and determine its sentiment.
 */
public class SentimentTweetProcessor extends TweetProcessor {
    private final SentimentAnalyser sentimentAnalyser;

    /**
     * Constructor for SentimentTweetProcessor.
     * Initialises the processor with a specific sentiment analyser.
     * Big-O Notation: O(1) - Initialisation with sentiment analyser is a constant-time operation.
     *
     * @param sentimentAnalyser The sentiment analyser to be used for tweet analysis.
     */
    public SentimentTweetProcessor(SentimentAnalyser sentimentAnalyser) {
        this.sentimentAnalyser = sentimentAnalyser;
    }

    /**
     * Processes a list of tweets to determine their sentiment.
     * Uses concurrent processing.
     * Big-O Notation: O(n) - Where n is the number of tweets. Each tweet is processed in parallel.
     *
     * @param tweets The list of tweets to be processed.
     * @return A list of formatted tweets with sentiment analysis results.
     */
    @Override
    public List<String> processTweets(List<String> tweets) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        ConcurrentHashMap<Integer, TweetResult> processedTweets = new ConcurrentHashMap<>();
        AtomicInteger index = new AtomicInteger(0);

        // Submit each tweet for processing in a separate thread
        tweets.forEach(tweet -> submitTweetForProcessing(tweet, processedTweets, index, executor));

        awaitTerminationAndHandleErrors(executor);
        displayProcessedTweets(processedTweets);

        // Collect processed tweets into a list
        return processedTweets.values().stream()
                .map(TweetResult::getFormattedTweet)
                .collect(Collectors.toList());
    }

    /**
     * Submits a single tweet for processing in a thread.
     * Stores the result in a concurrent map for ordered retrieval.
     * Big-O Notation: O(1) - Submitting a tweet for processing is a constant-time operation.
     *
     * @param tweet           The tweet to be processed.
     * @param processedTweets The concurrent map to store processed tweet results.
     * @param index           The atomic integer tracking the index/order of the tweets.
     * @param executor        The executor service for managing threads.
     */
    private void submitTweetForProcessing(String tweet, ConcurrentHashMap<Integer, TweetResult> processedTweets,
                                          AtomicInteger index, ExecutorService executor) {
        int currentIndex = index.getAndIncrement();
        executor.submit(() -> {
            TweetResult result = processSingleTweet(tweet);
            processedTweets.put(currentIndex, result);
        });
    }

    /**
     * Displays processed tweets if configured to show results in the console.
     * Orders the tweets according to their original input order.
     * Big-O Notation: O(n log n) - Sorting the entries by key before display. n is the number of tweets.
     *
     * @param processedTweets The concurrent map containing processed tweet results.
     */
    private void displayProcessedTweets(ConcurrentHashMap<Integer, TweetResult> processedTweets) {
        if (getShowResultsInConsole()) {
            processedTweets.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        TweetResult result = entry.getValue();
                        ConsoleDisplay.displayColoredMessage(result.getFormattedTweet(), result.getColor());
                    });
        }
    }

    /**
     * Awaits the termination of all tasks in the executor service.
     * Throws a RuntimeException if tasks do not finish within the expected time or if the thread is interrupted.
     * Big-O Notation: O(1) - Awaiting termination is a constant-time operation, not dependent on the number of tasks.
     *
     * @param executor The executor service managing the threads.
     */
    private void awaitTerminationAndHandleErrors(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                throw new RuntimeException("Tasks did not finish in expected time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted.", e);
        }
    }

    /**
     * Processes a single tweet to determine its sentiment.
     * Determines the color representation based on the sentiment and formats the tweet.
     * Big-O Notation: O(1) - Sentiment analysis and formatting for a single tweet is a constant-time operation.
     *
     * @param tweet The tweet to be processed.
     * @return A TweetResult object containing the formatted tweet and its associated color.
     */
    private TweetResult processSingleTweet(String tweet) {
        double sentimentScore = sentimentAnalyser.calculateSentimentScore(tweet);
        String sentiment = sentimentScore > 0 ? "Positive" : sentimentScore < 0 ? "Negative" : "Neutral";
        ConsoleColour color = determineColorBasedOnSentiment(sentiment);
        String formattedTweet = formatTweet(tweet, sentiment, sentimentScore);

        return new TweetResult(formattedTweet, color, sentimentScore);
    }

    /**
     * Determines the console color based on the sentiment of the tweet.
     * Big-O Notation: O(1) - Determining the color is a simple conditional operation, hence constant time.
     *
     * @param sentiment The sentiment of the tweet.
     * @return The console color associated with the sentiment.
     */
    private ConsoleColour determineColorBasedOnSentiment(String sentiment) {
        switch (sentiment) {
            case "Positive":
                return ConsoleColour.GREEN_BOLD;
            case "Negative":
                return ConsoleColour.RED_BOLD;
            default:
                return ConsoleColour.YELLOW_BOLD;
        }
    }

    /**
     * Formats the tweet for display, including its sentiment.
     * Big-O Notation: O(1) - Formatting a tweet is a constant-time operation.
     *
     * @param tweet     The tweet text.
     * @param sentiment The sentiment of the tweet.
     * @param score     The score of the tweet.
     * @return A formatted string representing the tweet and its sentiment.
     */
    private String formatTweet(String tweet, String sentiment, double score) {
        return "Tweet: " + tweet + "\nSentiment: " + sentiment + " " + score + "\n-----------------------------------";
    }

    /**
     * Inner class to hold and manage tweet processing results.
     */
    class TweetResult {
        private String formattedTweet;
        private ConsoleColour color;
        private double score;

        /**
         * Constructor for TweetResult.
         * Initialises the result with formatted tweet text and color.
         * Big-O Notation: O(1) - Initialisation of this class is a constant-time operation.
         *
         * @param formattedTweet The formatted tweet text.
         * @param color          The console color representing the sentiment of the tweet.
         * @param score          The scorer representing the sentiment of the tweet.
         */
        public TweetResult(String formattedTweet, ConsoleColour color, double score) {
            this.formattedTweet = formattedTweet;
            this.color = color;
            this.score = score;
        }

        /**
         * Gets the formatted tweet.
         * Big-O Notation: O(1) - Retrieval of a formatted tweet is a constant-time operation.
         *
         * @return The formatted tweet text.
         */
        public String getFormattedTweet() {
            return formattedTweet;
        }

        /**
         * Gets the console color representing the sentiment of the tweet.
         * Big-O Notation: O(1) - Retrieval of the color is a constant-time operation.
         *
         * @return The console color.
         */
        public ConsoleColour getColor() {
            return color;
        }
    }

}
