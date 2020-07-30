package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WishlistView implements SceneView {

    private final WindowHandler windowHandler;

    public WishlistView(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void reload() {

    }
}
