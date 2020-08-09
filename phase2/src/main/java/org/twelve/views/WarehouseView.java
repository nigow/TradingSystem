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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.twelve.controllers.WarehouseController;
import org.twelve.presenters.WarehousePresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseView<T extends ObservablePresenter & WarehousePresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final WarehouseController warehouseController;
    private final T warehousePresenter;

    @FXML
    private Button approveBtn;

    @FXML
    private Button denyBtn;

    @FXML
    private BorderPane graphic;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemDescLabel;

    @FXML
    private ListView<String> warehouseItems;

    public WarehouseView(WindowHandler windowHandler, WarehouseController warehouseController, T warehousePresenter) {

        this.windowHandler = windowHandler;
        this.warehouseController = warehouseController;
        this.warehousePresenter = warehousePresenter;

        this.warehouseController.setWarehousePresenter(this.warehousePresenter);

    }

    @Override
    public void reload() {

        warehouseController.updateWarehouseItems();

    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void approveClicked() {

        warehouseController.approveItem(warehouseItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void denyClicked() {

        warehouseController.denyItem(warehouseItems.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void backClicked() {

        windowHandler.changeScene(Scenes.MENU);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ReadOnlyJavaBeanObjectProperty<List<String>> pendingItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(warehousePresenter).name("pendingItems").build();

            ReadOnlyJavaBeanObjectProperty<List<String>> approvedItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(warehousePresenter).name("approvedItems").build();

            ObjectBinding<ObservableList<String>> binding = Bindings.createObjectBinding(() -> {

                List<String> warehouseItems = new ArrayList<>(pendingItemsBinding.get());
                warehouseItems.addAll(approvedItemsBinding.get());

                return FXCollections.observableArrayList(warehouseItems);

            }, pendingItemsBinding, approvedItemsBinding);

            warehouseItems.itemsProperty().bind(binding);

            itemNameLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(warehousePresenter).name("selectedItemName").build());
            itemDescLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(warehousePresenter).name("selectedItemDesc").build());

        } catch (NoSuchMethodException ignored) {} // impossible, im enforcing presence of methods via generics

        warehouseItems.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            for (int i = 0; i < warehouseItems.getItems().size(); i++) {

                                // comparison by reference is intentional to determine which specific item it is
                                //noinspection StringEquality
                                if (warehouseItems.getItems().get(i) == item) {

                                    if (i < warehousePresenter.getPendingItems().size()) {
                                        setTextFill(Color.GRAY);
                                    } else {
                                        setTextFill(Color.BLACK);
                                    }

                                }


                            }
                            setText(item);
                        }
                    }
                };
            }
        });

        // force recolor every time inventory is updated (doesn't happen by default if the contents are the same)
        warehouseItems.itemsProperty().addListener((observable, oldValue, newValue) -> warehouseItems.refresh());

        warehouseItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() != -1) warehouseController.changeSelectedItem(newValue.intValue());

        });

        approveBtn.disableProperty().bind(warehouseItems.getSelectionModel().selectedItemProperty().isNull());
        denyBtn.disableProperty().bind(warehouseItems.getSelectionModel().selectedItemProperty().isNull());

    }

}
