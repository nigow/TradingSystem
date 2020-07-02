package controllers;

import gateways.ManualConfig;

/**
 * A class that initiates the home page.
 */
public class MainController {
    /**
     * Starts the program and initiates ManualConfig and AuthController.
     */
    public void run() {
        ManualConfig manualConfig = new ManualConfig();
        AuthController authController = new AuthController(manualConfig);
        authController.run();
    }
}
