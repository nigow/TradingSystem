package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LandingView implements SceneView {

    private WindowHandler windowHandler;

    public LandingView(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    @FXML
    private void loginClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LOGIN);

    }

    @FXML
    private void registerClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.REGISTRATION);

    }
}
