package controllers;

import entities.Account;
import entities.Item;
import gateways.ManualConfig;
import presenters.ConsoleLendingPresenter;
import presenters.LendingPresenter;
import usecases.AccountManager;
import usecases.ItemManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controller that deals with lending service
 * @author Tairi
 */
public class LendingController {
    /**
     * Presenter for lending services
     */
    private final LendingPresenter lendingPresenter;

    /**
     * account manager needed to provide account-related services
     */
    private final AccountManager accountManager;

    /**
     * item manager needed to provide item-related services
     */
    private final ItemManager itemManager;

    /**
     * Collection of gateways
     */
    private final ManualConfig manualConfig;


    /**
     * Initialize the use cases and presenter
     * @param manualConfig collection of gateways
     */
    public LendingController(ManualConfig manualConfig){
        this.manualConfig = manualConfig;
        this.lendingPresenter = new ConsoleLendingPresenter();
        this.accountManager = manualConfig.getAccountManager();
        this.itemManager = manualConfig.getItemManager();
    }

    /**
     * Helper method that sends all accounts available to trade with, and let the user choose
     * @return index of the account in the list of accounts provided
     */
    private int chooseAccount(){
        List<Account> allAccounts = accountManager.getAccountsList();

        //remove the current account
        Iterator iterator = allAccounts.iterator();
        while(iterator.hasNext()){
            Account account = (Account)iterator.next();
            if(account.getAccountID() == accountManager.getCurrAccount().getAccountID()){
                iterator.remove();
            }
        }

        lendingPresenter.displayAccounts(allAccounts);

        int index = -2; //temporary value
        while(index <= -2){
            try{
                index = Integer.parseInt(lendingPresenter.selectAccount());

            }

            //not a number
            catch(NumberFormatException e){
                //pass
            }

            //valid input
            if(index < allAccounts.size() && index >= 0){
                index = allAccounts.get(index).getAccountID();
            }

            //user no longer trades
            else if (index == -1){
                lendingPresenter.abort();
                return -1;
            }

            //<=-2 or more than the list size
            else{
                lendingPresenter.invalidInput();
            }
        }

        return index;
    }


    /**
     * Helper method that sends all tradable items that the user owns
     * @return index of the item in the list of accounts provided
     */
    private int chooseItem(){
        List<Item> myItems = new ArrayList<>();
        int userId = accountManager.getCurrAccount().getAccountID();


        //list only tradable items
        for(Item item: itemManager.getAllItems()){
            if(item.getOwnerID() == userId && item.isApproved()) myItems.add(item);
        }

        lendingPresenter.displayInventory(myItems);

        int index = -2;
        while(index <= -2){
            try{
                index = Integer.parseInt(lendingPresenter.selectItem());
            }

            //not a number
            catch(NumberFormatException e){
                //pass
            }

            //valid input
            if(index < myItems.size() && index >= 0){
                index = myItems.get(index).getItemID();
            }

            //user no longer trades
            else if (index == -1) {
                lendingPresenter.abort();
                return -1;
            }

            //index <= -2 or more than the size of list
            else{
                lendingPresenter.invalidInput();
            }

        }
        return index;
    }

    /**
     * Let the user choose who to trade and which item to lend and start trading.
     */
    public void run(){
        int toAccountId = chooseAccount();
        if (toAccountId == -1) return;
        int tradingItemId = chooseItem();
        if(tradingItemId == -1) return;

        TradeCreatorController startTrade;
        startTrade = new TradeCreatorController(manualConfig, toAccountId, tradingItemId);
        startTrade.run();
    }

}
