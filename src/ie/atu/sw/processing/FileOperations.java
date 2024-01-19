package ie.atu.sw.processing;

import ie.atu.sw.utilities.ConsoleDisplay;

import java.io.*;
import java.util.*;

/**
 * FileOperations class contains static methods for performing file-related operations,
 * such as writing results to a file.
 */
public class FileOperations {

    /**
     * Writes a list of data to a file located in the specified directory.
     * Each element in the list is written as a separate line in the file.
     * Big-O Notation: O(n) - Where n is the number of elements in the data list. Each element is written sequentially.
     *
     * @param directoryPath The path to the directory where the file will be created.
     * @param data          The list of strings to be written to the file.
     */
    public static void outputResultsToFile(String directoryPath, List<String> data) {
        File outputFile = new File(directoryPath, "output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            ConsoleDisplay.displayConfirmationMessage("Results written to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            ConsoleDisplay.displayErrorMessage("Error writing to file: " + e.getMessage());
        }
    }

}
