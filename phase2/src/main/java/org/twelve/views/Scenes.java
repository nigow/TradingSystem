package org.twelve.views;

/**
 * Constants representing each JavaFX scene.
 */
public enum Scenes {

    LANDING("Landing"),
    LOGIN("Login"),
    MENU("Menu"),
    WAREHOUSE("Warehouse"),
    THRESHOLDS("Thresholds"),
    PROFILE("Profile"),
    TRADE_CREATOR("TradeCreator"),
    REGISTRATION("Registration"),
    WISHLIST("Wishlist"),
    INVENTORY("Inventory"),
    ACCOUNTS("Accounts"),
    TRADE_COLLECTION("TradeCollection"),
    ADMIN_WISHLIST("AdminWishlist");

    private final String fxml;

    // enum constructors are private thus this doesn't need a JavaDoc
    Scenes(String fxml) {
        this.fxml = fxml;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.fxml;
    }
}
