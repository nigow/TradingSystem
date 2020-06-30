package controllers;

/**
 * Contains the main method that starts the program.
 */
public class Main {
    /**
     * Calls the run method of MainController to start the program.
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.run();
    }
}
