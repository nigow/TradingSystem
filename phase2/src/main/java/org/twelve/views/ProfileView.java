package org.twelve.views;

import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import org.twelve.controllers.ProfileController;
import org.twelve.presenters.ui.UIProfilePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileView implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final ProfileController profileController;
    private UIProfilePresenter profilePresenter;

    @FXML
    private CheckBox onVacation;


    public ProfileView(WindowHandler windowHandler, ProfileController profileController) {
        this.windowHandler = windowHandler;
        this.profileController = profileController;
    }

    @Override
    public void reload() {

    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profilePresenter = new UIProfilePresenter(resources);
        profileController.setProfilePresenter(profilePresenter);

        try {
            onVacation.selectedProperty().bind(JavaBeanBooleanPropertyBuilder.create().bean(profilePresenter).name("onVacation").build());
            onVacation.disableProperty().bind(JavaBeanBooleanPropertyBuilder.create().bean(profilePresenter).name("canVacation").build());

        } catch (NoSuchMethodException ignored) {}

    }

    @FXML
    private void onVacationClicked(ActionEvent actionEvent) {

        profileController.changeVacationStatus(onVacation.isSelected());

    }
}
