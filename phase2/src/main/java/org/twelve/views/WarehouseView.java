package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import org.twelve.controllers.WarehouseController;
import org.twelve.presenters.WarehousePresenter;

public class WarehouseView implements SceneView {

    private final WindowHandler windowHandler;
    private final WarehouseController warehouseController;
    private final WarehousePresenter warehousePresenter;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemDescLabel;

    @FXML
    private ListView<String> pendingItems;

    public WarehouseView(WindowHandler windowHandler, WarehouseController warehouseController, WarehousePresenter warehousePresenter) {

        this.windowHandler = windowHandler;
        this.warehouseController = warehouseController;
        this.warehousePresenter = warehousePresenter;

    }

    @Override
    public void reload() {

    }

    @FXML
    private void approveClicked(ActionEvent actionEvent) {

        warehouseController.approveItem(pendingItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void selectedItemChanged(MouseEvent mouseEvent) {

        // there's a feature that allows this to be done in fxml but it's bugged at this time -.-
        pendingItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        warehouseController.changeSelectedItem(pendingItems.getSelectionModel().getSelectedIndex());

        itemNameLabel.setText(warehousePresenter.getSelectedItemName());
        itemDescLabel.setText(warehousePresenter.getSelectedItemDesc());

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }
}
