package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.WishlistController;
import org.twelve.presenters.WishlistPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing personal wishlist.
 * @param <T> Presenter.
 */
public class WishlistView<T extends ObservablePresenter & WishlistPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final WishlistController wishlistController;

    @FXML
    private Label itemOwner;

    @FXML
    private Button removeFromWishlistBtn;

    @FXML
    private Button addToWishlistBtn;

    @FXML
    private GridPane graphic;

    @FXML
    private Label itemName;

    @FXML
    private Label itemDesc;

    private final T wishlistPresenter;

    @FXML
    private ListView<String> wishlistItems;

    @FXML
    private ListView<String> warehouseItems;

    /**
     * Constructor of view for managing personal wishlist.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param wishlistController Controller for adding/removing items from personal wishlist.
     * @param wishlistPresenter Presenter for displaying wishlist and system items.
     */
    public WishlistView(WindowHandler windowHandler, WishlistController wishlistController, T wishlistPresenter) {
        this.windowHandler = windowHandler;
        this.wishlistController = wishlistController;
        this.wishlistPresenter = wishlistPresenter;

        this.wishlistController.setWishlistPresenter(this.wishlistPresenter);
    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {

        wishlistController.updateItems();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ReadOnlyJavaBeanObjectProperty<List<String>> wishlistItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(wishlistPresenter).name("wishlistItems").build();

            ObjectBinding<ObservableList<String>> wishlistBinding = Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(wishlistItemsBinding.get()), wishlistItemsBinding);

            wishlistItems.itemsProperty().bind(wishlistBinding);

            ReadOnlyJavaBeanObjectProperty<List<String>> warehouseItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(wishlistPresenter).name("localItems").build();

            ObjectBinding<ObservableList<String>> warehouseBinding = Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(warehouseItemsBinding.get()), warehouseItemsBinding);

            warehouseItems.itemsProperty().bind(warehouseBinding);

            itemName.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(wishlistPresenter).name("selectedItemName").build());

            itemDesc.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(wishlistPresenter).name("selectedItemDesc").build());

            itemOwner.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(wishlistPresenter).name("selectedItemOwner").build());

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

        addToWishlistBtn.disableProperty().bind(warehouseItems.getSelectionModel().selectedItemProperty().isNull());
        removeFromWishlistBtn.disableProperty().bind(wishlistItems.getSelectionModel().selectedItemProperty().isNull());

    }

    @FXML
    private void addToWishlistClicked() {

        wishlistController.addToWishlist(warehouseItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void removeFromWishlistClicked() {

        wishlistController.removeFromWishlist(wishlistItems.getSelectionModel().getSelectedIndex());

    }

}
