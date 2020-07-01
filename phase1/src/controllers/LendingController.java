package controllers;

import entities.Account;
import entities.Item;
import presenters.ConsoleLendingPresenter;
import presenters.LendingPresenter;
import usecases.AccountManager;
import usecases.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class LendingController {
    private final LendingPresenter lendingPresenter;
    private final AccountManager accountManager;
    private final ItemManager itemManager;

    public LendingController(AccountManager accountManager, ItemManager itemManager){
        this.lendingPresenter = new ConsoleLendingPresenter();
        this.accountManager = accountManager;
        this.itemManager = itemManager;
    }

    private Account chooseAccount(){
        List<Account> allAccounts = accountManager.getAccountsList();
        lendingPresenter.displayAccounts(allAccounts);
        int index;
        try{
            index = Integer.parseInt(lendingPresenter.selectAccount());
            if(index < allAccounts.size()) return allAccounts.get(index);
        }
        catch(NumberFormatException e){
        }
        return null;
    }


    private Item chooseItem(){
        List<Item> myItems = new ArrayList<>();
        int userId = accountManager.getCurrAccount().getAccountID();

        for(Item item: itemManager.getAllItems()){
            if(item.getOwnerID() == userId && item.isApproved()) myItems.add(item);
        }
        lendingPresenter.displayInventory(myItems);
        int index;
        try{
            index = Integer.parseInt(lendingPresenter.selectItem());
            if(index < myItems.size()) return myItems.get(index);
        }
        catch(NumberFormatException e){}
        return null;
    }

    public void run(){ //Todo: what if user changed their mind and decided not to trade in the middle?
        boolean validInput = false;
        Account toAccount;
        Item tradingItem;
        while(!validInput){
            toAccount = chooseAccount();
            tradingItem = chooseItem();
            if (toAccount != null && tradingItem != null){
                validInput = true;
                lendingPresenter.startTrade(toAccount, tradingItem);

            }else{
                lendingPresenter.invalidInput();
            }
        }
        lendingPresenter.returnToMenu();

    }

}
