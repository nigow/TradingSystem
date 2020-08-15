package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeEditorController;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing editing trades.
 * @param <T> Presenter.
 */
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

    /**
     * Constructor of view for managing trade edits.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param tradeEditorController Controller for managing trade edits.
     * @param tradeEditorPresenter Presenter for displaying trade edits.
     */
    public TradeEditorView(WindowHandler windowHandler, TradeEditorController tradeEditorController, T tradeEditorPresenter) {
        this.windowHandler = windowHandler;
        this.tradeEditorController = tradeEditorController;
        this.tradeEditorPresenter = tradeEditorPresenter;
        this.tradeEditorController.setTradeEditorPresenter(this.tradeEditorPresenter);
    }

    @SuppressWarnings("FieldCanBeLocal") // have to be global to prevent garbage collection while in use
    private ReadOnlyJavaBeanObjectProperty<LocalDate> dateChosenBinding;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ReadOnlyJavaBeanObjectProperty<List<String>> userItems =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("userItems").build();

            yourItems.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(userItems.get()), userItems));

            ReadOnlyJavaBeanObjectProperty<List<String>> peerItems =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("peerItems").build();

            theirItems.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(peerItems.get()), peerItems));

            peerUsername.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("peerUsername").build());
            tradeStatus.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("tradeStatus").build());

            isPermanent.selectedProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("isPermanent").build());

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
            ReadOnlyJavaBeanIntegerProperty hourChosenBinding =
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeEditorPresenter).name("hourChosen").build();

            hourChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, hourChosenBinding.get()), hourChosenBinding));
            //=================

            //The "Minute" Spinner
            ReadOnlyJavaBeanIntegerProperty minuteChosenBinding =
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeEditorPresenter).name("minuteChosen").build();

            minuteChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, minuteChosenBinding.get()), minuteChosenBinding));
            //=================

            locationBox.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("locationChosen").build());

            // workaround loss of generic info with JavaBeanObjectPropertyBuilder (JDK bug ticket: https://bugs.openjdk.java.net/browse/JDK-8152399)
            dateChosenBinding = ReadOnlyJavaBeanObjectPropertyBuilder.<LocalDate>create().bean(tradeEditorPresenter).name("dateChosen").build();
            dateChosenBinding.addListener((observable, oldValue, newValue) -> dateBox.setValue(newValue));

            BooleanBinding isValidTimeLocation = Bindings.createBooleanBinding(() -> {
                if (dateBox.getValue() == null) return false;
                LocalTime time = LocalTime.of(hourChosen.getValue(), minuteChosen.getValue());
                LocalDateTime dateTime = LocalDateTime.of(dateBox.getValue(), time);
                return dateTime.isAfter(LocalDateTime.now());
            }, hourChosen.valueProperty(), minuteChosen.valueProperty(), dateBox.valueProperty());

            editButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().and(isValidTimeLocation).not());

            BooleanBinding edited = Bindings.createBooleanBinding(() -> {

                boolean locationEdited = !Arrays.asList("", locationBox.getPromptText()).contains(locationBox.getText());
                boolean dateBoxEdited = !dateChosenBinding.get().equals(dateBox.getValue());
                boolean hourChosenEdited = !hourChosen.getValue().equals(hourChosenBinding.get());
                boolean minuteChosenEdited = !minuteChosen.getValue().equals(minuteChosenBinding.get());

                return locationEdited || dateBoxEdited || hourChosenEdited || minuteChosenEdited;

            }, locationBox.textProperty(), dateBox.valueProperty(), hourChosen.valueProperty(), minuteChosen.valueProperty());

            confirmButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canConfirm").build().not().or(edited));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        tradeEditorController.setTradeProperties();
        locationBox.clear();
    }

    /**
     * {@inheritDoc}
     */
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
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void editClicked() {
        LocalTime time = LocalTime.of(hourChosen.getValue(), minuteChosen.getValue());
        LocalDateTime dateTime = LocalDateTime.of(dateBox.getValue(), time);
        tradeEditorController.editTrade(locationBox.getText().isBlank() ? locationBox.getPromptText() : locationBox.getText(), dateTime);
        reload();
    }

    @FXML
    private void completeClicked() {
        tradeEditorController.completeTrade();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void confirmClicked() {
        tradeEditorController.confirmTrade();
        tradeEditorController.setTradeProperties();
    }
}
