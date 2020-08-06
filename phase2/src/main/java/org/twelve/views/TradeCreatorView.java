package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.twelve.controllers.TradeCreatorController;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TradeCreatorView<T extends ObservablePresenter & TradeCreatorPresenter> implements SceneView,
        Initializable {

    private WindowHandler windowHandler;

    private final TradeCreatorController tradeCreatorController;
    private final T tradeCreatorPresenter;


    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> peerItems;
    @FXML
    private TextField initiatorField;
    @FXML
    private TextField locationBox;
    @FXML
    private CheckBox isPermanent;
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
        tradeCreatorController.updateLists(peerBox.getSelectionModel().getSelectedIndex());
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


            //The "Permanent" checkbox
            JavaBeanBooleanProperty permanentProp = JavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeCreatorPresenter).name("isPermanent").build();
            isPermanent.selectedProperty().bindBidirectional(permanentProp);
            //================


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


//            initiatorField.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
//                .bean(tradeCreatorPresenter).name("createdTrade").build());

        } catch (NoSuchMethodException ignored) {}
    }

    @FXML
    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    public void peerSwitch(ActionEvent actionEvent) {
        tradeCreatorController.changeSelectedUser(peerBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void cancelTrade(ActionEvent actionevent) {

    }

    @FXML
    public void isPermanent(ActionEvent actionEvent) {
        tradeCreatorController.changePermanentStatus(isPermanent.isSelected());
    }


    public void saveClicked(ActionEvent actionEvent) {
        LocalDateTime time = LocalDateTime.of(dateBox.getValue(), LocalTime.of(hourChosen.getValue(), minuteChosen.getValue()));
        tradeCreatorController.createTrade(peerBox.getSelectionModel().getSelectedIndex(), yourItems.getSelectionModel().getSelectedIndex(),
                peerItems.getSelectionModel().getSelectedIndex(), tradeCreatorPresenter.getIsPermanent(), locationBox.getText(), time);
    }

}
