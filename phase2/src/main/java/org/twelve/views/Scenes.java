package org.twelve.views;

public enum Scenes {

    LANDING("Landing"),
    LOGIN("Login"),
    MENU("Menu"),
    WAREHOUSE("Warehouse"),
    RESTRICTIONS("Restrictions"),
    PROFILE("Profile"),
    TRADE_CREATOR("TradeCreator"),
    REGISTRATION("Registration"),
    WISHLIST("Wishlist"),
    INVENTORY("Inventory"),
    ACCOUNTS("Accounts");

    private final String fxml;

    Scenes(String fxml) {
        this.fxml = fxml;
    }

    @Override
    public String toString() {
        return this.fxml;
    }
}
