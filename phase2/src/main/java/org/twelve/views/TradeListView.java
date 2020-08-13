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
import org.twelve.controllers.TradeListController;
import org.twelve.presenters.TradeListPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeListView<T extends ObservablePresenter & TradeListPresenter> implements SceneView, Initializable {

    private WindowHandler windowHandler;

    private final TradeListController tradeListController;
    private final T tradeCollectionPresenter;

    @FXML
    private GridPane graphic;
    @FXML
    private ComboBox<String> tradeStatusBox;
    @FXML
    private ComboBox<String> statsBox;
    @FXML
    private ListView<String> tradesList;
    @FXML
    private ListView<String> statsList;
    @FXML
    private Button selectButton;

    public TradeListView(WindowHandler windowHandler, TradeListController tradeListController, T tradeCollectionPresenter) {
        this.windowHandler = windowHandler;
        this.tradeListController = tradeListController;
        this.tradeCollectionPresenter = tradeCollectionPresenter;
        this.tradeListController.setTradeListPresenter(this.tradeCollectionPresenter);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> tradeStatus = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCollectionPresenter.getAllTradeStatus());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("allTradeStatus").build());
            tradeStatusBox.itemsProperty().bind(tradeStatus);

            ObjectBinding<ObservableList<String>> statsType = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCollectionPresenter.getStatsTypes());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("statsTypes").build());
            statsBox.itemsProperty().bind(statsType);


            ObjectBinding<ObservableList<String>> tradesShown = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCollectionPresenter.getTradesShown());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("tradesShown").build());
            tradesList.itemsProperty().bind(tradesShown);

            ObjectBinding<ObservableList<String>> statsShown = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCollectionPresenter.getStatsShown());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("statsShown").build());
            statsList.itemsProperty().bind(statsShown);

        } catch (NoSuchMethodException ignored) {System.out.println("failure");}

        selectButton.disableProperty().bind(tradesList.getSelectionModel().selectedItemProperty().isNull());
    }

    @Override
    public void reload() {
        tradeStatusBox.getSelectionModel().select(0);
        statsBox.getSelectionModel().select(0);
        tradeListController.updateTradeList(tradeStatusBox.getSelectionModel().getSelectedIndex());
        tradeListController.updateStatsList(statsBox.getSelectionModel().getSelectedIndex());
        tradesList.getSelectionModel().clearSelection();
        statsList.getSelectionModel().clearSelection();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void selectClicked() {
        tradeListController.setSelectedTrade(tradeStatusBox.getSelectionModel().getSelectedIndex(), tradesList.getSelectionModel().getSelectedIndex());
        windowHandler.changeScene(Scenes.TRADE_EDITOR);
    }

    @FXML
    private void tradeStatusSwitch() {
        tradeListController.updateTradeList(tradeStatusBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void statsSwitch() {
        tradeListController.updateStatsList(statsBox.getSelectionModel().getSelectedIndex());
    }
}