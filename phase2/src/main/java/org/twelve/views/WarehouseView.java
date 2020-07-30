package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import org.twelve.controllers.WarehouseController;
import org.twelve.presenters.UIWarehousePresenter;

import java.util.List;

public class WarehouseView implements SceneView {

    private final WindowHandler windowHandler;
    private final WarehouseController warehouseController;
    private final UIWarehousePresenter warehousePresenter;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemDescLabel;

    @FXML
    private ListView<String> pendingItems;

    public WarehouseView(WindowHandler windowHandler, WarehouseController warehouseController, UIWarehousePresenter warehousePresenter) {

        this.windowHandler = windowHandler;
        this.warehouseController = warehouseController;
        this.warehousePresenter = warehousePresenter;

    }

    public void initialize() {

        try {

            // workaround for lack of casting (or custom return) in JavaBean*PropertyBuilder
            // there is a type incompatibility between native observer classes and javafx controls (javafx controls will ONLY work with its own observer classes)
            ObjectBinding<ObservableList<String>> binding = Bindings.<ObservableList<String>>createObjectBinding(() -> {

                return FXCollections.observableArrayList(warehousePresenter.getPendingItems());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(warehousePresenter).name("pendingItems").build());

            pendingItems.itemsProperty().bind(binding);

            itemNameLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(warehousePresenter).name("selectedItemName").build());
            itemDescLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(warehousePresenter).name("selectedItemDesc").build());

        } catch (NoSuchMethodException ignored) {
            // impossible, im enforcing presence of methods via interface
        }

        // there's a feature that allows this to be done in fxml but it's bugged at this time -.-
        pendingItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @Override
    public void reload() {

        warehouseController.updatePendingItems();
        // pendingItems.getItems().clear();
        // pendingItems.getItems().addAll(warehousePresenter.getPendingItems());

    }

    @FXML
    private void approveClicked(ActionEvent actionEvent) {

        warehouseController.approveItem(pendingItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void selectedItemChanged(MouseEvent mouseEvent) {

        int selectedIndex = pendingItems.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            warehouseController.changeSelectedItem(pendingItems.getSelectionModel().getSelectedIndex());

            // itemNameLabel.setText(warehousePresenter.getSelectedItemName());
            // itemDescLabel.setText(warehousePresenter.getSelectedItemDesc());
        }

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }
}
