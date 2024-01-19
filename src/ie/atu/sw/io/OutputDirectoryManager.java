package ie.atu.sw.io;

import ie.atu.sw.utilities.*;

import java.io.File;

/**
 * Manages directory paths for output, handling the
 * configuration and retrieval of paths where output files are to be stored.
 */
public class OutputDirectoryManager extends AbstractDirectoryManager {

    // Default directory name for output
    private static final String DEFAULT_DIRECTORY = "output";

    /**
     * Constructor for OutputDirectoryManager.
     * Initialises the current directory path to a default value and creates the directory if
     * needed.
     * Big-O Notation: O(1) - Initialisation and directory creation are constant-time operations.
     */
    public OutputDirectoryManager() {
        this.currentDirectoryPath = DEFAULT_DIRECTORY;
        createDefaultDirectoryIfNeeded();
    }

    /**
     * Configures and sets a new directory path for output files.
     * Allows users to keep the default directory or specify a new one.
     * Big-O Notation: O(n) - The complexity depends on the user input process. Directory validation is O(1).
     *
     * @return The directory path selected or confirmed by the user.
     * @throws DirectoryConfigurationException if path configuration fails.
     */
    @Override
    public String configureDirectoryPath() throws DirectoryConfigurationException {
        boolean keepCurrent = ConsoleInput.getYesOrNo("The default directory is '" + currentDirectoryPath + "'. Would you like to keep this?");
        if (keepCurrent) {
            createDefaultDirectoryIfNeeded();  // Create default directory if it doesn't exist
        } else {
            currentDirectoryPath = getValidDirectoryPathFromUser();  // Get new path from user
        }
        return currentDirectoryPath;
    }

    /**
     * Retrieves the current directory path where output files are stored.
     * Big-O Notation: O(1) - This method returns a member variable, which is a constant-time operation.
     *
     * @return The current directory path as a String.
     */
    @Override
    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }

    /**
     * Checks whether the provided path is a valid directory path for storing lexicons.
     * A valid path is a writable directory.
     * Big-O Notation: O(1) - Basic file system checks with constant-time complexity.
     *
     * @param path The path to be validated.
     * @return true if the path is a valid directory and writable, false otherwise.
     */
    @Override
    protected boolean isValidLexiconPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        File directory = new File(path);
        return directory.isDirectory() && directory.canWrite();
    }

    /**
     * Gets a new directory path from the user.
     * Ensures the entered path is not empty.
     * Big-O Notation: O(n) - The complexity depends on the number of times the user inputs a path.
     * Loop continues until a valid path is provided.
     *
     * @return A valid directory path entered by the user.
     */
    @Override
    protected String getNewDirectoryPathFromUser() {
        String newPath;
        do {
            newPath = ConsoleInput.getUserInput("Enter the new directory path: ");
            if (newPath != null && newPath.trim().isEmpty()) {
                ConsoleDisplay.displayErrorMessage("The directory path cannot be empty. Please enter a valid path.");
            }
        } while (newPath == null || newPath.trim().isEmpty());
        return newPath;
    }

    /**
     * Creates the default directory if it does not exist.
     * This method is called during initialisation to ensure the default directory is available.
     * Big-O Notation: O(1) - Involves checking if a directory exists and potentially creating it, which are constant-time operations.
     */
    private void createDefaultDirectoryIfNeeded() {
        File directory = new File(currentDirectoryPath);
        if (!directory.exists()) {
            createDirectory(directory);
        }
    }

    /**
     * Gets a valid directory path from the user.
     * This method prompts the user to enter and validate a new path.
     * Big-O Notation: O(n) - The complexity is determined by the number of attempts (up to maxAttempts) the user makes to input a valid path.
     *
     * @return A valid directory path entered by the user.
     * @throws DirectoryConfigurationException if the user fails to provide a valid path after several attempts.
     */
    private String getValidDirectoryPathFromUser() throws DirectoryConfigurationException {
        int attempt = 0;
        while (attempt < maxAttempts) {
            String newPath = ConsoleInput.getUserInput("Enter the new directory path: ");
            File directory = new File(newPath);
            if (!directory.exists()) {
                // Prompt the user to create a directory if it does not exist
                if (promptToCreateDirectory(directory)) {
                    return newPath;  // Directory created, return new path
                }
            } else {
                return newPath;  // Directory exists, return path
            }
            attempt++;
            ConsoleDisplay.displayErrorMessage("Invalid path. Attempts remaining: " + (maxAttempts - attempt));
            if (attempt >= maxAttempts) {
                throw new DirectoryConfigurationException("Failed to configure a valid output directory after " + maxAttempts + " attempts.");
            }
        }
        return currentDirectoryPath;
    }

    /**
     * Creates a new directory at the specified path.
     * Big-O Notation: O(1) - The method performs directory creation, a constant-time operation.
     *
     * @param directory The File object representing the directory to be created.
     * @return true if the directory was created successfully, false otherwise.
     */
    private boolean createDirectory(File directory) {
        if (directory.mkdirs()) {
            ConsoleDisplay.displayMessage("Directory created successfully.");
            return true;
        } else {
            ConsoleDisplay.displayErrorMessage("Failed to create directory.");
            return false;
        }
    }

    /**
     * Prompts the user to create a directory if it does not exist.
     * Big-O Notation: O(1) - Involves a user prompt and potentially creating a directory, which are constant-time operations.
     *
     * @param directory The File object representing the directory to be created.
     * @return true if the user agrees to create the directory and it is created successfully; false otherwise.
     */
    private boolean promptToCreateDirectory(File directory) {
        if (ConsoleInput.getYesOrNo("Directory does not exist. Create it?")) {
            return createDirectory(directory);
        } else {
            ConsoleDisplay.displayMessage("Directory creation was cancelled by the user.");
            return false;
        }
    }
}
