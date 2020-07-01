package usecases;

import entities.Account;
import entities.Permissions;
import entities.Restrictions;
import gateways.RestrictionsGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the manager responsible for dealing with frozen accounts and managing restrictions
 * @author Andrew
 */
public class FreezingUtility {

    /**
     * The restrictions gateway dealing with the storage of trading restrictions
     */
    private RestrictionsGateway restrictionsGateway;

    /**
     * The current restrictions of the trading system for all users
     */
    private Restrictions restrictions;

    /**
     * Constructs an instance of FreezingUtility and stores restrictionsGateway
     * @param restrictionsGateway Gateway used to interact with persistent storage of restrictions
     */
    public FreezingUtility(RestrictionsGateway restrictionsGateway){
        this.restrictionsGateway = restrictionsGateway;
        restrictions = restrictionsGateway.getRestrictions();
    }

    /**
     * Gets a list of accounts that have broken restrictions and are to be frozen
     * @param accountManager Manager for accounts used to retrieve all accounts
     * @param authManager Manager for permissions and authorizing actions
     * @param tradeUtility Utility for getting trade information
     * @return a list of accounts to freeze
     */
    public List<Account> getAccountsToFreeze(AccountManager accountManager, AuthManager authManager, TradeUtility tradeUtility){
        List<Account> accountsToFreeze = new ArrayList<>();
        for (Account account: accountManager.getAccountsList()){
            if (authManager.canbeFrozen(tradeUtility, account)){
                accountsToFreeze.add(account);
            }
        }
        return accountsToFreeze;
    }

    /**
     * Gets a list of account usernames that have broken restrictions and are to be frozen
     * @param accountManager Manager for accounts used to retrieve all accounts
     * @param authManager Manager for permissions and authorizing actions
     * @param tradeUtility Utility for getting trade information
     * @return a list of account usernames to freeze
     */
    public List<String> getUsernamesToFreeze(AccountManager accountManager, AuthManager authManager, TradeUtility tradeUtility){
        List<String> accountsToFreeze = new ArrayList<>();
        for (Account account: accountManager.getAccountsList()){
            if (authManager.canbeFrozen(tradeUtility, account)){
                accountsToFreeze.add(account.getUsername());
            }
        }
        return accountsToFreeze;
    }

    /**
     * Gets a list of accounts that have been frozen and have requested to be unfrozen
     * @param accountManager Manager for accounts used to retrieve all accounts
     * @param authManager Manager for permissions and authorizing actions
     * @return a list of accounts to freeze
     */
    public List<Account> getAccountsToUnfreeze(AccountManager accountManager, AuthManager authManager){
        List<Account> accountsToUnfreeze = new ArrayList<>();
        for (Account account: accountManager.getAccountsList()){
            if (authManager.isPending(account)){
                accountsToUnfreeze.add(account);
            }
        }
        return accountsToUnfreeze;
    }

    /**
     * Gets a list of account usernames that have been frozen and have requested to be unfrozen
     * @param accountManager Manager for accounts used to retrieve all accounts
     * @param authManager Manager for permissions and authorizing actions
     * @return a list of account usernames to freeze
     */
    public List<String> getUsernamesToUnfreeze(AccountManager accountManager, AuthManager authManager){
        List<String> accountsToUnfreeze = new ArrayList<>();
        for (Account account: accountManager.getAccountsList()){
            if (authManager.isPending(account)){
                accountsToUnfreeze.add(account.getUsername());
            }
        }
        return accountsToUnfreeze;
    }

    /**
     * Freezes an account by changing the removing the ability to borrow but adding a way to request to be unfrozen
     * @param authManager Manager for permissions and authorizing actions
     * @param tradeUtility Utility for getting trade information
     * @param account Account to freeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean freezeAccount(AuthManager authManager, TradeUtility tradeUtility, Account account){
        if (authManager.canbeFrozen(tradeUtility, account)){
            account.removePermission(Permissions.BORROW);
            account.addPermission(Permissions.REQUEST_UNFREEZE);
            return authManager.removePermissionByID(account, Permissions.BORROW)
                    && authManager.addPermissionByID(account, Permissions.REQUEST_UNFREEZE);
        }
        return false;
    }

    /**
     * Unfreezes an account that requested to be unfrozen by adding the ability to borrow
     * @param authManager Manager for permissions and authorizing actions
     * @param account Account to unfreeze
     * @return Whether the given account is successfully unfrozen or not
     */
    public boolean unfreezeAccount(AuthManager authManager, Account account){
        if (authManager.isPending(account)){
            return authManager.addPermissionByID(account, Permissions.BORROW);
        }
        return false;
    }

    /**
     * Updates the restriction of the amount of items needed to be lent before borrowing
     * @param lendMoreThanBorrow Amount of items needed to be lent before borrowing
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow){
        restrictions.setLendMoreThanBorrow(lendMoreThanBorrow);
        restrictionsGateway.updateRestrictions(restrictions);
    }

    /**
     * Updates the restriction of the max number of incomplete trades before an account is frozen
     * @param maxIncompleteTrade Max number of incomplete trades
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade){
        if (maxIncompleteTrade <= 0){
            throw new IllegalArgumentException("Value cannot be negative or 0");
        }
        restrictions.setMaxIncompleteTrade(maxIncompleteTrade);
        restrictionsGateway.updateRestrictions(restrictions);
    }

    /**
     * Updates the restriction of the max number of weekly trades before an account is frozen
     * @param maxWeeklyTrade Max number of weekly trades
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade){
        if (maxWeeklyTrade <= 0){
            throw new IllegalArgumentException("Value cannot be negative or 0");
        }
        restrictions.setMaxWeeklyTrade(maxWeeklyTrade);
        restrictionsGateway.updateRestrictions(restrictions);
    }

    /**
     * Gets the current restriction for amount of items needed to be lent before borrowing
     * @return Amount of items needed to be lent before borrowing
     */
    public int getLendMoreThanBorrow(){
        return restrictions.getLendMoreThanBorrow();
    }

    /**
     * Gets the current restriction of the max number of incomplete trades before an account is frozen
     * @return Max number of incomplete trades
     */
    public int getMaxIncompleteTrade(){
        return restrictions.getMaxIncompleteTrade();
    }

    /**
     * Gets the current restriction of the max number of weekly trades before an account is frozen
     * @return Max number of weekly trades
     */
    public int getMaxWeeklyTrade(){
        return restrictions.getMaxWeeklyTrade();
    }
}
