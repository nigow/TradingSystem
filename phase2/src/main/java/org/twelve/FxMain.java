package org.twelve;

import javafx.application.Application;
import org.twelve.views.WindowHandler;

/**
 * Instantiate the application using JavaFx
 */
public class FxMain {

    /**
     * Launches the window handler of the application
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        Application.launch(WindowHandler.class, args);
    }

}
