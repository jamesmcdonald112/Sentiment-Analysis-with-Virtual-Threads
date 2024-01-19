package ie.atu.sw.lexicon;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * LexiconParser is responsible for parsing lexicon files.
 * It reads lexicon data from a file and converts it into a map of word hashes to sentiment scores.
 */
public class LexiconParser {

    /**
     * Parses a lexicon file and returns its contents as a map.
     * Each line in the file is processed to extract the word hash and its associated sentiment score.
     * Big-O Notation: O(n) - Where n is the number of lines in the file.
     *
     * @param filePath The path to the lexicon file.
     * @return A map of integer word hashes to their double sentiment scores.
     * @throws IOException if there's an error reading the file.
     */
    public static Map<Integer, Double> parseFile(String filePath) throws IOException {
        Map<Integer, Double> lexicon = new ConcurrentSkipListMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, lexicon, filePath);
            }
        }
        return lexicon;
    }

    /**
     * Processes a single line from the lexicon file.
     * Splits the line into word and score parts and adds them to the lexicon map.
     * Big-O Notation: O(1) - Splitting and parsing of a line is a constant-time operation.
     *
     * @param line     The line to process.
     * @param lexicon  The map to add the word hash and sentiment score.
     * @param filePath The file path of the lexicon is used for error reporting.
     * @throws IOException if there's an error parsing the line.
     */
    private static void processLine(String line, Map<Integer, Double> lexicon, String filePath) throws IOException {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            int wordHash = parseWordHash(parts[0], filePath, line);
            double sentimentScore = parseSentimentScore(parts[1], filePath, line);
            lexicon.put(wordHash, sentimentScore);
        }
    }

    /**
     * Parses the word part of a line to get its hash.
     * Big-O Notation: O(1) - Hashing a string is a constant-time operation.
     *
     * @param wordPart The word part of the line.
     * @param filePath The file path of the lexicon is used for error reporting.
     * @param line     The line being processed.
     * @return The hash code of the word part.
     * @throws IOException if the word part cannot be parsed.
     */
    private static int parseWordHash(String wordPart, String filePath, String line) throws IOException {
        try {
            return wordPart.trim().hashCode();
        } catch (NumberFormatException e) {
            throw new IOException("Invalid word hash in file: " + filePath + " Line: " + line, e);
        }
    }

    /**
     * Parses the score part of a line to get the sentiment score.
     * Big-O Notation: O(1) - Parsing a double value from a string is a constant-time operation.
     *
     * @param scorePart The score part of the line.
     * @param filePath  The file path of the lexicon, used for error reporting.
     * @param line      The line being processed.
     * @return The parsed sentiment score.
     * @throws IOException if the score part cannot be parsed.
     */
    private static double parseSentimentScore(String scorePart, String filePath, String line) throws IOException {
        try {
            return Double.parseDouble(scorePart.trim());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid sentiment score in file: " + filePath + " Line: " + line, e);
        }
    }
}
