package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingView implements SceneView {

    @FXML
    private GridPane graphic;

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

    @Override
    public void reload() {

    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }
}
