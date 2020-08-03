package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;
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

            ReadOnlyJavaBeanObjectProperty<List<String>> approved = ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create()
                    .bean(inventoryPresenter).name("approvedItems").build();
            ReadOnlyJavaBeanObjectProperty<List<String>> pending = ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create()
                    .bean(inventoryPresenter).name("pendingItems").build();

            ObjectBinding<ObservableList<String>> inventoryBinding = Bindings.createObjectBinding(() -> {

                List<String> allItems = new ArrayList<>(inventoryPresenter.getApprovedItems());
                allItems.addAll(inventoryPresenter.getPendingItems());

                return FXCollections.observableArrayList(allItems);

            }, approved, pending);

            inventoryItems.itemsProperty().bind(inventoryBinding);

            itemName.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(inventoryPresenter).name("selectedItemName").build());

            itemDesc.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(inventoryPresenter).name("selectedItemDesc").build());

        } catch (NoSuchMethodException ignored) {}

        inventoryItems.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (inventoryPresenter.getPendingItems().contains(item))
                            setTextFill(Color.GRAY);
                        setText(item);
                    }
                };
            }
        });

        // force recolor every time inventory is updated (doesn't happen by default if the contents are the same)
        inventoryItems.itemsProperty().addListener((observable, oldValue, newValue) -> {
            inventoryItems.refresh();
        });

        inventoryItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() != -1)
                inventoryController.setSelectedItem(newValue.intValue());

        });

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

        int itemIndex = inventoryItems.getSelectionModel().getSelectedIndex();

        if (itemIndex != -1) inventoryController.removeFromInventory(itemIndex);

    }
}
