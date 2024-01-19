package ie.atu.sw.menu;

import ie.atu.sw.io.InputDirectoryManager;
import ie.atu.sw.io.OutputDirectoryManager;
import ie.atu.sw.io.LexiconDirectoryManager;
import ie.atu.sw.utilities.*;

import java.nio.file.Paths;
import java.util.InputMismatchException;

/**
 * ExtrasMenu is a subclass of Menu that handles additional settings and options
 * related to directory management for file input, output, and lexicon directories.
 */
public class ExtrasMenu extends Menu {
    private InputDirectoryManager fileInputDirectoryManager;
    private OutputDirectoryManager outputDirectoryManager;
    private LexiconDirectoryManager lexiconDirectoryManager;


    /**
     * Constructor for ExtrasMenu.
     * Initializes the menu with managers for handling different types of directories.
     * Big-O Notation: O(1) - Initialisation of the class with directory managers is a
     * constant-time operation.
     *
     * @param fileInputDirectoryManager Manager for the file input directory.
     * @param outputDirectoryManager    Manager for the output directory.
     * @param lexiconDirectoryManager   Manager for the lexicon directory.
     */
    public ExtrasMenu(InputDirectoryManager fileInputDirectoryManager,
                      OutputDirectoryManager outputDirectoryManager,
                      LexiconDirectoryManager lexiconDirectoryManager) {
        this.fileInputDirectoryManager = fileInputDirectoryManager;
        this.outputDirectoryManager = outputDirectoryManager;
        this.lexiconDirectoryManager = lexiconDirectoryManager;
    }

    /**
     * Manages the options menu, handling user inputs and navigating through the menu.
     * Big-O Notation: O(n) - The complexity depends on the user's interaction with the menu.
     */
    @Override
    public void menuManager() {
        optionsMenuChoice();
    }

    /**
     * Displays the options menu to the user.
     * Presents options to view settings, clear settings, or return to the main menu.
     * Big-O Notation: O(1) - Displaying the menu is a constant-time operation.
     */
    private void displayMenu() throws InterruptedException {
        ConsoleColour optionsColour = ConsoleColour.YELLOW_BRIGHT;

        ConsoleDisplay.displayColoredMessage("(1) View Settings", optionsColour);
        ConsoleDisplay.displayColoredMessage("(2) Clear Settings", optionsColour);
        ConsoleDisplay.displayColoredMessage("(3) Return to Main Menu", optionsColour);
    }

    /**
     * Handles the user's choice in the options' menu.
     * Allows the user to view settings, clear settings, or exit the options' menu.
     * Big-O Notation: O(1) - Processing user choice involves a series of constant-time operations.
     */
    private void optionsMenuChoice() {
        boolean running = true;
        while (running)
            try {
                displayMenu();
                int userChoice = super.getUserInput(3);
                switch (userChoice) {
                    case 1 -> handleViewSettings();
                    case 2 -> handleClearSettings();
                    case 3 -> running = false;
                    default -> System.out.println("Invalid choice. Please select an option from " +
                            "1-3");
                }
            } catch (InputMismatchException inputMismatchException) {
                ConsoleDisplay.displayErrorMessage("Invalid input. Please enter an integer value between 1-3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * Handles the action for viewing current settings.
     * Displays the current settings for the input, output, and lexicon directories.
     * Big-O Notation: O(1) - The method involves retrieving and displaying current settings, which are constant-time operations.
     */
    private void handleViewSettings() {
        getInputFileDirectorySettings();
        getOutputFileDirectorySettings();
        getLexiconDirectorySettings();
        ConsoleInput.promptContinue();
    }

    /**
     * Retrieves and displays the current directory path for input files.
     * Informs the user if the path is empty or displays the absolute path if it's set.
     * Big-O Notation: O(1) - Retrieving and displaying a single directory path is a constant-time operation.
     */
    private void getInputFileDirectorySettings() {
        String inputDirectoryPath = fileInputDirectoryManager.getCurrentDirectoryPath();
        if (inputDirectoryPath == null) {
            ConsoleDisplay.displayMessage("The text file directory path is empty");
        } else {
            String fileAbsolutePath =
                    Paths.get(inputDirectoryPath).toAbsolutePath().toString();
            ConsoleDisplay.displayMessage("The text file directory path is: " + fileAbsolutePath);
        }
    }

    /**
     * Retrieves and displays the current directory path for output files.
     * Informs the user if the path is empty or displays the absolute path if it's set.
     * Big-O Notation: O(1) - Retrieving and displaying a single directory path is a constant-time operation.
     */
    private void getOutputFileDirectorySettings() {
        String outputDirectoryPath = outputDirectoryManager.getCurrentDirectoryPath();
        if (outputDirectoryPath == null) {
            ConsoleDisplay.displayMessage("The output directory path is empty");
        } else {
            String fileAbsolutePath =
                    Paths.get(outputDirectoryPath).toAbsolutePath().toString();
            ConsoleDisplay.displayMessage("The output directory path is: " + fileAbsolutePath);
        }
    }

    /**
     * Retrieves and displays the current directory path for lexicon files.
     * Informs the user if the path is empty or displays the absolute path if it's set.
     * Big-O Notation: O(1) - Retrieving and displaying a single directory path is a constant-time operation.
     */
    private void getLexiconDirectorySettings() {
        String lexiconDirectoryPath = lexiconDirectoryManager.getCurrentDirectoryPath();
        if (lexiconDirectoryPath == null) {
            ConsoleDisplay.displayMessage("The lexicon directory path is empty");
        } else {
            String lexiconAbsolutePath =
                    Paths.get(lexiconDirectoryPath).toAbsolutePath().toString();
            ConsoleDisplay.displayMessage("The lexicon directory path is: " + lexiconAbsolutePath);
        }
    }

    /**
     * Handles the action to clear all directory settings.
     * Prompts the user for confirmation before clearing the settings.
     * Big-O Notation: O(1) - The method involves resetting directory paths, which are constant-time operations.
     */
    private void handleClearSettings() {
        boolean clearSetting = ConsoleInput.getYesOrNo("Would you like to clear all lettings");
        if (clearSetting) {
            fileInputDirectoryManager.resetCurrentDirectoryPath();
            outputDirectoryManager.resetCurrentDirectoryPath();
            lexiconDirectoryManager.resetCurrentDirectoryPath();
            ConsoleDisplay.displayConfirmationMessage("Success");
        }
        handleViewSettings(); // Display the settings after clearing to confirm the action
    }

}
