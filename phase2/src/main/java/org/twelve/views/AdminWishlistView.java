package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.AdminWishlistController;
import org.twelve.presenters.AdminWishlistPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for admin's wishlist.
 * @param <T> Presenter.
 */
public class AdminWishlistView<T extends ObservablePresenter & AdminWishlistPresenter> implements SceneView, Initializable {


    private final WindowHandler windowHandler;

    private final AdminWishlistController adminWishlistController;
    private final T adminWishlistPresenter;


    @FXML
    private GridPane graphic;
    @FXML
    private Button removeButton;
    @FXML
    private ListView<String> allUsers;
    @FXML
    private ListView<String> wishlistOfUser;
    @FXML
    private Label itemDescription;

    /**
     * Constructor of view for managing wishlist.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param adminWishlistController Controller for managing wishlist.
     * @param adminWishlistPresenter Presenter for displaying wishlist.
     */
    public AdminWishlistView(WindowHandler windowHandler, AdminWishlistController adminWishlistController,
                            T adminWishlistPresenter) {

        this.windowHandler = windowHandler;
        this.adminWishlistController = adminWishlistController;
        this.adminWishlistPresenter = adminWishlistPresenter;
        this.adminWishlistController.setAdminWishlistPresenter(this.adminWishlistPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        allUsers.getSelectionModel().clearSelection();
        adminWishlistController.updateWishlist(allUsers.getSelectionModel().getSelectedItem());
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

            ReadOnlyJavaBeanObjectProperty<List<String>> allAccounts =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(adminWishlistPresenter).name("allUsers").build();

            allUsers.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(allAccounts.get()), allAccounts));

            ReadOnlyJavaBeanObjectProperty<List<String>> accountsWishlist =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(adminWishlistPresenter).name("wishlistOfUser").build();

            wishlistOfUser.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(accountsWishlist.get()), accountsWishlist));

            itemDescription.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(adminWishlistPresenter).name("selectedItemDescription").build());

        } catch (NoSuchMethodException ignored) {System.out.println("failure");}

        allUsers.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                adminWishlistController.changeSelectedUser(newValue.intValue());
            }
        }));

        wishlistOfUser.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                adminWishlistController.changeSelectedItemToRemove(allUsers.getSelectionModel().getSelectedItem(), newValue.intValue());
            }
        }));

        removeButton.disableProperty().bind(wishlistOfUser.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void removeClicked() {
        adminWishlistController.removeFromWishlist(allUsers.getSelectionModel().getSelectedItem(), wishlistOfUser.getSelectionModel().getSelectedIndex());
    }

}