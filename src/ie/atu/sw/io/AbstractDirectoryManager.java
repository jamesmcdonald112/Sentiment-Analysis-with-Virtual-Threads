package ie.atu.sw.io;

import ie.atu.sw.utilities.*;

/**
 * This abstract class provides a template for managing directory paths.
 * It includes methods for getting, configuring, and resetting the current directory path,
 * as well as validating and obtaining a new directory path from the user.
 */
public abstract class AbstractDirectoryManager implements IDirectoryManager {
    // The current directory path.
    protected String currentDirectoryPath;
    // The maximum number of attempts allowed for user input.
    protected final int maxAttempts = 3;

    public abstract String getCurrentDirectoryPath();

    /**
     * Configures and sets a new directory path.
     * This method must be implemented to define how the directory path is set.
     *
     * @return A string representing the new directory path.
     * @throws DirectoryConfigurationException if the directory configuration fails.
     */
    public abstract String configureDirectoryPath() throws DirectoryConfigurationException;

    /**
     * Resets the current directory path to null.
     * This can be used when the directory path is no longer valid or needs reconfiguration.
     * Big-O Notation: O(1) - This method performs a single assignment operation, which is a constant-time operation.
     */
    public void resetCurrentDirectoryPath() {
        this.currentDirectoryPath = null;
    }

    /**
     * Checks whether a given path is valid for a lexicon file.
     *
     * @param path The path to be validated.
     * @return true if the path is valid, false otherwise.
     */
    protected abstract boolean isValidLexiconPath(String path);

    /**
     * Repeatedly prompts the user until a valid path is entered or the input is empty.
     * <p>
     * Big-O Notation: O(n) - The complexity depends on the number of times the user attempts to input a valid path.
     * Each iteration involves a call to getUserInput() and isValidLexiconPath(), which are O(1) operations.
     *
     * @return A string representing the new directory path entered by the user.
     */
    protected String getNewDirectoryPathFromUser() {
        String newPath;
        do {
            // Prompting the user for a new directory path
            newPath = ConsoleInput.getUserInput("Enter the new directory path: ");
            if (newPath.trim().isEmpty()) {
                ConsoleDisplay.displayErrorMessage("The directory path cannot be empty. Please enter a valid path.");
            }
        } while (!isValidLexiconPath(newPath) && !newPath.trim().isEmpty());
        return newPath;
    }
}
