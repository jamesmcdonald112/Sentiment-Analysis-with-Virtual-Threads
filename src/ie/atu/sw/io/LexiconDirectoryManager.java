package ie.atu.sw.io;

import ie.atu.sw.utilities.*;

import java.io.*;

/**
 * LexiconDirectoryManager extends the functionality of AbstractDirectoryManager
 * for handling the directory paths specifically for lexicon files.
 */
public class LexiconDirectoryManager extends AbstractDirectoryManager {

    /**
     * Constructor for LexiconDirectoryManager.
     * Big-O Notation: O(1) - Initialisation of the class without any complex operations.
     */
    public LexiconDirectoryManager() {
        super();
    }

    /**
     * Retrieves the current directory path where lexicon files are located.
     * Big-O Notation: O(1) - This method simply returns a String, which is a constant-time
     * operation.
     *
     * @return The current directory path as a String.
     */
    @Override
    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }

    /**
     * Configures and sets a new directory path for lexicon files.
     * Allows users to update the directory path,
     * with a limited number of attempts.
     * Big-O Notation: O(n) - The complexity depends on the number of user attempts, capped by maxAttempts.
     * Each iteration involves input and path validation, O(1) operations.
     *
     * @return The newly set directory path, or null if maximum attempts are reached.
     */
    @Override
    public String configureDirectoryPath() {
        int attempts = 0;
        while (attempts < super.maxAttempts) {
            // Check if the user wants to keep the current path
            if (shouldKeepCurrentPath()) {
                return currentDirectoryPath;
            }

            String newPath = getNewDirectoryPathFromUser();
            if (validatePath(newPath)) {
                return newPath;
            }

            attempts++;
            displayRemainingAttempts(attempts);
            if (attempts >= super.maxAttempts) {
                ConsoleDisplay.displayErrorMessage("Maximum attempts reached. Returning to main menu.");
                return null;
            }
        }
        return null;
    }

    /**
     * Checks whether the provided path is a valid lexicon path.
     * A valid path can be a directory or a regular existing file.
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
        File fileOrDirectory = new File(path);
        return fileOrDirectory.exists();
    }

    /**
     * Resets the current directory path to null.
     * Big-O Notation: O(1) - This method performs a single assignment operation, which is a constant-time operation.
     */
    public void resetCurrentDirectoryPath() {
        super.currentDirectoryPath = null;
    }

    /**
     * Prompts the user to enter a new path for lexicon files.
     * Big-O Notation: O(1) - The method performs a single input operation and no complex processing.
     *
     * @return A string representing the new directory path entered by the user.
     */
    protected String getNewDirectoryPathFromUser() {
        return ConsoleInput.getUserInput("Enter the new lexicon file or directory path: ");
    }

    /**
     * Validates the given path and sets it as the current directory path if valid.
     * Displays a confirmation message if the path is valid or an error message if it is invalid.
     * Big-O Notation: O(1) - Involves a validation check and a simple assignment, both constant-time operations.
     *
     * @param path The path to be validated and set.
     * @return true if the path is valid and has been set, false otherwise.
     */
    private boolean validatePath(String path) {
        if (isValidLexiconPath(path)) {
            ConsoleDisplay.displayConfirmationMessage("Lexicon path set to: " + path);
            currentDirectoryPath = path;
            return true;
        }
        ConsoleDisplay.displayErrorMessage("Path does not exist. Please enter a valid path.");
        return false;
    }

    /**
     * Determines if the current directory path should be kept.
     * Asks the user if they want to keep the current lexicon directory if it's valid.
     * Big-O Notation: O(1) - This method involves a single check and a user input operation, both constant-time operations.
     *
     * @return true if the current path should be kept, false otherwise.
     */
    private boolean shouldKeepCurrentPath() {
        if (currentDirectoryPath != null && isValidLexiconPath(currentDirectoryPath)) {
            return ConsoleInput.getYesOrNo("Current lexicon directory is " + currentDirectoryPath + ". Keep this?");
        }
        return false;
    }

    /**
     * Displays the remaining number of attempts to the user.
     * Big-O Notation: O(1) - This method only involves displaying a message, which is a constant-time operation.
     *
     * @param attempts The current number of attempts made.
     */
    private void displayRemainingAttempts(int attempts) {
        ConsoleDisplay.displayErrorMessage("Attempts remaining: " + (super.maxAttempts - attempts));
    }
}
