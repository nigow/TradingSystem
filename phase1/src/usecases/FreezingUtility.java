package usecases;

import entities.Account;
import entities.Restrictions;
import gateways.RestrictionsGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the manager responsible for dealing with frozen accounts and managing restrictions
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
     * @param accountManager Gateway used to interact with persistent storage of accounts
     * @return a list of accounts to freeze
     */
    public List<Account> getAccountstofreeze(AccountManager accountManager){
        // LET ME KNOW if you think an instance of AccountManager should be stored, or handled differently than being passed as parameter
        return new ArrayList<>();
    }

    /**
     * Gets a list of accounts that have been frozen and have requested to be unfrozen
     * @param accountManager Gateway used to interact with persistent storage of accounts
     * @return a list of accounts to freeze
     */
    public List<Account> getAccountstoUnfreeze(AccountManager accountManager){
        return new ArrayList<>();
    }

    /**
     * Freezes an account by changing the role of the current account from TRADER to FROZEN
     */
    public void freezeAccount(){

    }

    /**
     * Unfreezes an account by changing the role of the current account from PENDING to TRADER
     */
    public void unfreezeAccount(){

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
        restrictions.setMaxIncompleteTrade(maxIncompleteTrade);
        restrictionsGateway.updateRestrictions(restrictions);
    }

    /**
     * Updates the restriction of the max number of weekly trades before an account is frozen
     * @param maxWeeklyTrade Max number of weekly trades
     */
    public void setMaxWeeklyTrade(int maxWeeklyTrade){
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
