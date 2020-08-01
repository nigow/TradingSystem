package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.twelve.controllers.ThresholdController;
import org.twelve.entities.Thresholds;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.ResourceBundle;

public class RestrictionsView<T extends ObservablePresenter & ThresholdPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final ThresholdController thresholdController;

    private final T thresholdPresenter;

    @FXML
    private TextField lendNum;
    @FXML
    private TextField incompleteNum;
    @FXML
    private TextField weeklyNum;
    @FXML
    private TextField dayNum;
    @FXML
    private TextField editNum;
    @FXML
    private TextField statNum;

    public RestrictionsView(WindowHandler windowHandler, ThresholdController thresholdController, T thresholdPresenter) {
        this.windowHandler = windowHandler;
        this.thresholdPresenter = thresholdPresenter;

        this.thresholdController = thresholdController;
        this.thresholdController.setThresholdPresenter(thresholdPresenter);
    }

    @Override
    public void reload() {
        thresholdController.displayThresholds();
    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    public void saveClicked(ActionEvent actionEvent) {
        thresholdController.lendMoreThanBorrow(lendNum.getText());
        thresholdController.maxIncompleteTrades(incompleteNum.getText());
        thresholdController.maxWeeklyTrades(weeklyNum.getText());
        thresholdController.numberOfDays(dayNum.getText());
        thresholdController.numberOfEdits(editNum.getText());
        thresholdController.numberOfStats(statNum.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            lendNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("lendMoreThanBorrow").build());

            incompleteNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("maxIncompleteTrade").build());

            weeklyNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("maxWeeklyTrade").build());

            dayNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("numberOfDays").build());

            editNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("numberOfEdits").build());

            statNum.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(thresholdPresenter).name("numberOfStats").build());

        } catch (NoSuchMethodException ignored) {}

        // todo (enhancement): prevent users from entering non number chars
    }
}
