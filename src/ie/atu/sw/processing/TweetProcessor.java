package ie.atu.sw.processing;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Abstract class TweetProcessor provides a template for reading and processing tweets.
 * This class includes methods for reading tweets from files and directories, and abstracts the process of tweet analysis.
 */
public abstract class TweetProcessor {
    private boolean showResultsInConsole;

    /**
     * Abstract method to process a list of tweets.
     * This method should be implemented by subclasses to define specific tweet processing behavior.
     *
     * @param tweets The list of tweets to be processed.
     * @return A list of processed tweet data.
     */
    public abstract List<String> processTweets(List<String> tweets);

    /**
     * Sets whether the results of tweet processing should be shown in the console.
     * Big-O Notation: O(1) - Setting a boolean value is a constant-time operation.
     *
     * @param show True if results should be shown in the console, false otherwise.
     */
    public void setShowResultsInConsole(boolean show) {
        this.showResultsInConsole = show;
    }

    /**
     * Reads tweets from a specified directory.
     * This method handles both individual files and directories containing multiple files.
     * Big-O Notation: O(n) - Where n is the number of tweets. The complexity depends on the number of files and tweets.
     *
     * @param directoryPath The path to the file or directory containing tweets.
     * @return A list of tweets read from the specified path.
     */
    public List<String> readTweetsFromDirectory(String directoryPath) {
        File path = new File(directoryPath);

        if (path.isFile()) {
            return readTweetsFromFile(path);
        } else if (path.isDirectory()) {
            return readTweetsFromAllFilesInDirectory(path);
        } else {
            System.out.println(directoryPath + " is not a valid file or directory.");
            return Collections.emptyList();
        }
    }

    /**
     * Gets the current configuration for displaying results in the console.
     * Big-O Notation: O(1) - Retrieving a boolean value is a constant-time operation.
     *
     * @return True if results should be shown in the console, false otherwise.
     */
    protected boolean getShowResultsInConsole() {
        return this.showResultsInConsole;
    }

    /**
     * Reads tweets from all files in a specified directory.
     * Processes each file in the directory concurrently to improve performance.
     * Big-O Notation: O(n) - Where n is the number of tweets. Parallel processing of files can improve efficiency.
     *
     * @param directory The directory containing tweet files.
     * @return A list of all tweets read from the files in the directory.
     */
    private List<String> readTweetsFromAllFilesInDirectory(File directory) {
        File[] files = directory.listFiles(File::isFile);
        if (files == null) {
            System.out.println("No files found in the directory.");
            return Collections.emptyList();
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<String>>> futures = new ArrayList<>();

        for (File file : files) {
            Future<List<String>> future = executor.submit(() -> readTweetsFromFile(file));
            futures.add(future);
        }
        executor.shutdown();

        return collectTweetsFromFutures(futures);
    }

    /**
     * Collects tweets from the Future objects returned by the ExecutorService.
     * Waits for all concurrent tasks to complete and combines their results.
     * Big-O Notation: O(n) - Collecting and combining results from futures, where n is the total number of tweets.
     *
     * @param futures The list of Future objects representing pending results from concurrent tasks.
     * @return A combined list of all tweets from the futures.
     */
    private List<String> collectTweetsFromFutures(List<Future<List<String>>> futures) {
        List<String> allTweets = new ArrayList<>();
        for (Future<List<String>> future : futures) {
            try {
                allTweets.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return allTweets;
    }

    /**
     * Reads tweets from a single file.
     * Each line in the file is treated as a separate tweet.
     * Big-O Notation: O(n) - Where n is the number of lines (tweets) in the file.
     *
     * @param file The file to read tweets from.
     * @return A list of tweets read from the file.
     */
    private List<String> readTweetsFromFile(File file) {
        List<String> tweets = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                tweets.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }
}
