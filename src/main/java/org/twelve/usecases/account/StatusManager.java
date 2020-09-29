package org.twelve.usecases.account;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;
import org.twelve.entities.Roles;
import org.twelve.usecases.account.AccountRepository;
import org.twelve.usecases.system.ThresholdRepository;
import org.twelve.usecases.trade.TradeUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsible for dealing with frozen accounts and managing restrictions.
 */
public class StatusManager {

    private final AccountRepository accountRepository;
    private final ThresholdRepository thresholdRepository;
    private final TradeUtility tradeUtility;

    /**
     * Constructs an instance of StatusManager.
     *
     * @param accountRepository   An instance of accountRepository
     * @param tradeUtility        An instance of trade utility
     * @param thresholdRepository An instance of thresholdRepository
     */
    public StatusManager(AccountRepository accountRepository, TradeUtility tradeUtility,
                         ThresholdRepository thresholdRepository) {
        this.accountRepository = accountRepository;
        this.tradeUtility = tradeUtility;
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
        for (int accountID : getAccountIDsToFreeze()) {
            accountsToFreeze.add(accountRepository.getUsernameFromID(accountID));
        }
        return accountsToFreeze;
    }

    /**
     * Freezes an account by changing the removing the ability to borrow but adding a way to request to be unfrozen.
     *
     * @param accountID Account to freeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean freezeAccount(int accountID) {
        if (canBeFrozen(accountID)) {
            Account account = accountRepository.getAccountFromID(accountID);
            account.removePermission(Permissions.TRADE);
            account.addPermission(Permissions.REQUEST_UNFREEZE);
            accountRepository.updateToAccountGateway(account, false);
            return true;
        }
        return false;
    }

    /**
     * Unfreezes an account that requested to be unfrozen by adding the ability to borrow.
     *
     * @param accountID Account to unfreeze
     * @return Whether the given account is successfully frozen or not
     */
    public boolean unfreezeAccount(int accountID) {
        if (isPending(accountID)) {
            Account account = accountRepository.getAccountFromID(accountID);
            account.removePermission(Permissions.REQUEST_UNFREEZE);
            account.addPermission(Permissions.TRADE);
            accountRepository.updateToAccountGateway(account, false);
            return true;
        }
        return false;
    }

    /**
     * Determines whether a given account has requested to be unfrozen.
     *
     * @param accountID Account that is checked to see if it has requested to be unfrozen
     * @return Whether the account has requested to be unfrozen or not
     */
    public boolean isPending(int accountID) {
        return getRoleOfAccount(accountID) == Roles.FROZEN && !hasPermission(accountID, Permissions.REQUEST_UNFREEZE);
    }

    /**
     * Determines whether a given account should be frozen.
     *
     * @param accountID Unique identifier of an account that is checked if it can be frozen
     * @return Whether the account can be frozen or not
     */
    private boolean canBeFrozen(int accountID) {
        boolean withinMaxIncompleteTrades = tradeUtility.getTimesIncomplete(accountID) <= thresholdRepository.getMaxIncompleteTrade();
        boolean withinWeeklyLimit = tradeUtility.getNumWeeklyTrades(accountID) < thresholdRepository.getMaxWeeklyTrade();
        Roles role = getRoleOfAccount(accountID);
        return !hasPermission(accountID, Permissions.UNFREEZE) &&
                role != Roles.BANNED && role != Roles.FROZEN && (!withinMaxIncompleteTrades || !withinWeeklyLimit || !tradeUtility.lentMoreThanBorrowed(accountID));
    }

    /**
     * Check if an account is on a vacation.
     *
     * @param accountID An ID of an account
     * @return A boolean indicating whether account can go on a vacation
     */
    public boolean canVacation(int accountID) {
        return !canBeFrozen(accountID) && hasPermission(accountID, Permissions.TRADE) && hasPermission(accountID, Permissions.REQUEST_VACATION);
    }

    /**
     * Determines whether a given account can request to unfreeze and requests to unfreeze if it can.
     *
     * @param accountID Account to request to be unfrozen
     */
    public void requestUnfreeze(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.REQUEST_UNFREEZE);
        accountRepository.updateToAccountGateway(account, false);
    }

    /**
     * Check if an accountID has a certain permission.
     *
     * @param accountID The ID of the account checked
     * @param perm      A permission being checked
     * @return Whether the account has the permission.
     */
    public boolean hasPermission(int accountID, Permissions perm) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(perm);
    }

    /**
     * Change an Account's permissions to move it to vacation state.
     *
     * @param accountID The ID of the account
     */
    public void requestVacation(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.REQUEST_VACATION);
        account.removePermission(Permissions.TRADE);
        accountRepository.updateToAccountGateway(account, false);
    }

    /**
     * Finish an account's vacation, and return the account to a trading state.
     *
     * @param accountID The account returning from the vacation
     */
    public void completeVacation(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(Permissions.REQUEST_VACATION);
        account.addPermission(Permissions.TRADE);
        accountRepository.updateToAccountGateway(account, false);
    }

    /**
     * Ban an account, undoing the process of account creation.
     *
     * @param accountID The ID of the account
     */
    public void banAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.LOGIN);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Undo the banning of an account, returning them to their state before being banned.
     *
     * @param accountID An ID of an account being banned.
     */
    public void unBanAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(Permissions.LOGIN);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Promote an account to become a trusted community member.
     *
     * @param accountID The ID of the account being promoted
     */
    public void trustAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(Permissions.CONFIRM_ITEM);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Demote a trusted member to a normal account.
     *
     * @param accountID The ID of the account being demoted
     */
    public void unTrustAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.CONFIRM_ITEM);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Promote an account to a moderator.
     *
     * @param accountID The account being promoted
     */
    public void modAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        if (!account.getPermissions().contains(Permissions.CONFIRM_ITEM))
            account.addPermission(Permissions.CONFIRM_ITEM);
        account.addPermission(Permissions.REMOVE_WISHLIST);
        account.addPermission(Permissions.FREEZE);
        account.addPermission(Permissions.UNFREEZE);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Change a moderator to a normal account.
     *
     * @param accountID The ID of the account becoming a moderator
     */
    public void unModAccount(int accountID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(Permissions.CONFIRM_ITEM);
        account.removePermission(Permissions.REMOVE_WISHLIST);
        account.removePermission(Permissions.FREEZE);
        account.removePermission(Permissions.UNFREEZE);
        accountRepository.updateToAccountGateway(account, false);

    }

    /**
     * Get the role of the Account with a given ID.
     *
     * @param accountID The Account ID
     * @return The role of the account
     */
    public Roles getRoleOfAccount(int accountID) {
        List<Permissions> perms = accountRepository.getAccountFromID(accountID).getPermissions();
        if (!perms.contains(Permissions.LOGIN))
            return Roles.BANNED;
        if (!perms.contains(Permissions.REQUEST_VACATION))
            return Roles.VACATION;
        if (!perms.contains(Permissions.TRADE))
            return Roles.FROZEN;
        if (perms.contains(Permissions.ADD_ADMIN))
            return Roles.ADMIN;
        if (perms.contains(Permissions.FREEZE))
            return Roles.MOD;
        if (perms.contains(Permissions.CONFIRM_ITEM))
            return Roles.TRUSTED;
        return Roles.NORMAL;
    }

}
