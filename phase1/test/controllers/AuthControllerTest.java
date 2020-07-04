package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Test;
import presenters.MockHomePresenter;

import static org.junit.Assert.fail;

public class AuthControllerTest {
    private AuthController authController;
    private ManualConfig manualConfig;

    public AuthControllerTest() {
        manualConfig = new InMemoryManualConfig();
        authController = new AuthController(manualConfig, new MockHomePresenter(), null);

    }

    /**
     * Verifies that it is impossible to create an account with an already existing username.
     */
    @Test(timeout = 50)
    public void testInvalidCreation() {
        try {
            manualConfig.getAccountManager().createStandardAccount("testUsername",
                    "testPassword");
            authController.run();
        }
        // NullPointerException should only occur if MenuFacade is called, which should not be the case here.
        catch (NullPointerException e) {
            fail();
        }

    }
}
