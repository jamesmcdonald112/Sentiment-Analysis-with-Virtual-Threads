/**
 * @author James McDonald
 */

package ie.atu.sw.utilities;

/**
 * ConsoleDisplay provides methods for displaying different types of messages in the console.
 * It includes functionalities for displaying messages, prompts, error messages, and confirmation messages with color coding.
 */
public class ConsoleDisplay {

    /**
     * Displays a standard message in a yellow color.
     * Big-O Notation: O(1) - Displaying a message is a constant-time operation.
     *
     * @param message The message to be displayed.
     */
    public static void displayMessage(String message) {
        System.out.println(ConsoleColour.YELLOW_BRIGHT + message + ConsoleColour.RESET);
    }

    /**
     * Displays a prompt message in blue color.
     * Big-O Notation: O(1) - Displaying a prompt is a constant-time operation.
     *
     * @param message The prompt message to be displayed.
     */
    public static void displayPrompt(String message) {
        System.out.println(ConsoleColour.BLUE + message + ConsoleColour.RESET);
    }

    /**
     * Displays a message in a specified color.
     * Big-O Notation: O(1) - Displaying a colored message is a constant-time operation.
     *
     * @param message The message to be displayed.
     * @param color   The color in which the message will be displayed.
     */
    public static void displayColoredMessage(String message, ConsoleColour color) {
        System.out.println(color + message + ConsoleColour.RESET);
    }

    /**
     * Displays an error message in red color.
     * Big-O Notation: O(1) - Displaying an error message is a constant-time operation.
     *
     * @param message The error message to be displayed.
     */
    public static void displayErrorMessage(String message) {
        System.out.println(ConsoleColour.RED + message + ConsoleColour.RESET);
    }

    /**
     * Displays a confirmation message in green color.
     * Big-O Notation: O(1) - Displaying a confirmation message is a constant-time operation.
     *
     * @param message The confirmation message to be displayed.
     */
    public static void displayConfirmationMessage(String message) {
        System.out.println(ConsoleColour.GREEN + message + ConsoleColour.RESET);
    }
}
