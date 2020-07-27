package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RegistrationView implements SceneView {

    private WindowHandler windowHandler;

    public RegistrationView(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LANDING);

    }

}
