package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.twelve.controllers.WishlistController;
import org.twelve.presenters.WishlistPresenter;

public class WishlistView implements SceneView {

    private final WindowHandler windowHandler;
    private final WishlistController wishlistController;
    private final WishlistPresenter wishlistPresenter;

    @FXML
    private ListView<String> wishlistItems;

    @FXML
    private ListView<String> warehouseItems;

    public WishlistView(WindowHandler windowHandler, WishlistController wishlistController, WishlistPresenter wishlistPresenter) {
        this.windowHandler = windowHandler;
        this.wishlistController = wishlistController;
        this.wishlistPresenter = wishlistPresenter;
    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void reload() {
        wishlistController.updateItems();

        wishlistItems.getItems().clear();
        wishlistItems.getItems().addAll(wishlistPresenter.getWishlistItems());

        warehouseItems.getItems().clear();
        warehouseItems.getItems().addAll(wishlistPresenter.getWarehouseItems());

    }
}
