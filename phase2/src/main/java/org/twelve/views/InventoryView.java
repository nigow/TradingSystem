package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.twelve.controllers.InventoryController;
import org.twelve.presenters.InventoryPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryView<T extends ObservablePresenter & InventoryPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final InventoryController inventoryController;
    private final T inventoryPresenter;

    @FXML
    private TextField itemName;

    @FXML
    private TextField itemDesc;

    @FXML
    private ListView<String> inventoryItems;

    public InventoryView(WindowHandler windowHandler, InventoryController inventoryController, T inventoryPresenter) {

        this.windowHandler = windowHandler;
        this.inventoryPresenter = inventoryPresenter;
        this.inventoryController = inventoryController;

        this.inventoryController.setInventoryPresenter(this.inventoryPresenter);

    }

    @Override
    public void reload() {

        inventoryController.displayAllYourInventory();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            // don't inline this, it won't work
            ReadOnlyJavaBeanObjectProperty<List<String>> prop1 = ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create()
                    .bean(inventoryPresenter).name("approvedItems").build();
            ReadOnlyJavaBeanObjectProperty<List<String>> prop2 = ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create()
                    .bean(inventoryPresenter).name("pendingItems").build();

            ObjectBinding<ObservableList<String>> inventoryBinding = Bindings.<ObservableList<String>>createObjectBinding(() -> {

                List<String> allItems = new ArrayList<>(inventoryPresenter.getApprovedItems());
                allItems.addAll(inventoryPresenter.getPendingItems());

                return FXCollections.observableArrayList(allItems);

            }, prop1, prop2);

            inventoryItems.itemsProperty().bind(inventoryBinding);

        } catch (NoSuchMethodException ignored) {}

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }

    @FXML
    private void addItemClicked(ActionEvent actionEvent) {

        inventoryController.createItem(itemName.getText(), itemDesc.getText());

    }

    @FXML
    private void removeItemClicked(ActionEvent actionEvent) {



    }
}
