package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.twelve.controllers.WishlistController;
import org.twelve.presenters.WishlistPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WishlistView<T extends ObservablePresenter & WishlistPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final WishlistController wishlistController;

    @FXML
    private Label itemName;

    @FXML
    private Label itemDesc;

    private final T wishlistPresenter;

    @FXML
    private ListView<String> wishlistItems;

    @FXML
    private ListView<String> warehouseItems;

    public WishlistView(WindowHandler windowHandler, WishlistController wishlistController, T wishlistPresenter) {
        this.windowHandler = windowHandler;
        this.wishlistController = wishlistController;
        this.wishlistPresenter = wishlistPresenter;

        this.wishlistController.setWishlistPresenter(this.wishlistPresenter);
    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void reload() {

        wishlistController.updateItems();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> wishlistBinding = Bindings.<ObservableList<String>>createObjectBinding(() -> {

                return FXCollections.observableArrayList(wishlistPresenter.getWishlistItems());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(wishlistPresenter).name("wishlistItems").build());

            wishlistItems.itemsProperty().bind(wishlistBinding);

            ObjectBinding<ObservableList<String>> warehouseBinding = Bindings.<ObservableList<String>>createObjectBinding(() -> {

                return FXCollections.observableArrayList(wishlistPresenter.getWarehouseItems());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(wishlistPresenter).name("warehouseItems").build());

            warehouseItems.itemsProperty().bind(warehouseBinding);

            itemName.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(wishlistPresenter).name("selectedItemName").build());

            itemDesc.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(wishlistPresenter).name("selectedItemDesc").build());

        } catch (NoSuchMethodException ignored) {}

        wishlistItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() != -1) {
                warehouseItems.getSelectionModel().clearSelection();
                wishlistController.changeSelectedWishlistItem(newValue.intValue());
            }

        });

        warehouseItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() != -1) {
                wishlistItems.getSelectionModel().clearSelection();
                wishlistController.changeSelectedWarehouseItem(newValue.intValue());
            }

        });

    }

    @FXML
    private void addToWishlistClicked(ActionEvent actionEvent) {

        int itemIndex = warehouseItems.getSelectionModel().getSelectedIndex();

        if (itemIndex != -1) wishlistController.addToWishlist(itemIndex);

    }

    @FXML
    private void removeFromWishlistClicked(ActionEvent actionEvent) {

        int itemIndex = wishlistItems.getSelectionModel().getSelectedIndex();

        if (itemIndex != -1) wishlistController.removeFromWishlist(itemIndex);
    }

}
