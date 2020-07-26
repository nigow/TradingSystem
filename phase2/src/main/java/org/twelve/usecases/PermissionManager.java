package org.twelve.usecases;

import org.twelve.entities.Account;
import org.twelve.entities.Permissions;


import java.util.List;

public class PermissionManager {

    private AccountRepository accountRepository;

    public PermissionManager(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Adds a permission to the account by permissionID.
     *
     * @param accountID    Unique identifier of Account to add the permission to
     * @param permissionID Unique identifier of permission
     */
    public void addPermissionByID(int accountID, Permissions permissionID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.addPermission(permissionID);
        accountRepository.updateAccount(account);
    }

    /**
     * Adds a list of permissions to the account by permissionIDs.
     *
     * @param accountID       Unique identifier of Account to add the list of permissions to
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void addPermissionsByIDs(int accountID, List<Permissions> permissionIDs) {
        Account account = accountRepository.getAccountFromID(accountID);
        for (Permissions permissionID : permissionIDs) {
            account.addPermission(permissionID);
        }
        accountRepository.updateAccount(account);
    }

    /**
     * Remove a permission from the account by permissionID.
     *
     * @param accountID    Unique identifier of Account to remove the permission from
     * @param permissionID Unique identifier of permission
     */
    public void removePermissionByID(int accountID, Permissions permissionID) {
        Account account = accountRepository.getAccountFromID(accountID);
        account.removePermission(permissionID);
        accountRepository.updateAccount(account);
    }

    /**
     * Removes a list of permissions from the account by permissionsIDs.
     *
     * @param accountID     Unique identifier of Account to remove the list of permissions from
     * @param permissionIDs List of unique identifiers of permissions
     */
    public void removePermissionsByIDs(int accountID, List<Permissions> permissionIDs) {
        Account account = accountRepository.getAccountFromID(accountID);
        for (Permissions permissionID : permissionIDs) {
            account.removePermission(permissionID);
        }
        accountRepository.updateAccount(account);
    }

    /**
     * Determines whether a given account account can login.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can login
     * @return Whether the account can login or not
     */
    public boolean canLogin(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.LOGIN);
    }

    /**
     * Determines whether a given account can add to wishlist.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can add to wishlist
     * @return Whether the account can add to wishlist or not
     */
    public boolean canAddToWishlist(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.ADD_TO_WISHLIST);
    }

    /**
     * Determines whether a given account can create items.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can create items
     * @return Whether the account can create items or not
     */
    public boolean canCreateItem(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.CREATE_ITEM);
    }

    /**
     * Determines if account can browse the inventory.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can browse the inventory
     * @return Whether the account can browse the inventory
     */
    public boolean canBrowseInventory(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.BROWSE_INVENTORY);
    }

    /**
     * Determines whether a given account account can borrow items.
     *
     * @param accountID Unique identifier of Account that is checked if it can borrow items
     * @return Whether the account can borrow items
     */
    private boolean canBorrow(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.BORROW);
    }

    /**
     * Determines whether a given account account can lend items.
     *
     * @param accountID Unique identifier of Account that is checked if it can lend items
     * @return Whether the account can lend items
     */
    private boolean canLend(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.LEND);
    }

    /**
     * Determines whether a given account account can change trading restrictions.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can change restrictions
     * @return Whether the account can change restrictions
     */
    public boolean canChangeRestrictions(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.CHANGE_RESTRICTIONS);
    }

    /**
     * Determines whether a given account can add administrator accounts.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can add administrator accounts
     * @return Whether the account can add administrator accounts
     */
    public boolean canAddAdmin(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.ADD_ADMIN);
    }

    /**
     * Determines whether a given account account can confirm that an item can be added.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can confirm items
     * @return Whether the account can confirm items
     */
    public boolean canConfirmItem(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.CONFIRM_ITEM);
    }

    /**
     * Determines whether a given account account can freeze other accounts.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can freeze other accounts
     * @return Whether the account can freeze other accounts
     */
    public boolean canFreeze(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.FREEZE);
    }

    /**
     * Determines whether a given account can unfreeze other accounts.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can unfreeze other accounts
     * @return Whether the account can unfreeze other accounts
     */
    public boolean canUnfreeze(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.UNFREEZE);
    }

    /**
     * Determines whether a given account can request to be unfrozen.
     *
     * @param accountID Unique identifier of Account that is checked it see if it can request to be unfrozen
     * @return Whether the account can request to be unfrozen
     */
    public boolean canRequestUnfreeze(int accountID) {
        return accountRepository.getAccountFromID(accountID).getPermissions().contains(Permissions.REQUEST_UNFREEZE);
    }

}
