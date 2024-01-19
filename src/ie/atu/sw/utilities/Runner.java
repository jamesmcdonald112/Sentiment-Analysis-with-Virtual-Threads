package ie.atu.sw.utilities;

import ie.atu.sw.menu.MainMenu;

/**
 * Runner class contains the main method to start the application.
 * It initialises and manages the main menu and other startup processes.
 */
public class Runner {

    /**
     * The entry point of the application.
     * Initialises the main menu and starts the application's execution flow.
     * This method sets up the necessary components and enters the main menu loop for user interaction.
     * Big-O Notation: O(n) - The complexity depends on the interactions and operations performed in the main menu.
     *
     * @param args Command-line arguments passed to the application (not used in this case).
     * @throws Exception if an exception occurs during the execution of the application.
     */
    public static void main(String[] args) throws Exception {
        MainMenu mainMenu = new MainMenu();
        mainMenu.menuManager();
        ProgressMeter.runProgressMeter();
    }
}