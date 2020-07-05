package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Assert;
import presenters.MockMenuPresenter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MenuFacadeTest {

    private List<Object> setUp(String[] options) {
        ManualConfig inMemoryManualConfig = new InMemoryManualConfig();
        FreezingController freezingController = new FreezingController(inMemoryManualConfig, null);
        InventoryController inventoryController = new InventoryController(inMemoryManualConfig, null);
        WishlistController wishlistController = new WishlistController(null, null,
                inMemoryManualConfig);
        LendingController lendingController = new LendingController(null,
                inMemoryManualConfig, null);
        AppealController appealController = new AppealController(inMemoryManualConfig, null);
        TradeController tradeController = new TradeController(inMemoryManualConfig, null);
        AdminCreatorController adminCreatorController = new AdminCreatorController(inMemoryManualConfig,
                null);
        RestrictionController restrictionController = new RestrictionController(inMemoryManualConfig,
                null);
        MockMenuPresenter menuPresenter = new MockMenuPresenter();
        menuPresenter.setOptions(options);
        List<Object> lst = new ArrayList<>();
        lst.add(new MenuFacade(inMemoryManualConfig, freezingController, inventoryController,
                wishlistController, lendingController, appealController, tradeController, adminCreatorController,
                restrictionController, menuPresenter));
        lst.add(inMemoryManualConfig);
        return lst;
    }

    @Test
    public void testInvalidInput() {
        List<Object> lst = this.setUp(new String[]{"34", "44", "1000", "fjgjf", "hi", "__,", "4"});
        MenuFacade menuFacade = (MenuFacade) lst.get(0);
        ManualConfig manualConfig = (ManualConfig) lst.get(1);
        manualConfig.getAccountManager().createStandardAccount("test", "account");
        try {
            menuFacade.run();
        }
        catch (Exception ignored) {
            Assert.fail("The program should be able to handle invalid input");
        }

    }
}
