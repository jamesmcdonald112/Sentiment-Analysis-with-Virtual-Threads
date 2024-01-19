package ie.atu.sw.processing;

import ie.atu.sw.io.InputDirectoryManager;
import ie.atu.sw.io.OutputDirectoryManager;
import ie.atu.sw.lexicon.LexiconSentimentAnalyser;
import ie.atu.sw.utilities.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * SentimentAnalysisManager is responsible for orchestrating the sentiment analysis process.
 * It utilises various components to read, analyse, and output sentiment analysis results.
 */
public class SentimentAnalysisManager {

    private final InputDirectoryManager fileInputDirectoryManager;
    private final OutputDirectoryManager outputDirectoryManager;
    private final Map<Integer, Double> combinedLexicon;

    /**
     * Constructor for SentimentAnalysisManager.
     * Initialises the manager with directory managers and a lexicon map.
     * Big-O Notation: O(1) - Initialisation of the class with provided parameters is a
     * constant-time operation.
     *
     * @param fileInputDirectoryManager Manager for input directory containing tweets.
     * @param outputDirectoryManager    Manager for output directory where results will be stored.
     * @param combinedLexicon           A map containing word hashes and their associated sentiment scores.
     */
    public SentimentAnalysisManager(InputDirectoryManager fileInputDirectoryManager,
                                    OutputDirectoryManager outputDirectoryManager,
                                    Map<Integer, Double> combinedLexicon) {
        this.fileInputDirectoryManager = fileInputDirectoryManager;
        this.outputDirectoryManager = outputDirectoryManager;
        this.combinedLexicon = combinedLexicon;
    }

    /**
     * Executes the sentiment analysis process.
     * Reads tweets, analyses their sentiment, and outputs the results.
     * Big-O Notation: O(n) - The complexity depends on the number of tweets being processed and the complexity of the sentiment analysis.
     *
     * @param showInConsole Indicates whether the results should be displayed in the console.
     * @throws IOException If an I/O error occurs during processing.
     */
    public void executeAnalyseAndReport(boolean showInConsole) {
        SentimentAnalyser sentimentAnalyser = new LexiconSentimentAnalyser(combinedLexicon);
        SentimentTweetProcessor tweetProcessor = new SentimentTweetProcessor(sentimentAnalyser);
        tweetProcessor.setShowResultsInConsole(showInConsole);

        List<String> tweets = tweetProcessor.readTweetsFromDirectory(fileInputDirectoryManager.getCurrentDirectoryPath());
        List<String> formattedTweets = tweetProcessor.processTweets(tweets);

        FileOperations.outputResultsToFile(outputDirectoryManager.getCurrentDirectoryPath(), formattedTweets);

        ConsoleDisplay.displayConfirmationMessage("Sentiment analysis completed.");
    }
}
