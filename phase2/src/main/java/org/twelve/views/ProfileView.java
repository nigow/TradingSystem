package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.twelve.controllers.ProfileController;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileView<T extends ObservablePresenter & ProfilePresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final ProfileController profileController;
    private final T profilePresenter;


    @FXML
    private PasswordField oldPassword;
    @FXML
    private Button requestUnfreezeBtn;
    @FXML
    private PasswordField newPassword;
    @FXML
    private TextField locationBox;
    @FXML
    private CheckBox onVacation;

    public ProfileView(WindowHandler windowHandler, ProfileController profileController, T profilePresenter) {
        this.windowHandler = windowHandler;
        this.profilePresenter = profilePresenter;

        this.profileController = profileController;
        this.profileController.setProfilePresenter(this.profilePresenter);
    }

    @Override
    public void reload() {
        profileController.updateProfile();
    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            onVacation.selectedProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create().bean(profilePresenter).name("vacationStatus").build());

            onVacation.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create().bean(profilePresenter).name("canVacation").build().not());
            requestUnfreezeBtn.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create().bean(profilePresenter).name("canRequestUnfreeze").build().not());

        } catch (NoSuchMethodException ignored) {}

    }

    @FXML
    private void onVacationClicked(ActionEvent actionEvent) {

        profileController.changeVacationStatus(onVacation.isSelected());

    }

    public void updatePasswordClicked(ActionEvent actionEvent) {
        profileController.changePassword(oldPassword.getText(), newPassword.getText());
    }

    public void requestUnfreezeClicked(ActionEvent actionEvent) {
        profileController.requestUnfreeze();
    }

    public void updateLocationClicked(ActionEvent actionEvent) {

    }
}
