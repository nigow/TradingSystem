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
import org.twelve.controllers.WarehouseController;
import org.twelve.presenters.UIWarehousePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseView implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final WarehouseController warehouseController;
    private UIWarehousePresenter warehousePresenter;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemDescLabel;

    @FXML
    private ListView<String> pendingItems;

    public WarehouseView(WindowHandler windowHandler, WarehouseController warehouseController) {

        this.windowHandler = windowHandler;
        this.warehouseController = warehouseController;

    }

    @Override
    public void reload() {

        warehouseController.updatePendingItems();

    }

    @FXML
    private void approveClicked(ActionEvent actionEvent) {

        warehouseController.approveItem(pendingItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warehousePresenter = new UIWarehousePresenter(resources);
        warehouseController.setWarehousePresenter(warehousePresenter);

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

        pendingItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            warehouseController.changeSelectedItem(newValue.intValue());

        });


    }
}
