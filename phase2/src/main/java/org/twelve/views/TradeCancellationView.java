package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeCancellationController;
import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeCancellationView <T extends ObservablePresenter & TradeCancellationPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final TradeCancellationController tradeCancellationController;
    private final T tradeCancellationPresenter;

    @FXML
    private GridPane graphic;
    @FXML
    private ListView<String> allTrades;

    public TradeCancellationView(WindowHandler windowHandler, TradeCancellationController tradeCancellationController,
                            T tradeCancellationPresenter) {
        this.windowHandler = windowHandler;
        this.tradeCancellationController = tradeCancellationController;
        this.tradeCancellationPresenter = tradeCancellationPresenter;
        this.tradeCancellationController.setTradeCancellationPresenter(this.tradeCancellationPresenter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //The trades list
            ObjectBinding<ObservableList<String>> yourItemsBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCancellationPresenter.getAllTrades());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCancellationPresenter).name("allTrades").build());
            allTrades.itemsProperty().bind(yourItemsBinding);

        } catch (NoSuchMethodException ignored) {
            System.out.println("failure");
        }
    }

    @Override
    public void reload() {
        tradeCancellationController.updateList();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void cancelClicked(ActionEvent actionEvent) {
        tradeCancellationController.cancelTrade(allTrades.getSelectionModel().getSelectedIndex());
        tradeCancellationController.updateList();
    }
}