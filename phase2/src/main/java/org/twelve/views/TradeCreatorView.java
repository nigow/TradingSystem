package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeCreatorController;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TradeCreatorView<T extends ObservablePresenter & TradeCreatorPresenter> implements SceneView, Initializable {


    private WindowHandler windowHandler;

    private final TradeCreatorController tradeCreatorController;
    private final T tradeCreatorPresenter;


    @FXML
    private GridPane graphic;
    @FXML
    private Button saveButton;
    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> peerItems;
    @FXML
    private TextField locationBox;
    @FXML
    private CheckBox isPermanent;
    @FXML
    private CheckBox twoWay;
    @FXML
    private ComboBox<String> peerBox;
    @FXML
    private DatePicker dateBox;
    @FXML
    private Spinner<Integer> hourChosen;
    @FXML
    private Spinner<Integer> minuteChosen;

    public TradeCreatorView(WindowHandler windowHandler, TradeCreatorController tradeCreatorController,
                            T tradeCreatorPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCreatorController = tradeCreatorController;
        this.tradeCreatorPresenter = tradeCreatorPresenter;
        this.tradeCreatorController.setTradeCreatorPresenter(this.tradeCreatorPresenter);
    }

    @Override
    public void reload() {
        tradeCreatorController.updateLists(peerBox.getSelectionModel().getSelectedItem());
        locationBox.clear();
        isPermanent.setSelected(false);
        hourChosen.getValueFactory().setValue(0);
        minuteChosen.getValueFactory().setValue(0);
        dateBox.setValue(LocalDate.now());
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            //The PEER dropdown menu vvv
            ObjectBinding<ObservableList<String>> allAccounts = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getAllUsers());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("allUsers").build());
            peerBox.itemsProperty().bind(allAccounts);
            //========================

            //The "Give" list
            ObjectBinding<ObservableList<String>> yourItemsBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getItemsToGive());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToGive").build());
            yourItems.itemsProperty().bind(yourItemsBinding);
            //=================

            //The "Receive" list
            ObjectBinding<ObservableList<String>> peerItemsBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getItemsToReceive());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToReceive").build());
            peerItems.itemsProperty().bind(peerItemsBinding);
            //================

            //The "Hour" Spinner
            hourChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, tradeCreatorPresenter.getHourChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCreatorPresenter).name("hourChosen").build()));
            //=================

            //The "Minute" Spinner
            minuteChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, tradeCreatorPresenter.getMinuteChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCreatorPresenter).name("minuteChosen").build()));
            //=================

        } catch (NoSuchMethodException ignored) {System.out.println("failure");}

        yourItems.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (!twoWay.isSelected()) {
                peerItems.getSelectionModel().clearSelection();
                tradeCreatorController.changeSelectedItemToLend(newValue.intValue());
            }
        }));


        peerItems.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (!twoWay.isSelected()) {
                yourItems.getSelectionModel().clearSelection();
                tradeCreatorController.changeSelectedItemToBorrow(newValue.intValue(), peerBox.getSelectionModel().getSelectedItem());
            }
        }));
        BooleanBinding isLocationEmpty = Bindings.isEmpty(locationBox.textProperty());
        saveButton.disableProperty().bind(isLocationEmpty.or(yourItems.getSelectionModel().selectedItemProperty().isNull().and(
                peerItems.getSelectionModel().selectedItemProperty().isNull())));
    }

    @FXML
    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    public void peerSwitch(ActionEvent actionEvent) {
        tradeCreatorController.changeSelectedUser(peerBox.getSelectionModel().getSelectedItem());
        tradeCreatorController.updateLists(peerBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void clickTwoWay(ActionEvent actionevent) {
        if (!twoWay.isSelected()) {
            peerItems.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void isPermanent(ActionEvent actionEvent) {
        tradeCreatorController.changePermanentStatus(isPermanent.isSelected());
    }


    public void saveClicked(ActionEvent actionEvent) {
        LocalDateTime time = LocalDateTime.of(dateBox.getValue(), LocalTime.of(hourChosen.getValue(), minuteChosen.getValue()));
        tradeCreatorController.createTrade(peerBox.getSelectionModel().getSelectedItem(), yourItems.getSelectionModel().getSelectedIndex(),
                peerItems.getSelectionModel().getSelectedIndex(), isPermanent.isSelected(), locationBox.getText(), time);
        windowHandler.changeScene(Scenes.MENU);
    }

}
