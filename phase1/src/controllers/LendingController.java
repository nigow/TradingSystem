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

public class LendingController {
    private final LendingPresenter lendingPresenter;
    private final AccountManager accountManager;
    private final ItemManager itemManager;
    private final ManualConfig manualConfig;

    public LendingController(ManualConfig manualConfig){
        this.manualConfig = manualConfig;
        this.lendingPresenter = new ConsoleLendingPresenter();
        this.accountManager = manualConfig.getAccountManager();
        this.itemManager = manualConfig.getItemManager();
    }

    private int chooseAccount(){
        List<Account> allAccounts = accountManager.getAccountsList();
        Iterator iterator = allAccounts.iterator();
        while(iterator.hasNext()){
            Account account = (Account)iterator.next();
            if(account.getAccountID() == accountManager.getCurrAccount().getAccountID()){
                iterator.remove();
            }
        }

        lendingPresenter.displayAccounts(allAccounts);

        int index = -2;
        while(index <= -2){
            try{
                index = Integer.parseInt(lendingPresenter.selectAccount());

            }
            catch(NumberFormatException e){

            }
            if(index < allAccounts.size() && index >= 0){
                index = allAccounts.get(index).getAccountID();
            }
            else if (index == -1){
                lendingPresenter.abort();
                return -1;
            }else{
                lendingPresenter.invalidInput();
            }
        }

        return index;
    }


    private int chooseItem(){
        List<Item> myItems = new ArrayList<>();
        int userId = accountManager.getCurrAccount().getAccountID();

        for(Item item: itemManager.getAllItems()){
            if(item.getOwnerID() == userId && item.isApproved()) myItems.add(item);
        }
        lendingPresenter.displayInventory(myItems);
        int index = -2;
        while(index <= -2){
            try{
                index = Integer.parseInt(lendingPresenter.selectItem());
            }
            catch(NumberFormatException e){
                //pass
            }
            if(index < myItems.size() && index >= 0) index = myItems.get(index).getItemID();
            else if (index == -1){
                lendingPresenter.abort();
                return -1;
            }else{
                lendingPresenter.invalidInput();
            }

        }

        return index;
    }

    public void run(){ //Todo: what if user changed their mind and decided not to trade in the middle?
        int toAccountId = chooseAccount();
        if (toAccountId == -1) return;
        int tradingItemId = chooseItem();
        if(tradingItemId == -1) return;

        TradeCreatorController startTrade;
        startTrade = new TradeCreatorController(manualConfig, toAccountId, tradingItemId);
        startTrade.run();
    }

}
