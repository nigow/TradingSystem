package org.twelve.entities;

/**
 * Different permissions an account can have.
 *
 * @author Michael
 */
public enum Permissions {
    LOGIN,
    FREEZE,
    UNFREEZE,
    CREATE_ITEM,
    CONFIRM_ITEM,
    ADD_TO_WISHLIST,
    REMOVE_WISHLIST,
    CANCEL_TRADE,
    TRADE,
    BROWSE_INVENTORY,
    CHANGE_THRESHOLDS,
    ADD_ADMIN,
    REQUEST_UNFREEZE,
    REQUEST_VACATION,
    CAN_BAN,
    MAKE_TRUSTED
}
