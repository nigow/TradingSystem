package usecases;

import entities.Account;
import entities.Permissions;
import entities.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsible for dealing with frozen accounts and managing restrictions.
 *
 * @author Andrew
 */
public class FreezingUtility {

    /**
     * The current restrictions of the trading system for all users.
     */
    private Restrictions restrictions;

    private AccountRepository accountRepository;

    /**
     * Constructs an instance of FreezingUtility and stores restrictionsGateway.
     *
     * @param restrictions Restrictions of the current program
     */

    //TODO how do we want to instantiate restrictions. From existing(like csv) or create a new instance here?
    public FreezingUtility(AccountRepository accountRepository, Restrictions restrictions) {
        this.accountRepository = accountRepository;
        this.restrictions = restrictions;
    }

    /**
     * Gets a list of accounts that have broken restrictions and are to be frozen.
     *
     * @param oldTradeUtility   Utility for getting trade information
     * @return List of accounts to freeze
     */
    public List<Account> getAccountsToFreeze(OldTradeUtility oldTradeUtility, Account adminAccount) {
        List<Account> accountsToFreeze = new ArrayList<>();
        for (Account account : accountRepository.getAccounts()) {
            if (canBeFrozen(oldTradeUtility, account, adminAccount)) {
                accountsToFreeze.add(account);
            }
        }
        return accountsToFreeze;
    }

    /**
     * Gets a list of account usernames that have broken restrictions and are to be frozen.
     *
     * @param oldTradeUtility   Utility for getting trade information
     * @return List of account usernames to freeze
     */
    public List<String> getUsernamesToFreeze(OldTradeUtility oldTradeUtility, Account adminAccount) {
        List<String> accountsToFreeze = new ArrayList<>();
        for (Account account : accountRepository.getAccounts()) {
            if (canBeFrozen(oldTradeUtility, account, adminAccount)) {
                accountsToFreeze.add(account.getUsername());
            }
        }
        return accountsToFreeze;
    }

    /**
     * Gets a list of accounts that have been frozen and have requested to be unfrozen.
     *
     * @return List of accounts to freeze
     */
    public List<Account> getAccountsToUnfreeze() {
        List<Account> accountsToUnfreeze = new ArrayList<>();
        for (Account account : accountRepository.getAccounts()) {
            if (isPending(account)) {
                accountsToUnfreeze.add(account);
            }
        }
        return accountsToUnfreeze;
    }

    /**
     * Gets a list of account usernames that have been frozen and have requested to be unfrozen.
     *
     * @return List of account usernames to freeze
     */
    public List<String> getUsernamesToUnfreeze() {
        List<String> accountsToUnfreeze = new ArrayList<>();
        for (Account account : accountRepository.getAccounts()) {
            if (isPending(account)) {
                accountsToUnfreeze.add(account.getUsername());
            }
        }
        return accountsToUnfreeze;
    }

    /**
     * Freezes an account by changing the removing the ability to borrow but adding a way to request to be unfrozen.
     *
     * @param oldTradeUtility Utility for getting trade information
     * @param account      Account to freeze
     * @param adminAccount The admin account that is freezing this account
     * @return Whether the given account is successfully frozen or not
     */
    public boolean freezeAccount(OldTradeUtility oldTradeUtility, Account account, Account adminAccount) {
        if (canBeFrozen(oldTradeUtility, account, adminAccount)) {
            account.removePermission(Permissions.BORROW);
            account.removePermission(Permissions.LEND);
            account.addPermission(Permissions.REQUEST_UNFREEZE);
            return true;
        }
        return false;
    }

