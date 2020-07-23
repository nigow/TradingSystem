package controllers;

import entities.Account;
import entities.Item;
import gateways.InMemoryUseCasePool;
import gateways.UseCasePool;
import org.junit.Test;
import presenters.ConsoleTradeCreatorPresenter;
import presenters.LendingPresenter;
import presenters.TradeCreatorPresenter;
import usecases.AccountManager;
import usecases.ItemManager;
import usecases.OldTradeManager;

import java.util.List;


public class LendingControllerTest {


    @Test
    public void validInput(){
        UseCasePool useCasePool = new InMemoryUseCasePool();
        AccountManager accountManager = useCasePool.getAccountManager();
        ItemManager itemManager = useCasePool.getItemManager();
        OldTradeManager oldTradeManager = useCasePool.getOldTradeManager();
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
            public void customMessage(String message) {

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
                return "UTM";
            }

            @Override
            public String getDate() {
                return "9999-12-31";
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

        LendingController lendingController = new LendingController(lendingPresenter, useCasePool, tradeCreatorPresenter);
        Account admin = accountManager.getAccountsList().get(0);
        itemManager.createItem("Apple", "Tasty", admin.getAccountID());
        Item item = itemManager.getAllItems().get(0);
        item.approve();
        accountManager.createStandardAccount("test","password");
        accountManager.setCurrAccount(admin.getUsername());
        lendingController.run();

        assert(oldTradeManager.getAllTrades().get(0).getItemOneIDs().get(0) == item.getItemID());

    }


    @Test(timeout=50)
    public void invalidInput(){
        UseCasePool useCasePool = new InMemoryUseCasePool();
        AccountManager accountManager = useCasePool.getAccountManager();
        ItemManager itemManager = useCasePool.getItemManager();
        OldTradeManager oldTradeManager = useCasePool.getOldTradeManager();

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
            public void customMessage(String message) {

            }

        };

        LendingController lendingController = new LendingController(lendingPresenter, useCasePool, new ConsoleTradeCreatorPresenter());
        lendingController.run();
        assert(oldTradeManager.getAllTrades().isEmpty());
    }


}
