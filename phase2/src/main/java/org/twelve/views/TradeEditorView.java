package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeEditorController;
import org.twelve.controllers.TradeListController;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TradeEditorView<T extends ObservablePresenter & TradeEditorPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final TradeEditorController tradeEditorController;
    private final T tradeEditorPresenter;

    @FXML
    private GridPane graphic;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button completeButton;
    @FXML
    private TextField locationBox;
    @FXML
    private DatePicker dateBox;
    @FXML
    private Spinner<Integer> hourChosen;
    @FXML
    private Spinner<Integer> minuteChosen;
    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> theirItems;
    @FXML
    private Label tradeStatus;
    @FXML
    private CheckBox isPermanent;
    @FXML
    private Label peerUsername;

    // need to be class vars so they don't end up garbage collected
    private final ObjectProperty<LocalDate> dateProp;

    public TradeEditorView(WindowHandler windowHandler, TradeEditorController tradeEditorController, T tradeEditorPresenter) {
        this.windowHandler = windowHandler;
        this.tradeEditorController = tradeEditorController;
        this.tradeEditorPresenter = tradeEditorPresenter;
        this.tradeEditorController.setTradeEditorPresenter(this.tradeEditorPresenter);
        dateProp = new SimpleObjectProperty<>();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> userItems = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeEditorPresenter.getUserItems());
            }, ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("userItems").build());
            yourItems.itemsProperty().bind(userItems);

            ObjectBinding<ObservableList<String>> peerItems = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeEditorPresenter.getPeerItems());
            }, ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("peerItems").build());
            theirItems.itemsProperty().bind(peerItems);

            peerUsername.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("peerUsername").build());
            tradeStatus.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("tradeStatus").build());

            isPermanent.selectedProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("isPermanent").build());

            confirmButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canConfirm").build().not());

            completeButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canComplete").build().not());
            cancelButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canCancel").build().not());

            locationBox.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            dateBox.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            hourChosen.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            minuteChosen.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());

            //The "Hour" Spinner
            hourChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, tradeEditorPresenter.getHourChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeEditorPresenter).name("hourChosen").build()));
            //=================

            //The "Minute" Spinner
            minuteChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> {
                return new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, tradeEditorPresenter.getMinuteChosen());
            }, ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeEditorPresenter).name("minuteChosen").build()));
            //=================

            // todo: we can change this but need to also change some other places for consistency
            locationBox.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("locationChosen").build());

            // workaround loss of generic info with JavaBeanObjectPropertyBuilder (JDK bug ticket: https://bugs.openjdk.java.net/browse/JDK-8152399)
            dateProp.bind(ReadOnlyJavaBeanObjectPropertyBuilder.<LocalDate>create().bean(tradeEditorPresenter).name("dateChosen").build());
            dateBox.valueProperty().bindBidirectional(dateProp);

            BooleanBinding isValidTimeLocation = Bindings.createBooleanBinding(() -> {
                if (dateBox.getValue() == null || locationBox.getText() == null)
                    return false;
                LocalTime time = LocalTime.of(hourChosen.getValue(), minuteChosen.getValue());
                LocalDateTime dateTime = LocalDateTime.of(dateBox.getValue(), time);
                return dateTime.isAfter(LocalDateTime.now());
            }, hourChosen.valueProperty(), minuteChosen.valueProperty(), dateBox.valueProperty());

            editButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().and(isValidTimeLocation).not());

            // TODO maybe find a better way
//            locationBox.textProperty().addListener((observable, oldValue, newValue) -> tradeEditorPresenter.setCanConfirm(false));
//            dateBox.valueProperty().addListener((observable, oldValue, newValue) -> tradeEditorPresenter.setCanConfirm(false));
//            hourChosen.valueProperty().addListener((observable, oldValue, newValue) -> tradeEditorPresenter.setCanConfirm(false));
//            minuteChosen.valueProperty().addListener((observable, oldValue, newValue) -> tradeEditorPresenter.setCanConfirm(false));


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        if (!dateBox.isDisable()) dateBox.getEditor().clear();
        tradeEditorController.setTradeProperties();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked() {
        tradeEditorController.removeSelectedTrade();
        windowHandler.changeScene(Scenes.TRADE_LIST);
    }

    @FXML
    private void cancelClicked() {
        tradeEditorController.cancelTrade();
        dateBox.getEditor().clear();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void editClicked() {
        LocalTime time = LocalTime.of(hourChosen.getValue(), minuteChosen.getValue());
        LocalDateTime dateTime = LocalDateTime.of(dateBox.getValue(), time);
        tradeEditorController.editTrade(locationBox.getText(), dateTime);
        dateBox.getEditor().clear();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void completeClicked() {
        tradeEditorController.completeTrade();
        dateBox.getEditor().clear();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void confirmClicked() {
        tradeEditorController.confirmTrade();
        dateBox.getEditor().clear();
        tradeEditorController.setTradeProperties();
    }
}
