package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import usecases.AccountManager;
import usecases.ItemManager;


/**
 * Unit test for LendingController
 * @author Tairi
 */
public class LendingControllerTest {

    /**
     * Collection of gateways
     */
    private ManualConfig manualConfig;

    /**
     * account manager needed to provide account-related services
     */
    private AccountManager accountManager;

    /**
     * item manager needed to provide item-related services
     */
    private ItemManager itemManager;

    /**
     * Collection of gateways
     */
    @Rule
    private LendingController lendingController;


    /**
     * Initialize the utilities
     */
    @Before
    public void initializer(){
       manualConfig = new InMemoryManualConfig();
       accountManager = manualConfig.getAccountManager();
       itemManager = manualConfig.getItemManager();
       lendingController = new LendingController(manualConfig);
    }

    /**
     * Test the two inputs that are valid
     */
    @Test
    public void testCorrectly(){
        //pass
    }

    /**
     * Test the first input being alphabetic so that it is now invalid as an index
     */
    @Test
    public void testInputString(){
        //pass
    }

    /**
     * Test the first input being greater than the number of all accounts
     */
    @Test
    public void testIndexOutOfRange(){
        //pass
    }

    /**
     * Test if the program aborts appropriately when user no longer wants to trade
     */
    @Test
    public void testAbort(){

    }
}
