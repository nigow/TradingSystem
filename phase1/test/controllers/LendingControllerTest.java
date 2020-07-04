package controllers;

import entities.Account;
import entities.Item;
import gateways.InMemoryManualConfig;
import gateways.ManualConfig;
import org.junit.Test;
import presenters.ConsoleTradeCreatorPresenter;
import presenters.LendingPresenter;
import presenters.TradeCreatorPresenter;
import usecases.AccountManager;
import usecases.ItemManager;
import usecases.TradeManager;

import java.util.List;


public class LendingControllerTest {


    @Test
    public void validInput(){
        ManualConfig manualConfig = new InMemoryManualConfig();
        AccountManager accountManager = manualConfig.getAccountManager();
        ItemManager itemManager = manualConfig.getItemManager();
        TradeManager tradeManager = manualConfig.getTradeManager();
        LendingPresenter lendingPresenter = new LendingPresenter(){

            @Override
            public void displayAccounts(List<Account> allAccounts) {
                //pass
            }

            @Override
            public void displayInventory(List<Item> inventory) {
                //pass
            }

            @Override
            public String selectAccount() {
                return "0";
            }

            @Override
            public String selectItem() {
                return "0";
            }


            @Override
            public void invalidInput() {
                //pass
            }

            @Override
            public void abort() {
                //pass
            }
        };
        TradeCreatorPresenter tradeCreatorPresenter = new TradeCreatorPresenter() {
            @Override
            public void invalidInput() {
                //pass
            }

            @Override
            public String getTwoWayTrade() {
                return "n";
            }

            @Override
            public void showInventory(String username, List<String> inventory) {
                //pass
            }

            @Override
            public String getItem() {
                return null;
            }

            @Override
            public String getLocation() {
                return null;
            }

            @Override
            public String getDate() {
                return "2000-01-01";
            }

            @Override
            public String getTime() {
                return "10:10";
            }

            @Override
            public String getIsPerm() {
                return "n";
            }

            @Override
            public void successMessage() {
                //pass
            }
        };

        LendingController lendingController = new LendingController(lendingPresenter, manualConfig, tradeCreatorPresenter);
        Account admin = accountManager.getAccountsList().get(0);
        itemManager.createItem("Apple", "Tasty", admin.getAccountID());
        Item item = itemManager.getAllItems().get(0);
        item.approve();
        accountManager.createStandardAccount("test","password");
        accountManager.setCurrAccount(admin.getUsername());
        lendingController.run();

        assert(tradeManager.getAllTrades().get(0).getItemOneIDs().get(0) == item.getItemID());

    }


    @Test
    public void invalidInput(){
        ManualConfig manualConfig = new InMemoryManualConfig();
        AccountManager accountManager = manualConfig.getAccountManager();
        ItemManager itemManager = manualConfig.getItemManager();
        TradeManager tradeManager = manualConfig.getTradeManager();

        LendingPresenter lendingPresenter = new LendingPresenter(){

            int i = 0;

            @Override
            public void displayAccounts(List<Account> allAccounts) {
                //pass
            }

            @Override
            public void displayInventory(List<Item> inventory) {
                //pass
            }

            @Override
            public String selectAccount() {
                if (i==0){
                    i++;
                    return "aaa";
                }else{
                    return "-1";
                }
            }

            @Override
            public String selectItem() {
                return "aaa";
            }


            @Override
            public void invalidInput() {
                //pass
            }

            @Override
            public void abort() {
                //pass
            }
        };

        LendingController lendingController = new LendingController(lendingPresenter, manualConfig, new ConsoleTradeCreatorPresenter());
        lendingController.run();
        assert(tradeManager.getAllTrades().isEmpty() == true);
    }


}
