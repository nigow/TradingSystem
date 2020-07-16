package views;

public enum Scenes {

    LANDING("LandingView.fxml");

    private final String fxml;

    Scenes(String fxml) {
        this.fxml = fxml;
    }

    @Override
    public String toString() {
        return this.fxml;
    }
}
