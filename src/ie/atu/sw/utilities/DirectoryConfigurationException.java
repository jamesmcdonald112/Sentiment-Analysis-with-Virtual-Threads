package ie.atu.sw.utilities;

/**
 * Custom exception class for handling directory configuration errors.
 * This exception is used to indicate problems with setting up or validating directory paths.
 */
public class DirectoryConfigurationException extends Exception {

    /**
     * Constructs a new DirectoryConfigurationException with the specified detail message.
     * The message provides more information about the directory configuration error that occurred.
     * This constructor is typically used when an error is encountered during the directory configuration process.
     *
     * @param message The detail message which provides more information about the error.
     */
    public DirectoryConfigurationException(String message) {
        super(message);
    }
}
