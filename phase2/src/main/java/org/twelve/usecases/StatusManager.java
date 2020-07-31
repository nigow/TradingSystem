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
public class StatusManager {

    private final AccountRepository accountRepository;
    private final ThresholdRepository thresholdRepository;

    private TradeManager tradeManager;

    /**
     * Constructs an instance of StatusManager and stores restrictionsGateway.
     *
     */

    public StatusManager(AccountRepository accountRepository, TradeManager tradeManager, ThresholdRepository thresholdRepository) {
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

    public List<Account> getAccountsToBan(){
        List<Account> accountsToBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(hasPermission(accountID, Permissions.LOGIN) && !hasPermission(accountID, Permissions.CAN_BAN)){
                accountsToBan.add(accountRepository.getAccountFromID(accountID));
            }
        }
        return accountsToBan;
    }

    public List<Integer> getAccountIDsToBan(){
        List<Integer> accountIDsToBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(hasPermission(accountID, Permissions.LOGIN) && !hasPermission(accountID, Permissions.CAN_BAN)){
                accountIDsToBan.add(accountID);
            }
        }
        return accountIDsToBan;
    }

    public List<String> getUsernamesToBan(){
        List<String> usernamesToBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(hasPermission(accountID, Permissions.LOGIN) && !hasPermission(accountID, Permissions.CAN_BAN)){
                usernamesToBan.add(accountRepository.getUsernameFromID(accountID));
            }
        }
        return usernamesToBan;
    }

    public List<Account> getAccountsToUnBan(){
        List<Account> accountsToUnBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(!hasPermission(accountID, Permissions.LOGIN)){
                accountsToUnBan.add(accountRepository.getAccountFromID(accountID));
            }
        }
        return accountsToUnBan;
    }

    public List<Integer> getAccountIDsToUnBan(){
        List<Integer> accountIDsToUnBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(!hasPermission(accountID, Permissions.LOGIN)){
                accountIDsToUnBan.add(accountID);
            }
        }
        return accountIDsToUnBan;
    }

    public List<String> getUsernamesToUnBan(){
        List<String> usernamesToUnBan = new ArrayList<>();
        for(int accountID: accountRepository.getAccountIDs()){
            if(!hasPermission(accountID, Permissions.LOGIN)){
                usernamesToUnBan.add(accountRepository.getUsernameFromID(accountID));
            }
        }
        return usernamesToUnBan;
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
        return !hasPermission(accountID, Permissions.LEND) && !hasPermission(accountID, Permissions.BORROW)
                && hasPermission(accountID, Permissions.REQUEST_VACATION);
    }

    /**
     * Determines whether a given account has requested to be unfrozen.
     *
     * @param accountID Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(int accountID) {
        return isFrozen(accountID) && !hasPermission(accountID, Permissions.REQUEST_UNFREEZE);
    }

    public boolean canTrade(int accountID){
        return hasPermission(accountID, Permissions.LEND) && hasPermission(accountID, Permissions.BORROW);
    }

    /**
     * Determines whether a given account should be frozen.
     *
     * @param accountID    Unique identifier of an account that is checked if it can be frozen
     * @return Whether the account can be frozen or not
     */
    private boolean canBeFrozen(int accountID) {
        boolean withinMaxIncompleteTrades = tradeManager.getTimesIncomplete(accountID) <= thresholdRepository.getMaxIncompleteTrade();
        boolean withinWeeklyLimit = tradeManager.getNumWeeklyTrades(accountID) < thresholdRepository.getMaxWeeklyTrade();
        return !hasPermission(accountID, Permissions.UNFREEZE) &&
                !isFrozen(accountID) && (!withinMaxIncompleteTrades || !withinWeeklyLimit);
    }

    public boolean canVacation(int accountID){
        return !canBeFrozen(accountID) && canTrade(accountID) && hasPermission(accountID, Permissions.REQUEST_VACATION);
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

    public boolean hasPermission(int accountID, Permissions perm){
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(perm);
    }

    public boolean isVacationing(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        return !canTrade(accountID) && !account.getPermissions().contains(Permissions.REQUEST_UNFREEZE);
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

    public void banAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.LOGIN);
    }

    public void unbanAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(Permissions.LOGIN);
    }


}
