package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.twelve.controllers.ThresholdController;
import org.twelve.entities.Thresholds;
import org.twelve.presenters.ThresholdPresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RestrictionsView implements SceneView {

    private final WindowHandler windowHandler;

    private final org.twelve.controllers.ThresholdController thresholdController;

    private final ThresholdPresenter thresholdPresenter;

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

    public RestrictionsView(WindowHandler windowHandler, ThresholdController thresholdController, ThresholdPresenter thresholdPresenter) {
        this.windowHandler = windowHandler;
        this.thresholdController = thresholdController;
        this.thresholdPresenter = thresholdPresenter;
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
}
