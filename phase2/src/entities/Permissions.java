package entities;

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
    LEND,
    BORROW,
    BROWSE_INVENTORY,
    CHANGE_RESTRICTIONS,
    ADD_ADMIN,
    REQUEST_UNFREEZE
}