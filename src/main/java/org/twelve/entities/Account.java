package org.twelve.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an account.
 */
public class Account {

    private final String username;
    private String password;
    private final List<Integer> wishlist;
    private final List<Permissions> permissions;
    private final int accountID;
    private String location;

    /**
     * Creates a new account to the system.
     * The wishlist is initialized as an empty Arraylist.
     *
     * @param username    Username of this account
     * @param password    Password for this account
     * @param permissions What this account is allowed to do
     * @param accountID   Unique identifier of this account
     * @param location    The location of the user of this account
     */
    public Account(String username, String password, List<Permissions> permissions, int accountID, String location) {
        this.username = username;
        this.password = password;
        this.wishlist = new ArrayList<>();
        this.permissions = permissions;
        this.accountID = accountID;
        this.location = location;
    }

    /**
     * An overloaded constructor.
     * Used to create accounts that already exist in the system.
     *
     * @param username    Username of this account
     * @param password    Password for this account
     * @param wishlist    Wishlist that this account should be initialized with
     * @param permissions What this account is allowed to do
     * @param accountID   Unique identifier of this account
     * @param location    The location of this account
     */
    public Account(String username, String password, List<Integer> wishlist, List<Permissions> permissions,
                   int accountID, String location) {
        this(username, password, permissions, accountID, location);
        this.wishlist.addAll(wishlist);
    }

    /**
     * Gets the username of the account.
     *
     * @return Username of the account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of this account.
     *
     * @return Password of the account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a new password.
     *
     * @param password New password that'll be assigned to this account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the wishlist of this account.
     *
     * @return Wishlist of the account
     */
    public List<Integer> getWishlist() {
        return wishlist;
    }


    /**
     * Get the accountID of this account.
     *
     * @return Unique identifier of the account
     */
    public int getAccountID() {
        return accountID;
    }

    /**
     * Get all permissions that this account has.
     *
     * @return list of permissions this account has
     */
    public List<Permissions> getPermissions() {
        return permissions;
    }

    /**
     * Add a permission to the list of permissions.
     *
     * @param permission The new permission that'll be added to this account
     */
    public void addPermission(Permissions permission) {
        permissions.add(permission);
    }

    /**
     * Remove given permission from user's list of permissions.
     *
     * @param permission the permission that should be removed from this account
     * @return true if the permission has been removed
     */
    public boolean removePermission(Permissions permission) {
        return permissions.remove(permission);
    }


    /**
     * Adds an item to the end of the wishlist.
     *
     * @param itemID The ID of the item to be added to this wishlist
     */
    public void addToWishlist(int itemID) {
        wishlist.add(itemID);
    }

    /**
     * Remove an item from the wishlist.
     *
     * @param itemID The ID of the item to be removed from this wishlist
     * @return true if the item has been removed
     */
    public boolean removeFromWishList(int itemID) {
        return wishlist.remove(Integer.valueOf(itemID));
    }

    /**
     * Get the location of the account user
     *
     * @return location of user
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets a new location
     *
     * @param location the new location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Creates a string representation of this Account.
     *
     * @return A string representation of this Account object
     */
    @Override
    public String toString() {
        return this.username;
    }
}
