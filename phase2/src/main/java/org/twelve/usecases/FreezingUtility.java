package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsible for dealing with frozen accounts and managing restrictions.
 *
 * @author Andrew
 */
public class FreezingUtility {

    private final AccountRepository accountRepository;
    private final ThresholdRepository thresholdRepository;

    private TradeManager tradeManager;

    /**
     * Constructs an instance of FreezingUtility and stores restrictionsGateway.
     *
     */

    public FreezingUtility(AccountRepository accountRepository, TradeManager tradeManager, ThresholdRepository thresholdRepository) {
        this.accountRepository = accountRepository;
        this.tradeManager = tradeManager;
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Gets a list of accounts that have broken restrictions and are to be frozen.
     *
     * @return List of accounts to freeze
     */
    public List<Integer> getAccountIDsToFreeze() {
        List<Integer> accountIDsToFreeze = new ArrayList<>();
        for (int accountID : accountRepository.getAccountIDs()) {
            if (canBeFrozen(accountID)) {
                accountIDsToFreeze.add(accountID);
            }
        }
        return accountIDsToFreeze;
    }

    /**
     * Gets a list of account usernames that have broken restrictions and are to be frozen.
     *
     * @return List of account usernames to freeze
     */
    public List<String> getUsernamesToFreeze() {
        List<String> accountsToFreeze = new ArrayList<>();
        for (int accountID : accountRepository.getAccountIDs()) {
            if (canBeFrozen(accountID)) {
                accountsToFreeze.add(accountRepository.getUsernameFromID(accountID));
            }
        }
        return accountsToFreeze;
    }

    /**
     * Gets a list of accounts that have been frozen and have requested to be unfrozen.
     *
     * @return List of accounts to freeze
     */
    public List<Integer> getAccountsToUnfreeze() {
        List<Integer> accountIDsToUnfreeze = new ArrayList<>();
        for (int accountID : accountRepository.getAccountIDs()) {
            if (isPending(accountID)) {
                accountIDsToUnfreeze.add(accountID);
            }
        }
        return accountIDsToUnfreeze;
    }

    /**
     * Gets a list of account usernames that have been frozen and have requested to be unfrozen.
     *
     * @return List of account usernames to freeze
     */
    public List<String> getUsernamesToUnfreeze() {
        List<String> accountsToUnfreeze = new ArrayList<>();
        for (int accountID : accountRepository.getAccountIDs()) {
            if (isPending(accountID)) {
                accountsToUnfreeze.add(accountRepository.getUsernameFromID(accountID));
            }
        }
        return accountsToUnfreeze;
    }

    /**
     * Freezes an account by changing the removing the ability to borrow but adding a way to request to be unfrozen.
     *
     * @param accountID      Account to freeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean freezeAccount(int accountID) {
        if (canBeFrozen(accountID)) {
            Account account = accountRepository.getAccountFromID(accountID);
            account.removePermission(Permissions.BORROW);
            account.removePermission(Permissions.LEND);
            account.addPermission(Permissions.REQUEST_UNFREEZE);
            accountRepository.updateAccount(account);
            return true;
        }
        return false;
    }

    /**
     * Unfreezes an account that requested to be unfrozen by adding the ability to borrow.
     *
     * @param accountID     Account to unfreeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean unfreezeAccount(int accountID) {
        if (isPending(accountID)) {
            Account account = accountRepository.getAccountFromID(accountID);
            account.removePermission(Permissions.REQUEST_UNFREEZE);
            account.addPermission(Permissions.LEND);
            account.addPermission(Permissions.BORROW);
            accountRepository.updateAccount(account);
            return true;
        }
        return false;
    }

    /**
     * Determines whether a given account is frozen.
     *
     * @param accountID Account that is checked if it is frozen
     * @return Whether the account is frozen or not
     */
    public boolean isFrozen(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        return !account.getPermissions().contains(Permissions.LEND) &&
                !account.getPermissions().contains(Permissions.BORROW) && account.getPermissions().contains(Permissions.REQUEST_VACATION);
    }

    /**
     * Determines whether a given account has requested to be unfrozen.
     *
     * @param accountID Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        return isFrozen(accountID) && !account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

    public boolean canTrade(int accountID){
        Account account = accountRepository.getAccountFromID(accountID);
        return account.getPermissions().contains(Permissions.LEND) &&
                account.getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account should be frozen.
     *
     * @param accountID    Unique identifier of an account that is checked if it can be frozen
     * @return Whether the account can be frozen or not
     */
    private boolean canBeFrozen(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        boolean withinMaxIncompleteTrades = tradeManager.getTimesIncomplete(accountID) <= thresholdRepository.getMaxIncompleteTrade();
        boolean withinWeeklyLimit = tradeManager.getNumWeeklyTrades(accountID) < thresholdRepository.getMaxWeeklyTrade();
        return !account.getPermissions().contains(Permissions.UNFREEZE) &&
                !isFrozen(accountID) && (!withinMaxIncompleteTrades || !withinWeeklyLimit);
    }


    /**
     * Determines whether the current account has lent more than borrowed.
     *
     * @return Whether the current account has lent more than borrowed
     */
    public boolean lentMoreThanBorrowed(int accountID) {
        return tradeManager.getTimesLent(accountID) - tradeManager.getTimesBorrowed(accountID) >=
                thresholdRepository.getLendMoreThanBorrow();
    }

    /**
     * Determines whether a given account can request to unfreeze and requests to unfreeze if it can.
     *
     * @param accountID Account to request to be unfrozen
     */
    public void requestUnfreeze(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.REQUEST_UNFREEZE);
        accountRepository.updateAccount(account);
    }

    public void requestVacation(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.REQUEST_VACATION);
        account.removePermission(Permissions.LEND);
        account.removePermission(Permissions.BORROW);
        accountRepository.updateAccount(account);
    }

    public void completeVacation(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(Permissions.REQUEST_VACATION);
        account.addPermission(Permissions.LEND);
        account.addPermission(Permissions.BORROW);
        accountRepository.updateAccount(account);
    }

}
