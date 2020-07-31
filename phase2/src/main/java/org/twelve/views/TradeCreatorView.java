package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TradeCreatorView implements SceneView {

    private WindowHandler windowHandler;

    public TradeCreatorView(WindowHandler windowHandler) {

        this.windowHandler = windowHandler;

    }

    @Override
    public void reload() {

    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }
}
