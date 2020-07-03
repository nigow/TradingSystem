package controllers;

import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import usecases.AccountManager;
import usecases.ItemManager;



public class LendingControllerTest {


    private ManualConfig manualConfig;


    private AccountManager accountManager;


    private ItemManager itemManager;


    @Rule
    private LendingController lendingController;



    @Before
    public void initializer(){
       manualConfig = new InMemoryManualConfig();
       accountManager = manualConfig.getAccountManager();
       itemManager = manualConfig.getItemManager();
       lendingController = new LendingController(manualConfig);
    }


    @Test
    public void testCorrectly(){
        //pass
    }


    @Test
    public void testInputString(){
        //pass
    }


    @Test
    public void testIndexOutOfRange(){
        //pass
    }


    @Test
    public void testAbort(){

    }
}