    /**
     * Unfreezes an account that requested to be unfrozen by adding the ability to borrow.
     *
     * @param account     Account to unfreeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean unfreezeAccount(Account account) {
        if (isPending(account)) {
            account.removePermission(Permissions.REQUEST_UNFREEZE);
            account.addPermission(Permissions.LEND);
            account.addPermission(Permissions.BORROW);
            return true;
        }
        return false;
    }

    /**
     * Updates the restriction of the amount of items needed to be lent before borrowing.
     *
     * @param lendMoreThanBorrow Amount of items needed to be lent before borrowing
     */
    public void setLendMoreThanBorrow(int lendMoreThanBorrow) {
        restrictions.setLendMoreThanBorrow(lendMoreThanBorrow);
    }

    /**
     * Updates the restriction of the max number of incomplete trades before an account is frozen.
     *
     * @param maxIncompleteTrade Max number of incomplete trades
     */
    public void setMaxIncompleteTrade(int maxIncompleteTrade) {
        restrictions.setMaxIncompleteTrade(maxIncompleteTrade);
    }

    /**
     * Updates the restriction of the max number of weekly trades before an account is frozen.
     *
     * @param maxWeeklyTrade Max number of weekly trades
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade) {
        restrictions.setMaxWeeklyTrade(maxWeeklyTrade);
    }

    /**
     * Gets the current restriction for amount of items needed to be lent before borrowing.
     *
     * @return Amount of items needed to be lent before borrowing
     */
    public int getLendMoreThanBorrow() {
        return restrictions.getLendMoreThanBorrow();
    }

    /**
     * Gets the current restriction of the max number of incomplete trades before an account is frozen.
     *
     * @return Max number of incomplete trades
     */
    public int getMaxIncompleteTrade() {
        return restrictions.getMaxIncompleteTrade();
    }

    /**
     * Gets the current restriction of the max number of weekly trades before an account is frozen.
     *
     * @return Max number of weekly trades
     */
    public int getMaxWeeklyTrade() {
        return restrictions.getMaxWeeklyTrade();
    }

    /**
     * Determines whether a given account is frozen.
     *
     * @param account Account that is checked if it is frozen
     * @return Whether the account is frozen or not
     */
    public boolean isFrozen(Account account) {
        return account.getPermissions().contains(Permissions.LEND) &&
                !account.getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account has requested to be unfrozen.
     *
     * @param account Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(Account account) {
        return isFrozen(account) && !account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

    /**
     * Determines whether a given account should be frozen.
     *
     * @param oldTradeUtility Utility for getting trade information
     * @param account      Account that is checked if it can be frozen
     * @param adminAccount The admin account that is freezing this account
     * @return Whether the account can be frozen or not
     */
    // TODO incomplete since OldTradeUtility isn't updated
    public boolean canBeFrozen(OldTradeUtility oldTradeUtility, Account account, Account adminAccount) {
        oldTradeUtility.setAccount(account);

        //TODO each boolean should be method within OldTradeUtility or here?
        boolean withinMaxIncompleteTrades = oldTradeUtility.getTimesIncomplete() <= restrictions.getMaxIncompleteTrade();
        boolean withinWeeklyLimit = oldTradeUtility.getNumWeeklyTrades() < restrictions.getMaxWeeklyTrade();
        oldTradeUtility.setAccount(adminAccount);
        return !account.getPermissions().contains(Permissions.UNFREEZE) &&
                !isFrozen(account) && (!withinMaxIncompleteTrades || !withinWeeklyLimit);
    }

    //TODO should be in OldTradeUtility?
    /**
     * Determines whether the current account has lent more than borrowed.
     *
     * @param oldTradeUtility Utility for getting trade information
     * @return Whether the current account has lent more than borrowed
     */
    public boolean lentMoreThanBorrowed(OldTradeUtility oldTradeUtility) {
        return oldTradeUtility.getTimesLent() - oldTradeUtility.getTimesBorrowed() >=
                restrictions.getLendMoreThanBorrow();
    }

    /**
     * Determines whether a given account can request to unfreeze and requests to unfreeze if it can.
     *
     * @param account Account to request to be unfrozen
     */
    public void requestUnfreeze(Account account) {
        account.removePermission(Permissions.REQUEST_UNFREEZE);
    }
}
