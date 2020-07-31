package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.twelve.controllers.MenuController;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView implements SceneView {
    private WindowHandler windowHandler;
    private MenuController menuController;

    public MenuView(WindowHandler windowHandler, MenuController menuController) {
        this.windowHandler = windowHandler;
        this.menuController = menuController;
    }

    @FXML
    private void logoutClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LANDING);
        menuController.logout();

    }

    @FXML
    private void approveItemsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.WAREHOUSE);

    }

    @FXML
    private void modifyRestrictionsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.RESTRICTIONS);

    }

    @FXML
    private void initiateTradeClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.TRADE_CREATOR);

    }

    @FXML
    private void addAdminClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.REGISTRATION);

    }

    @Override
    public void reload() {

    }

    @FXML
    private void manageWishlistClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.WISHLIST);

    }

    @FXML
    private void manageAccountClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.PROFILE);

    }

    @FXML
    private void manageInventoryClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.INVENTORY);

    }
}
