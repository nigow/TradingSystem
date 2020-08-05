package org.twelve.presenters;

import java.util.List;

/**
 * Interface for showing possible actions.
 *
 * @author Catherine
 */
public interface OldMenuPresenter {
    /**
     * Displays all possible actions for the user.
     *
     * @param menuOptions Possible actions
     * @return Index of chosen action
     */
    String displayMenu(List<String> menuOptions);

    /**
     * Tells user that their input was invalid.
     */
    void invalidInput();

    /**
     * Shows user they can manage their trade.
     * @return the string message
     */
    String manageTrades();

    /**
     * Shows user they can browse the inventory.
     * @return the string message
     */
    String browseInventory();

    /**
     * Shows user they can manage their wishlist.
     * @return the string message
     */
    String manageWishlist();

    /**
     * Shows user they can create a trade.
     * @return the string message
     */
    String initiateTrade();

    /**
     * Shows user they can modify thresholds.
     * @return the string message
     */
    String modifyThresholds();

    /**
     * Shows user they can manage frozen users.
     * @return the string message
     */
    String manageFrozen();

    /**
     * Shows user they can add an admin account.
     * @return the string message
     */
    String addAdmin();

    /**
     * Shows user they can request to be unfrozen or request to go on vacation or to complete a vacation.
     * @return the string message
     */
    String requestAppeal();

    /**
     * Shows user they can log out.
     * @return the string message
     */
    String logout();


}