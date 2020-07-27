package org.twelve;

import org.twelve.controllers.console.MainController;

/**
 * Contains the main method that starts the program.
 *
 * @author Maryam
 */
public class ConsoleMain {
    /**
     * Calls the run method of MainController to start the program.
     *
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.run();
    }
}
