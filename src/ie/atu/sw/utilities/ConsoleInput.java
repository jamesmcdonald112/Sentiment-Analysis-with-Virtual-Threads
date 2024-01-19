/**
 * @author James McDonald
 */

package ie.atu.sw.utilities;

import java.util.*;

/**
 * ConsoleInput handles user input from the console.
 * It provides methods to get user input of various types.
 */
public class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Gets user input as a string.
     * Big-O Notation: O(1) - Getting user input from the console is a constant-time operation.
     *
     * @param prompt The message to display before waiting for input.
     * @return The user input as a string. Returns an empty string if no input is found.
     */
    public static String getUserInput(String prompt) {
        try {
            ConsoleDisplay.displayMessage(prompt);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            ConsoleDisplay.displayErrorMessage("No line was found.");
        }
        return ""; // Default return if an exception occurs
    }

    /**
     * Gets user input as an integer within a specified range.
     * Big-O Notation: O(n) - The complexity depends on the number of times the user inputs until a valid integer is entered.
     *
     * @param prompt The message to display before waiting for input.
     * @param max    The maximum valid value for the input.
     * @return The user input as an integer.
     */
    public static int getUserInputInt(String prompt, int max) {
        int input;
        while (true) {
            ConsoleDisplay.displayPrompt(prompt);
            while (!scanner.hasNextInt()) {
                scanner.next(); // Discard incorrect input
                ConsoleDisplay.displayErrorMessage("Invalid input. Please enter an integer:");
            }
            input = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            if (input >= 1 && input <= max) {
                return input;
            } else {
                ConsoleDisplay.displayErrorMessage("Please enter a number between 1 and " + max + ":");
            }
        }
    }

    /**
     * Waits for the user to press 'y' before continuing.
     * Big-O Notation: O(n) - The complexity depends on the number of times the user inputs until 'y' is entered.
     */
    public static void promptContinue() {
        ConsoleDisplay.displayPrompt("Press 'y' to continue...");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                break;
            } else {
                ConsoleDisplay.displayErrorMessage("Invalid input. Press 'y' to continue...");
            }
        }
    }

    /**
     * Gets a boolean choice from the user.
     * Big-O Notation: O(n) - The complexity depends on the number of times the user inputs until a valid response ('y' or 'n') is entered.
     *
     * @param prompt The message to display before waiting for input.
     * @return true if the user inputs 'y' or 'yes', false if 'n' or 'no'.
     */
    public static boolean getYesOrNo(String prompt) {
        String input;
        while (true) {
            ConsoleDisplay.displayColoredMessage(prompt + " (y/n)", ConsoleColour.CYAN);
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                return false;
            } else {
                ConsoleDisplay.displayPrompt("Invalid input. Please enter 'y' or 'n':");
            }
        }
    }

    /**
     * Closes the scanner.
     * Big-O Notation: O(1) - Closing the scanner is a constant-time operation.
     */
    public static void closeScanner() {
        try {
            scanner.close();
        } catch (Exception e) {
            ConsoleDisplay.displayErrorMessage("Error when closing scanner: " + e.getMessage());
        }
    }
}
