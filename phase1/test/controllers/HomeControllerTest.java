package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Test;
import presenters.MockHomePresenter;

import static org.junit.Assert.fail;

public class HomeControllerTest {
    private HomeController homeController;
    private ManualConfig manualConfig;

    public HomeControllerTest() {
        manualConfig = new InMemoryManualConfig();
        homeController = new HomeController(manualConfig, new MockHomePresenter(), null);

    }

    /**
     * Verifies that it is impossible to create an account with an already existing username.
     */
    @Test(timeout = 50)
    public void testInvalidCreation() {
        try {
            manualConfig.getAccountManager().createStandardAccount("testUsername",
                    "testPassword");
            homeController.run();
        }
        // NullPointerException should only occur if MenuFacade is called, which should not be the case here.
        catch (NullPointerException e) {
            fail();
        }

    }
}
