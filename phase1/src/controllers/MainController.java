package controllers;

import gateways.ManualConfig;
import gateways.ManualConfigCSV;

import java.io.IOException;

/**
 * A class that initiates the home page.
 */
public class MainController {
    /**
     * Starts the program and initiates ManualConfig and AuthController.
     */
    public void run() {
        try {
            ManualConfig manualConfig = new ManualConfigCSV();
            AuthController authController = new AuthController(manualConfig);
            authController.run();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
