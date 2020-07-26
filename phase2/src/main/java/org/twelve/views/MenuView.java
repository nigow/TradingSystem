package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MenuView implements SceneView {
    private WindowHandler windowHandler;

    @Override
    public void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    public void logoutClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LANDING);

    }

    public void approveItemsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.WAREHOUSE);

    }

    public void modifyRestrictionsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.RESTRICTIONS);

    }

    public void initiateTradeClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.TRADE_CREATOR);

    }
}
