package ie.atu.sw.io;

import ie.atu.sw.utilities.DirectoryConfigurationException;

/**
 * Interface for managing directory paths within the application.
 * It defines the contract for classes that handle operations related to
 * managing and configuring directory paths.
 */
public interface IDirectoryManager {

    /**
     * Retrieves the current directory path.
     * This method should return the currently selected path.
     *
     * @return A string representing the current directory path.
     */
    String getCurrentDirectoryPath();

    /**
     * Configures and sets a new directory path.
     * This method is responsible for defining the logic to set up a new directory path.
     *
     * @return A string representing the newly configured directory path.
     * @throws DirectoryConfigurationException if there is an issue in setting up the directory path.
     */
    String configureDirectoryPath() throws DirectoryConfigurationException;
}

