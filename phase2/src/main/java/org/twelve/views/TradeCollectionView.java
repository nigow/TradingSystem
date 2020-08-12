package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeCollectionController;
import org.twelve.presenters.TradeCollectionPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeCollectionView<T extends ObservablePresenter & TradeCollectionPresenter> implements SceneView, Initializable {

    @FXML
    private GridPane graphic;
    @FXML
    private ListView<String> allTrades;
    @FXML
    private ListView<String> recentOneWays;
    @FXML
    private ListView<String> recentTwoWays;

    @FXML
    private TextField locationBox;
    @FXML
    private ComboBox<String> userBox;
    @FXML
    private DatePicker dateBox;
    @FXML
    private Spinner<Integer> hourChosen;
    @FXML
    private Spinner<Integer> minuteChosen;

    @FXML
    private Label tradingPartnerLabel;
    @FXML
    private Label yourItemsLabel;
    @FXML
    private Label theirItemsLabel;
    @FXML
    private Label topTradingPartnersLabel;

    @FXML
    private Button confirmBtn;
    @FXML
    private Button cancelBtn;

    private WindowHandler windowHandler;
    private final TradeCollectionController tradeCollectionController;
    private final T tradeCollectionPresenter;

    public TradeCollectionView(WindowHandler windowHandler, TradeCollectionController tradeCollectionController,
                            T tradeCollectionPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCollectionController = tradeCollectionController;
        this.tradeCollectionPresenter = tradeCollectionPresenter;

        this.tradeCollectionController.setTradeCollectionPresenter(this.tradeCollectionPresenter);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //The drop down
            ObjectBinding<ObservableList<String>> allAccounts = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeCollectionPresenter.getAllUsers());
                }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("allUsers").build());
                userBox.itemsProperty().bind(allAccounts);

            //The hour spinner
            hourChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, tradeCollectionPresenter.getHourChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCollectionPresenter).name("hourChosen").build()));

            //The minute spinner
            minuteChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, tradeCollectionPresenter.getMinuteChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCollectionPresenter).name("minuteChosen").build()));

            //The trades list
            ObjectBinding<ObservableList<String>> allTradesBinding = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeCollectionPresenter.getAllTrades());
            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("allTrades").build());
            allTrades.itemsProperty().bind(allTradesBinding);

            //The recent one-ways trades list
            ObjectBinding<ObservableList<String>> recentOneWaysBinding = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeCollectionPresenter.getRecentOneWays());
            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("recentOneWays").build());
            recentOneWays.itemsProperty().bind(recentOneWaysBinding);

            //The recent two-ways trades list
            ObjectBinding<ObservableList<String>> recentTwoWaysBinding = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeCollectionPresenter.getRecentTwoWays());
            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("recentTwoWays").build());
            recentTwoWays.itemsProperty().bind(recentTwoWaysBinding);

            //The trading partner label
            tradingPartnerLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeCollectionPresenter).name("tradingPartner").build());

            //The your items label
            yourItemsLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeCollectionPresenter).name("yourItems").build());

            //The their items label
            theirItemsLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeCollectionPresenter).name("theirItems").build());

            //The your top trading partners label
            topTradingPartnersLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeCollectionPresenter).name("topTradingPartners").build());




        } catch (NoSuchMethodException ignored) {
            System.out.println("failure");
        }




    }

    @Override
    public void reload() {
        tradeCollectionController.updateLists(userBox.getSelectionModel().getSelectedItem());
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    public void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }

    @FXML
    public void userSwitch(ActionEvent actionEvent) {
        tradeCollectionController.changeSelectedUser(userBox.getSelectionModel().getSelectedItem());
        tradeCollectionController.updateLists(userBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void confirmClicked(ActionEvent actionEvent) {
        tradeCollectionController.confirmTrade(allTrades.getSelectionModel().getSelectedIndex());
        tradeCollectionController.updateLists(userBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void cancelClicked(ActionEvent actionEvent) {
        tradeCollectionController.cancelTrade(allTrades.getSelectionModel().getSelectedIndex());
        tradeCollectionController.updateLists(userBox.getSelectionModel().getSelectedItem());
    }
}
