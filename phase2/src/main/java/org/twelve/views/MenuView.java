package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.MenuController;
import org.twelve.presenters.MenuPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView<T extends ObservablePresenter & MenuPresenter> implements SceneView, Initializable{

    private final WindowHandler windowHandler;
    private final MenuController menuController;
    private final T menuPresenter;

    @FXML
    private GridPane graphic;
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

    public MenuView(WindowHandler windowHandler, MenuController menuController, T menuPresenter) {
        this.windowHandler = windowHandler;
        this.menuPresenter = menuPresenter;
        this.menuController = menuController;
        this.menuController.setMenuPresenter(this.menuPresenter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            initiateTrade.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                .bean(menuPresenter).name("initiateTrade").build().not());
            modifyRestrictions.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("modifyRestrictions").build());
            manageAccounts.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("manageAccounts").build());
            addAdmin.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("addAdmin").build());
            approveItems.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("approveItems").build());


    } catch (NoSuchMethodException ignored) {}
    }

    @FXML
    private void logoutClicked() {

        windowHandler.changeScene(Scenes.LANDING);
        menuController.logout();

    }

    @FXML
    private void approveItemsClicked() {

        windowHandler.changeScene(Scenes.WAREHOUSE);

    }

    @FXML
    private void modifyRestrictionsClicked() {

        windowHandler.changeScene(Scenes.RESTRICTIONS);

    }

    @FXML
    private void initiateTradeClicked() {

        windowHandler.changeScene(Scenes.TRADE_CREATOR);

    }

    @FXML
    private void addAdminClicked() {

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
    private void manageWishlistClicked() {

        windowHandler.changeScene(Scenes.WISHLIST);

    }

    @FXML
    private void manageInventoryClicked() {

        windowHandler.changeScene(Scenes.INVENTORY);

    }

    public void manageAccountsClicked() {

        windowHandler.changeScene(Scenes.ACCOUNTS);

    }

    @FXML
    private void accountSettingsClicked() {

        windowHandler.changeScene(Scenes.PROFILE);
    }
}
