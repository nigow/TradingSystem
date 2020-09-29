package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.adapter.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import org.twelve.controllers.ThresholdController;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View for managing system threshold values.
 *
 * @param <T> Presenter.
 */
public class ThresholdsView<T extends ObservablePresenter & ThresholdPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final ThresholdController thresholdController;
    private final T thresholdPresenter;

    @FXML
    public Button saveButton;

    @FXML
    private BorderPane graphic;
    @FXML
    private Spinner<Integer> lendVsBorrow;
    @FXML
    private Spinner<Integer> maxIncomplete;
    @FXML
    private Spinner<Integer> maxWeekly;
    @FXML
    private Spinner<Integer> numOfDays;
    @FXML
    private Spinner<Integer> numOfEdits;
    @FXML
    private Spinner<Integer> numOfStats;
    @FXML
    private Spinner<Integer> numForTrusted;

    /**
     * Constructor of view for managing system threshold values.
     *
     * @param windowHandler       An instance of {@link org.twelve.views.WindowHandler}.
     * @param thresholdController Controller for managing system threshold values.
     * @param thresholdPresenter  Presenter for displaying current threshold values.
     */
    public ThresholdsView(WindowHandler windowHandler, ThresholdController thresholdController, T thresholdPresenter) {
        this.windowHandler = windowHandler;
        this.thresholdPresenter = thresholdPresenter;

        this.thresholdController = thresholdController;
        this.thresholdController.setThresholdPresenter(thresholdPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        thresholdController.displayThresholds();
        lendVsBorrow.getValueFactory().setValue(thresholdPresenter.getLendMoreThanBorrow());
        maxIncomplete.getValueFactory().setValue(thresholdPresenter.getMaxIncompleteTrade());
        maxWeekly.getValueFactory().setValue(thresholdPresenter.getMaxWeeklyTrade());
        numOfDays.getValueFactory().setValue(thresholdPresenter.getNumberOfDays());
        numOfEdits.getValueFactory().setValue(thresholdPresenter.getNumberOfEdits());
        numOfStats.getValueFactory().setValue(thresholdPresenter.getNumberOfStats());
        numForTrusted.getValueFactory().setValue(thresholdPresenter.getNumberOfTradesUntilTrusted());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    /**
     * Brings user back to the menu.
     */
    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    /**
     * Updates thresholds with new values in the spinner.
     */
    @FXML
    private void saveClicked() {
        thresholdController.lendMoreThanBorrow(lendVsBorrow.getValue());
        thresholdController.maxIncompleteTrades(maxIncomplete.getValue());
        thresholdController.maxWeeklyTrades(maxWeekly.getValue());
        thresholdController.numberOfDays(numOfDays.getValue());
        thresholdController.numberOfEdits(numOfEdits.getValue());
        thresholdController.numberOfStats(numOfStats.getValue());
        thresholdController.numberOfTradesUntilTrusted(numForTrusted.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            lendVsBorrow.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, thresholdPresenter.getLendMoreThanBorrow()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("lendMoreThanBorrow").build()));

            maxIncomplete.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getMaxIncompleteTrade()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("maxIncompleteTrade").build()));

            maxWeekly.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getMaxWeeklyTrade()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("maxWeeklyTrade").build()));

            numOfDays.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getNumberOfDays()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("numberOfDays").build()));

            numOfEdits.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getNumberOfEdits()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("numberOfEdits").build()));

            numOfStats.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getNumberOfStats()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("numberOfStats").build()));

            numForTrusted.valueFactoryProperty().bind(Bindings.createObjectBinding(() ->
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, thresholdPresenter.getNumberOfTradesUntilTrusted()),
                    ReadOnlyJavaBeanIntegerPropertyBuilder.create().bean(thresholdPresenter).name("numberOfTradesUntilTrusted").build()));

            BooleanBinding booleanBinding = ReadOnlyJavaBeanBooleanPropertyBuilder.create().bean(thresholdPresenter).name("isAdmin").build().not();
            lendVsBorrow.disableProperty().bind(booleanBinding);
            maxIncomplete.disableProperty().bind(booleanBinding);
            maxWeekly.disableProperty().bind(booleanBinding);
            numOfDays.disableProperty().bind(booleanBinding);
            numOfEdits.disableProperty().bind(booleanBinding);
            numOfStats.disableProperty().bind(booleanBinding);
            numForTrusted.disableProperty().bind(booleanBinding);
            saveButton.disableProperty().bind(booleanBinding);
        } catch (NoSuchMethodException ignored) {
        }

    }
}
