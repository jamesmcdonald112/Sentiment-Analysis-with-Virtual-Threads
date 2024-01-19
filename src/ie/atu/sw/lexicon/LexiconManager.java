package ie.atu.sw.lexicon;

import ie.atu.sw.utilities.ConsoleDisplay;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Manages the loading of lexicon data from a specified directory or file path.
 * This class provides functionality to load and process lexicon files for sentiment analysis.
 */
public class LexiconManager {

    /**
     * Loads lexicon data from a specified directory or file.
     * Parses the lexicon files and returns a map of word scores.
     * Big-O Notation: O(n) - Complexity depends on the number of words across all lexicon files.
     *
     * @param directoryOrFilePath Path to the directory or file containing lexicon data.
     * @return A Map with Integer keys (hashes of words) and Double values (scores) if successful, or an empty map if not.
     * @throws InterruptedException if the thread executing the method is interrupted.
     */
    public static Map<Integer, Double> loadLexicons(String directoryOrFilePath) throws InterruptedException {
        if (directoryOrFilePath == null) {
            ConsoleDisplay.displayErrorMessage("No valid lexicon directory path provided.");
            return Collections.emptyMap();
        }
        File directoryOrFile = new File(directoryOrFilePath);
        if (!isValidLexiconPath(directoryOrFile)) {
            ConsoleDisplay.displayErrorMessage("Invalid lexicon path: " + directoryOrFilePath);
            return Collections.emptyMap();
        }

        File[] lexiconFiles;
        if (directoryOrFile.isDirectory()) {
            lexiconFiles = getLexiconFiles(directoryOrFile);
        } else {
            lexiconFiles = new File[]{directoryOrFile}; // If it's a file, wrap it in an array
        }

        if (lexiconFiles.length == 0) {
            ConsoleDisplay.displayErrorMessage("No lexicon files found in the directory: " + directoryOrFilePath);
            return Collections.emptyMap();
        }

        return processLexiconFiles(lexiconFiles);
    }

    /**
     * Validates whether the provided path is a valid lexicon path.
     * The path must be either an existing directory or a file.
     * Big-O Notation: O(1) - Involves checking if a path exists and whether it is a file or directory.
     *
     * @param fileOrDirectory The file or directory to validate.
     * @return true if the path is valid, false otherwise.
     */
    private static boolean isValidLexiconPath(File fileOrDirectory) {
        if (!fileOrDirectory.exists()) {
            System.out.println("Provided path does not exist: " + fileOrDirectory.getAbsolutePath());
            return false;
        }
        if (!fileOrDirectory.isDirectory() && !fileOrDirectory.isFile()) {
            System.out.println("Provided path is neither a directory nor a valid file: " + fileOrDirectory.getAbsolutePath());
            return false;
        }
        return true;
    }

    /**
     * Retrieves all lexicon files from the given directory.
     * Only files with a ".txt" extension are considered lexicon files.
     * Big-O Notation: O(n) - Where n is the number of files in the directory.
     *
     * @param directory The directory containing lexicon files.
     * @return An array of File objects representing the lexicon files.
     */
    private static File[] getLexiconFiles(File directory) {
        return directory.listFiles((dir, name) -> name.endsWith(".txt"));
    }

    /**
     * Processes multiple lexicon files and combines their contents into a single map.
     * This method uses concurrent processing for efficiency.
     * Big-O Notation: O(n) - Where n is the total number of words across all files. Parallel processing improves efficiency.
     *
     * @param lexiconFiles Array of lexicon file objects.
     * @return A map combining all lexicon data from the files, keyed by word hash with corresponding scores.
     * @throws InterruptedException if the thread executing the method is interrupted.
     */
    private static Map<Integer, Double> processLexiconFiles(File[] lexiconFiles) throws InterruptedException {
        Map<Integer, Double> combinedLexicon = new ConcurrentSkipListMap<>();
        List<String> errorMessages = new ArrayList<>();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        for (File file : lexiconFiles) {
            executor.submit(() -> loadFileToLexicon(file, combinedLexicon, errorMessages));
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);

        printErrorMessages(errorMessages);
        return combinedLexicon;
    }

    /**
     * Loads lexicon data from a single file into the combined lexicon map.
     * This method is designed to be used concurrently.
     * Big-O Notation: O(n) - Where n is the number of words in the file.
     *
     * @param file            The lexicon file to be processed.
     * @param combinedLexicon The map to which the lexicon data will be added.
     * @param errorMessages   A list to record any error messages encountered during processing.
     */
    private static void loadFileToLexicon(File file, Map<Integer, Double> combinedLexicon, List<String> errorMessages) {
        try {
            Map<Integer, Double> fileLexicon = LexiconParser.parseFile(file.getPath());
            synchronized (combinedLexicon) {
                fileLexicon.forEach((key, value) -> combinedLexicon.merge(key, value, Double::sum));
            }
        } catch (IOException e) {
            synchronized (errorMessages) {
                errorMessages.add("Error loading lexicon: " + e.getMessage() + " File: " + file.getName());
            }
        }
    }

    /**
     * Prints all error messages collected during the lexicon loading process.
     * This can be used to log or display errors encountered.
     * Big-O Notation: O(n) - Where n is the number of error messages. Each message is printed in
     * constant time.
     *
     * @param errorMessages A list of error messages to be printed.
     */
    private static void printErrorMessages(List<String> errorMessages) {
        for (String errorMsg : errorMessages) {
            System.out.println(errorMsg);
        }
    }
}
