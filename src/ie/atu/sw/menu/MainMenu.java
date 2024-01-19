package ie.atu.sw.menu;

import ie.atu.sw.io.*;
import ie.atu.sw.lexicon.LexiconManager;
import ie.atu.sw.processing.*;
import ie.atu.sw.utilities.*;

import java.util.*;

/**
 * MainMenu class extends Menu to provide the main user interface for the application.
 * It offers various options like managing directories, configuring lexicons, and running sentiment analysis.
 */
public class MainMenu extends Menu {
    private final InputDirectoryManager fileInputDirectoryManager = new InputDirectoryManager();
    private final OutputDirectoryManager outputDirectoryManager = new OutputDirectoryManager();
    private final LexiconDirectoryManager lexiconDirectoryManager = new LexiconDirectoryManager();
    private boolean running = true;
    private final ExtrasMenu extrasMenu = new ExtrasMenu(fileInputDirectoryManager,
            outputDirectoryManager,
            lexiconDirectoryManager);
    private Map<Integer, Double> combinedLexicon;

    /**
     * Manages the main menu of the application.
     * Displays options to the user and handles user input to navigate through the menu.
     * Big-O Notation: O(n) - The complexity depends on the user interaction within the while loop.
     */
    @Override
    public void menuManager() {
        while (running) {
            displayMenu();
            int choice = super.getUserInput(7);
            switch (choice) {
                case 1 -> handleFileInputDirectory();
                case 2 -> specifyURL();
                case 3 -> handleOutputDirectory();
                case 4 -> configureLexicons();
                case 5 -> executeAnalyseAndReport();
                case 6 -> extras();
                case 7 -> quitProgram();
                default -> handleInvalidOption();
            }
        }
    }

    /**
     * Displays the main menu options to the console.
     * Provides a list of actions the user can take.
     * Big-O Notation: O(1) - Displaying menu options is a constant-time operation.
     */
    private void displayMenu() {
        ConsoleColour headerColour = ConsoleColour.WHITE;
        ConsoleColour optionsColour = ConsoleColour.YELLOW_BRIGHT;

        ConsoleDisplay.displayColoredMessage("************************************************************", headerColour);
        ConsoleDisplay.displayColoredMessage("*     ATU - Dept. of Computer Science & Applied Physics    *", headerColour);
        ConsoleDisplay.displayColoredMessage("*                                                          *", headerColour);
        ConsoleDisplay.displayColoredMessage("*             Virtual Threaded Sentiment Analyser          *", headerColour);
        ConsoleDisplay.displayColoredMessage("*                                                          *", headerColour);
        ConsoleDisplay.displayColoredMessage("************************************************************", headerColour);
        ConsoleDisplay.displayColoredMessage("(1) Specify a Text File", optionsColour);
        ConsoleDisplay.displayColoredMessage("(2) Specify a URL", optionsColour);
        ConsoleDisplay.displayColoredMessage("(3) Specify an Output File (default: ./out.txt)", optionsColour);
        ConsoleDisplay.displayColoredMessage("(4) Configure Lexicons", optionsColour);
        ConsoleDisplay.displayColoredMessage("(5) Execute, Analyse and Report", optionsColour);
        ConsoleDisplay.displayColoredMessage("(6) Optional Extras...", optionsColour);
        ConsoleDisplay.displayColoredMessage("(7) Quit", optionsColour);

    }

    /**
     * Handles setting or updating the file input directory.
     * Invokes the fileInputDirectoryManager to configure the path and handles any exceptions.
     * Big-O Notation: O(n) - Complexity depends on the directory configuration process which may involve user input.
     */
    private void handleFileInputDirectory() {
        try {
            fileInputDirectoryManager.configureDirectoryPath();
        } catch (DirectoryConfigurationException e) {
            ConsoleDisplay.displayErrorMessage(e.getMessage());
        }
        ConsoleInput.promptContinue();
    }

    /**
     * Prompts the user to specify a URL for analysis.
     * Note: Functionality to be completed (TBC).
     * Big-O Notation: O(1) - Prompting the user is a constant-time operation.
     */
    private void specifyURL() {
        ConsoleDisplay.displayMessage("Enter the URL for analysis: (TBC)");
        ConsoleInput.promptContinue();
    }

    /**
     * Handles setting or updating the output directory.
     * Invokes the outputDirectoryManager to configure the path and handles any exceptions.
     * Big-O Notation: O(n) - Complexity depends on the directory configuration, which prompts
     * the user to enter a valid directory.
     */
    private void handleOutputDirectory() {
        try {
            outputDirectoryManager.configureDirectoryPath();
        } catch (DirectoryConfigurationException e) {
            ConsoleDisplay.displayErrorMessage(e.getMessage());
        }
        ConsoleInput.promptContinue();
    }

    /**
     * Configures lexicons for use in sentiment analysis.
     * Loads lexicon data from the specified directory and handles any exceptions.
     * Big-O Notation: O(n) - Complexity depends on the lexicon loading process.
     */
    private void configureLexicons() {
        try {
            String lexiconDirectoryPath = lexiconDirectoryManager.configureDirectoryPath();
            if (lexiconDirectoryPath == null) {
                return;
            }
            combinedLexicon = LexiconManager.loadLexicons(lexiconDirectoryPath);
            ConsoleDisplay.displayConfirmationMessage("Lexicons loaded successfully.");
        } catch (InterruptedException e) {
            ConsoleDisplay.displayErrorMessage("An error occurred while loading lexicons: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        ConsoleInput.promptContinue();
    }

    /**
     * Executes sentiment analysis and generates a report.
     * Validates configuration, performs analysis, and displays results based on user preference.
     * Big-O Notation: O(n) - Complexity depends on the sentiment analysis process, including file reading and processing.
     */
    private void executeAnalyseAndReport() {
        try {
            String configErrors = ConfigurationValidator.checkSentimentAnalysisConfigurations(
                    fileInputDirectoryManager, outputDirectoryManager, combinedLexicon);

            if (!configErrors.isEmpty()) {
                ConsoleDisplay.displayErrorMessage(configErrors);
                return;
            }

            boolean showInConsole = ConsoleInput.getYesOrNo("Do you want to see the analysis results in the console?");
            SentimentAnalysisManager analysisManager = new SentimentAnalysisManager(
                    fileInputDirectoryManager, outputDirectoryManager, combinedLexicon);
            analysisManager.executeAnalyseAndReport(showInConsole);

        } catch (Exception e) {
            ConsoleDisplay.displayErrorMessage("Error: " + e.getMessage());
        }
        ConsoleInput.promptContinue();
    }

    /**
     * Opens the extras menu for additional settings and options.
     * Big-O Notation: O(n) - The complexity depends on the interactions within the extras menu.
     */
    private void extras() {
        extrasMenu.menuManager();
    }

    /**
     * Quits the program.
     * Displays a confirmation message and updates the running status to false.
     * Big-O Notation: O(1) - Quitting the program is a constant-time operation.
     */
    private void quitProgram() {
        ConsoleDisplay.displayConfirmationMessage("Quitting program...");
        ConsoleInput.closeScanner();
        running = false;
    }

    /**
     * Handles invalid menu option selections.
     * Displays an error message prompting the user to try again.
     * Big-O Notation: O(1) - Handling an invalid option is a constant-time operation.
     */
    private void handleInvalidOption() {
        ConsoleDisplay.displayErrorMessage("Invalid option. Please try again.");
    }
}
