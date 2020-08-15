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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.twelve.controllers.InventoryController;
import org.twelve.presenters.InventoryPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing personal inventory.
 * @param <T> Presenter.
 */
public class InventoryView<T extends ObservablePresenter & InventoryPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final InventoryController inventoryController;
    private final T inventoryPresenter;

    @FXML
    private Button addItemBtn;

    @FXML
    private Button removeItemBtn;

    @FXML
    private BorderPane graphic;

    @FXML
    private TextField itemName;

    @FXML
    private TextField itemDesc;

    @FXML
    private ListView<String> inventoryItems;

    /**
     * Constructor of view for managing personal inventory.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param inventoryController Controller for managing personal inventory.
     * @param inventoryPresenter Presenter for displaying inventory items.
     */
    public InventoryView(WindowHandler windowHandler, InventoryController inventoryController, T inventoryPresenter) {

        this.windowHandler = windowHandler;
        this.inventoryPresenter = inventoryPresenter;
        this.inventoryController = inventoryController;

        this.inventoryController.setInventoryPresenter(this.inventoryPresenter);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {

        inventoryController.displayAllYourInventory();
        itemName.clear();
        itemDesc.clear();
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

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            for (int i = 0; i < inventoryItems.getItems().size(); i++) {

                                // comparison by reference is intentional to determine which specific item it is
                                //noinspection StringEquality
                                if (inventoryItems.getItems().get(i) == item) {

                                    if (i < inventoryPresenter.getApprovedItems().size()) {
                                        setTextFill(Color.BLACK);
                                    } else {
                                        setTextFill(Color.GRAY);
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
        inventoryItems.itemsProperty().addListener((observable, oldValue, newValue) -> inventoryItems.refresh());

        inventoryItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.intValue() != -1) inventoryController.setSelectedItem(newValue.intValue());

        });

        addItemBtn.disableProperty().bind(Bindings.createBooleanBinding(() ->
                itemName.getText().isBlank() || itemDesc.getText().isBlank(),
                itemName.textProperty(), itemDesc.textProperty()));

        removeItemBtn.disableProperty().bind(inventoryItems.getSelectionModel().selectedItemProperty().isNull());

    }

    @FXML
    private void backClicked() {

        windowHandler.changeScene(Scenes.MENU);

    }

    @FXML
    private void addItemClicked() {

        inventoryController.createItem(itemName.getText(), itemDesc.getText());
        itemName.clear();
        itemDesc.clear();

    }

    @FXML
    private void removeItemClicked() {

        inventoryController.removeFromInventory(inventoryItems.getSelectionModel().getSelectedIndex());

    }
}
