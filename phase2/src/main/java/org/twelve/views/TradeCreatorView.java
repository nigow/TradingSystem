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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.twelve.controllers.TradeCreatorController;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing creation of trades.
 * @param <T> Presenter.
 * @author Ethan follow (@ethannomiddlenamelam)
 */
public class TradeCreatorView<T extends ObservablePresenter & TradeCreatorPresenter> implements SceneView, Initializable {


    private final WindowHandler windowHandler;

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

    /**
     * Constructor for the trade creation view.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param tradeCreatorController controller for creating trades.
     * @param tradeCreatorPresenter presenter for displaying trades.
     */
    public TradeCreatorView(WindowHandler windowHandler, TradeCreatorController tradeCreatorController,
                            T tradeCreatorPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCreatorController = tradeCreatorController;
        this.tradeCreatorPresenter = tradeCreatorPresenter;
        this.tradeCreatorController.setTradeCreatorPresenter(this.tradeCreatorPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        tradeCreatorController.updateLists(peerBox.getSelectionModel().getSelectedItem());
        locationBox.clear();
        twoWay.setSelected(false);
        isPermanent.setSelected(false);
        dateBox.setValue(LocalDate.now());
        peerBox.getSelectionModel().clearSelection();
        hourChosen.getValueFactory().setValue(0);
        minuteChosen.getValueFactory().setValue(0);
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

            //The PEER dropdown menu vvv
            ReadOnlyJavaBeanObjectProperty<List<String>> allAccounts =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("allUsers").build();

            peerBox.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(allAccounts.get()), allAccounts));
            //========================

            //The "Give" list
            ReadOnlyJavaBeanObjectProperty<List<String>> yourItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToGive").build();

            yourItems.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(yourItemsBinding.get()), yourItemsBinding));
            //=================

            //The "Receive" list
            ReadOnlyJavaBeanObjectProperty<List<String>> peerItemsBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToReceive").build();

            peerItems.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(peerItemsBinding.get()), peerItemsBinding));
            //================

            //The "Hour" Spinner
            hourChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCreatorPresenter).name("hourChosen").build()));
            //=================

            //The "Minute" Spinner
            minuteChosen.valueFactoryProperty().bind(Bindings.createObjectBinding(() -> new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(tradeCreatorPresenter).name("minuteChosen").build()));
            //=================

        } catch (NoSuchMethodException ignored) {System.out.println("failure");}

        yourItems.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (!twoWay.isSelected() && newValue.intValue() != -1) {
                peerItems.getSelectionModel().clearSelection();
            }
        }));


        peerItems.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (!twoWay.isSelected() && newValue.intValue() != -1) {
                yourItems.getSelectionModel().clearSelection();
            }
        }));

        dateBox.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        BooleanBinding isLocationEmpty = Bindings.isEmpty(locationBox.textProperty());
        BooleanBinding isValidTimeLocation = Bindings.createBooleanBinding(() -> {
            if (dateBox.getValue() == null || locationBox.getText() == null)
                return false;
            LocalTime time = LocalTime.of(hourChosen.getValue(), minuteChosen.getValue());
            LocalDateTime dateTime = LocalDateTime.of(dateBox.getValue(), time);
            return dateTime.isAfter(LocalDateTime.now());
        }, hourChosen.valueProperty(), minuteChosen.valueProperty(), dateBox.valueProperty());
        saveButton.disableProperty().bind(isValidTimeLocation.not().or(isLocationEmpty.or(peerBox.getSelectionModel().selectedItemProperty().isNull().or(yourItems.getSelectionModel().selectedItemProperty().isNull().and(
                peerItems.getSelectionModel().selectedItemProperty().isNull())))));

        yourItems.setCellFactory(new Callback<>() {
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
                            for (int i = 0; i < yourItems.getItems().size(); i++) {

                                // comparison by reference is intentional to determine which specific item it is
                                //noinspection StringEquality
                                if (yourItems.getItems().get(i) == item) {

                                    if (tradeCreatorPresenter.getPeerWishlist().contains(item)) {
                                        setTextFill(Color.GREEN);
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

        peerBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> yourItems.refresh());


    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void peerSwitch() {
        tradeCreatorController.updateLists(peerBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void clickTwoWay() {
        if (!twoWay.isSelected()) {
            peerItems.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void saveClicked() {
        LocalDateTime time = LocalDateTime.of(dateBox.getValue(), LocalTime.of(hourChosen.getValue(), minuteChosen.getValue()));
        tradeCreatorController.createTrade(peerBox.getSelectionModel().getSelectedItem(), yourItems.getSelectionModel().getSelectedIndex(),
                peerItems.getSelectionModel().getSelectedIndex(), isPermanent.isSelected(), locationBox.getText(), time);
        windowHandler.changeScene(Scenes.MENU);
    }

}
