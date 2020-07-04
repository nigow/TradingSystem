package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import junit.framework.TestCase;
import presenters.ConsoleFreezingPresenter;
import presenters.MockHomePresenter;
import presenters.MockHomePresenter;

import java.util.ArrayList;
import java.util.List;

public class AuthControllerTest extends TestCase {
    private AuthController authController;
    private ManualConfig manualConfig;

    public AuthControllerTest() {
        manualConfig = new InMemoryManualConfig();
        AuthController authController = new AuthController(manualConfig, new MockHomePresenter(), null);


    }

    public void testValidLogin() {
        manualConfig.getAccountManager().createStandardAccount("testUsername",
                "testPassword");
        authController.run();
    }

}
