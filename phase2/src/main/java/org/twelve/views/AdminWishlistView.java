package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.AdminWishlistController;
import org.twelve.presenters.AdminWishlistPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminWishlistView<T extends ObservablePresenter & AdminWishlistPresenter> implements SceneView, Initializable {


    private WindowHandler windowHandler;

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


    public AdminWishlistView(WindowHandler windowHandler, AdminWishlistController adminWishlistController,
                            T adminWishlistPresenter) {

        this.windowHandler = windowHandler;
        this.adminWishlistController = adminWishlistController;
        this.adminWishlistPresenter = adminWishlistPresenter;
        this.adminWishlistController.setAdminWishlistPresenter(this.adminWishlistPresenter);
    }

    @Override
    public void reload() {
        allUsers.getSelectionModel().clearSelection();
        adminWishlistController.updateWishlist(allUsers.getSelectionModel().getSelectedItem());
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> allAccounts = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(adminWishlistPresenter.getAllUsers());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(adminWishlistPresenter).name("allUsers").build());
            allUsers.itemsProperty().bind(allAccounts);

            ObjectBinding<ObservableList<String>> accountWishlist = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(adminWishlistPresenter.getWishlistOfUser());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(adminWishlistPresenter).name("wishlistOfUser").build());
            wishlistOfUser.itemsProperty().bind(accountWishlist);

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
    public void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    public void removeClicked() {
        adminWishlistController.removeFromWishlist(allUsers.getSelectionModel().getSelectedItem(), wishlistOfUser.getSelectionModel().getSelectedIndex());
    }

}