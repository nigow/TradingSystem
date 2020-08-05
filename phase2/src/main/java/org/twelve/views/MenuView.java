package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.MenuController;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.presenters.MenuPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView<T extends ObservablePresenter & ProfilePresenter> implements SceneView, Initializable{

    @FXML
    private GridPane graphic;

    private WindowHandler windowHandler;
    private MenuController menuController;
    private MenuPresenter menuPresenter;

    @FXML
    private Button initiateTrade;
    @FXML
    private Button modifyRestrictions;
    @FXML
    private Button manageAccounts;
    @FXML
    private Button addAdmin;
    @FXML
    private Button approveItems;


    public MenuView(WindowHandler windowHandler, MenuController menuController, MenuPresenter menuPresenter) {
        this.windowHandler = windowHandler;
        this.menuPresenter = menuPresenter;
        this.menuController = menuController;
        this.menuController.setMenuPresenter(this.menuPresenter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            initiateTrade.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                .bean(menuPresenter).name("initiateTrade").build());
            modifyRestrictions.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("modifyRestrictions").build().not());
            manageAccounts.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("manageAccounts").build().not());
            addAdmin.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("addAdmin").build().not());
            approveItems.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("approveItems").build().not());

    } catch (NoSuchMethodException ignored) {}
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
        menuController.displayButtons();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void manageWishlistClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.WISHLIST);

    }

    @FXML
    private void manageInventoryClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.INVENTORY);

    }

    public void manageAccountsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.ACCOUNTS);

    }

    @FXML
    private void accountSettingsClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.PROFILE);
    }
}
