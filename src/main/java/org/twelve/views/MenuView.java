package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.MenuController;
import org.twelve.presenters.MenuPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View for post login menu.
 *
 * @param <T> Presenter.
 */
public class MenuView<T extends ObservablePresenter & MenuPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final MenuController menuController;
    private final T menuPresenter;

    @FXML
    private Label currentUser;
    @FXML
    private GridPane graphic;
    @FXML
    private Button initiateTrade;
    @FXML
    private Button modifyThresholds;
    @FXML
    private Button manageAccounts;
    @FXML
    private Button addAdmin;
    @FXML
    private Button approveItems;
    @FXML
    private Button adminWishlist;
    @FXML
    private Button cancelTrades;

    /**
     * Constructor of view for post login menu.
     *
     * @param windowHandler  An instance of {@link org.twelve.views.WindowHandler}.
     * @param menuController Controller for managing access to further features.
     * @param menuPresenter  Presenter for displaying access errors.
     */
    public MenuView(WindowHandler windowHandler, MenuController menuController, T menuPresenter) {
        this.windowHandler = windowHandler;
        this.menuPresenter = menuPresenter;
        this.menuController = menuController;
        this.menuController.setMenuPresenter(this.menuPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            initiateTrade.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("initiateTrade").build().not());
            manageAccounts.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("manageAccounts").build());
            addAdmin.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("addAdmin").build());
            approveItems.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("approveItems").build());
            adminWishlist.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("adminWishlist").build());
            cancelTrades.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(menuPresenter).name("cancelTrades").build());

            currentUser.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(menuPresenter).name("currentUser").build());

        } catch (NoSuchMethodException ignored) {
        }
    }

    /**
     * Logs user out from their account.
     */
    @FXML
    private void logoutClicked() {

        windowHandler.changeScene(Scenes.LANDING);
        menuController.logout();

    }

    /**
     * Brings user to scene that lets them manage pending items.
     */
    @FXML
    private void approveItemsClicked() {

        windowHandler.changeScene(Scenes.WAREHOUSE);

    }

    /**
     * Brings user to scene that lets them view and change thresholds.
     */
    @FXML
    private void modifyThresholdsClicked() {

        windowHandler.changeScene(Scenes.THRESHOLDS);

    }

    /**
     * Brings user to scene that lets them make a new trade.
     */
    @FXML
    private void initiateTradeClicked() {

        windowHandler.changeScene(Scenes.TRADE_CREATOR);

    }

    /**
     * Brings user to scene that lets them make a new admin account.
     */
    @FXML
    private void addAdminClicked() {

        windowHandler.changeScene(Scenes.REGISTRATION);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        menuController.displayButtons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    /**
     * Brings user to a scene letting them manage their wishlist.
     */
    @FXML
    private void manageWishlistClicked() {

        windowHandler.changeScene(Scenes.WISHLIST);

    }

    /**
     * Brings user to a scene that lets them manage their inventory.
     */
    @FXML
    private void manageInventoryClicked() {

        windowHandler.changeScene(Scenes.INVENTORY);

    }

    /**
     * Brings user to a scene that lets them modify responsibilities of accounts.
     */
    public void manageAccountsClicked() {

        windowHandler.changeScene(Scenes.ACCOUNTS);

    }

    /**
     * Brings user to a scene that lets them manage their account settings.
     */
    @FXML
    private void accountSettingsClicked() {

        windowHandler.changeScene(Scenes.PROFILE);
    }

    /**
     * Brings user to a scene that lets them manage trades they're involved in.
     */
    @FXML
    private void manageExistingTradesClicked() {

        windowHandler.changeScene(Scenes.TRADE_LIST);
    }

    /**
     * Brings user to a scene reserved to admins that lets them manage others wishlists.
     */
    @FXML
    private void adminWishlistClicked() {
        windowHandler.changeScene(Scenes.ADMIN_WISHLIST);
    }

    /**
     * Brings user to a scene that lets them cancel trades.
     */
    @FXML
    private void cancelTradesClicked() {
        windowHandler.changeScene(Scenes.TRADE_CANCELLATION);
    }
}
