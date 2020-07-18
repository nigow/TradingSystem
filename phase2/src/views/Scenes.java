package views;

public enum Scenes {

    LANDING("Landing"),
    LOGIN("Login"),
    MENU("Menu"),
    WAREHOUSE("Warehouse"),
    RESTRICTIONS("Restrictions");

    private final String fxml;

    Scenes(String fxml) {
        this.fxml = fxml;
    }

    @Override
    public String toString() {
        return this.fxml;
    }
}
