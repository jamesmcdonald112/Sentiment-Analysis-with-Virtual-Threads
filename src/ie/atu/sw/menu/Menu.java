package ie.atu.sw.menu;

import ie.atu.sw.utilities.ConsoleInput;

/**
 * Abstract class Menu serves as a template for different types of menus in the application.
 * It provides the structure for menu management and handling user input.
 */
public abstract class Menu {

    /**
     * Abstract method to be implemented by subclasses for managing their specific menu.
     */
    public abstract void menuManager();

    /**
     * Prompts the user to input an integer within the specified range.
     * Ensures that the input is within the bounds of the available menu options.
     * Big-O Notation: O(1) - The method involves a single input operation and a basic range check, both are constant-time operations.
     *
     * @param maxOption The maximum option number available in the menu.
     * @return An integer representing the user's choice.
     */
    public int getUserInput(int maxOption) {
        return ConsoleInput.getUserInputInt("Select an option (1-" + maxOption + ")> ", maxOption);
    }

}
