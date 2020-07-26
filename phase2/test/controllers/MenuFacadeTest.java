package controllers;

import gateways.InMemoryUseCasePool;
import org.junit.Assert;
import presenters.MockMenuPresenter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MenuFacadeTest {

    private List<Object> setUp(String[] options) {
        UseCasePool inMemoryUseCasePool = new InMemoryUseCasePool();
        FreezingController freezingController = new FreezingController(inMemoryUseCasePool, null);
        InventoryController inventoryController = new InventoryController(inMemoryUseCasePool, null);
        WishlistController wishlistController = new WishlistController(null, null,
                inMemoryUseCasePool);
        LendingController lendingController = new LendingController(null,
                inMemoryUseCasePool, null);
        AppealController appealController = new AppealController(inMemoryUseCasePool, null);
        TradeController tradeController = new TradeController(inMemoryUseCasePool, null);
        AdminCreatorController adminCreatorController = new AdminCreatorController(inMemoryUseCasePool,
                null);
        RestrictionController restrictionController = new RestrictionController(inMemoryUseCasePool,
                null);
        MockMenuPresenter menuPresenter = new MockMenuPresenter();
        menuPresenter.setOptions(options);
        List<Object> lst = new ArrayList<>();
        lst.add(new MenuFacade(inMemoryUseCasePool, freezingController, inventoryController,
                wishlistController, lendingController, appealController, tradeController, adminCreatorController,
                restrictionController, menuPresenter));
        lst.add(inMemoryUseCasePool);
        return lst;
    }

    @Test(timeout = 100)
    public void testInvalidInput() {
        // 3 is the exit key for a regular user.
        List<Object> lst = this.setUp(new String[]{"34", "44", "1000", "fjgjf", "hi", "__,", "3"});
        MenuFacade menuFacade = (MenuFacade) lst.get(0);
        UseCasePool useCasePool = (UseCasePool) lst.get(1);
        useCasePool.getAccountManager().createStandardAccount("test", "account");
        try {
            menuFacade.run();
        }
        catch (Exception ignored) {
            Assert.fail("The program should be able to handle invalid input");
        }

    }
}
