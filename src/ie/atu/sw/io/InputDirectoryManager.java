package ie.atu.sw.io;

import ie.atu.sw.utilities.*;

import java.nio.file.*;

/**
 * This class extends AbstractDirectoryManager to manage input directories,
 * particularly for handling the configuration and retrieval of directory paths.
 */
public class InputDirectoryManager extends AbstractDirectoryManager {

    /**
     * Retrieves the current directory path where input files are located.
     * Big-O Notation: O(1) - This method simply returns a string, which is a constant-time
     * operation.
     *
     * @return The current directory path as a String.
     */
    @Override
    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }

    /**
     * Configures and sets a new directory path for input files.
     * This method allows users to set or update the directory path through user input,
     * with a maximum number of attempts to get a valid path.
     * Big-O Notation: O(n) - The complexity depends on the number of user attempts, capped by maxAttempts.
     * Each iteration involves input and path validation, which are O(1) operations.
     *
     * @return wew directory path.
     * @throws DirectoryConfigurationException if the path configuration fails after the maximum number of attempts.
     */
    @Override
    public String configureDirectoryPath() throws DirectoryConfigurationException {
        int attempt = 0; // Initialise the attempt counter
        while (attempt < maxAttempts) {
            if (currentDirectoryPath != null) {
                boolean keepCurrent = ConsoleInput.getYesOrNo("Keep current path: " + currentDirectoryPath + "?");
                if (keepCurrent) {
                    return currentDirectoryPath; // Return the current path if user chooses to keep it
                } else {
                    currentDirectoryPath = null; // Reset the path if user decides to change it
                }
            }

            // Get a new directory path from the user
            String newPath = getNewDirectoryPathFromUser();
            if (validateAndSetPath(newPath)) {
                return currentDirectoryPath; // Return the new path if it's valid
            } else {
                attempt++; // Increment attempt counter if path is not valid
                if (attempt < maxAttempts) {
                    ConsoleDisplay.displayErrorMessage("Attempts remaining: " + (maxAttempts - attempt));
                }
            }
        }
        throw new DirectoryConfigurationException("Failed to configure a valid input directory after " + maxAttempts + " attempts.");
    }

    /**
     * Checks whether the provided path is a valid lexicon path.
     * A valid path can be either a directory or a regular file that exists.
     * Big-O Notation: O(1) - Basic file system checks with constant-time complexity.
     *
     * @param path The path to be validated.
     * @return true if the path is valid, false otherwise.
     */
    @Override
    protected boolean isValidLexiconPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        try {
            // Resolve the path and check if it exists and is a directory or a regular file
            Path resolvedPath = Path.of(path);
            return Files.exists(resolvedPath) && (Files.isDirectory(resolvedPath) || Files.isRegularFile(resolvedPath));
        } catch (Exception e) {
            // Return false if any exception occurs during path resolution or checking
            return false;
        }
    }

    /**
     * Gets a new directory path from the user.
     * It validates the user input and ensures it is a valid path before returning it.
     * If the path is invalid, an error message is displayed, and null is returned.
     * Big-O Notation: O(1) - The method performs a single input operation and a validation check.
     *
     * @return A valid directory path entered by the user, or null if the input is invalid.
     */
    @Override
    protected String getNewDirectoryPathFromUser() {
        String newPath = ConsoleInput.getUserInput("Enter the new input path (directory or file): ");
        if (isValidLexiconPath(newPath)) {
            return newPath;
        } else {
            ConsoleDisplay.displayErrorMessage("Invalid path or the path does not exist. Please enter a valid path.");
            return null;
        }
    }

    /**
     * Validates the given path and sets it as the current directory path if valid.
     * If the path is valid, a confirmation message is displayed.
     * Big-O Notation: O(1) - Involves a validation check and a simple assignment, both constant-time operations.
     *
     * @param path The path to be validated and set.
     * @return true if the path is valid and has been set, false otherwise.
     */
    private boolean validateAndSetPath(String path) {
        if (isValidLexiconPath(path)) {
            currentDirectoryPath = path;
            ConsoleDisplay.displayConfirmationMessage("Path set to: " + path);
            return true;
        }
        return false;
    }
}
