package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.twelve.controllers.RegistrationController;
import org.twelve.presenters.RegistrationPresenter;

public class RegistrationView implements SceneView {

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private TextField locationBox;

    @FXML
    private ComboBox<String> typeBox;

    private final WindowHandler windowHandler;
    private final RegistrationController registrationController;
    private final RegistrationPresenter registrationPresenter;

    public RegistrationView(WindowHandler windowHandler, RegistrationController registrationController,
                            RegistrationPresenter registrationPresenter) {
        this.windowHandler = windowHandler;
        this.registrationController = registrationController;
        this.registrationPresenter = registrationPresenter;

    }

    @FXML
    private void registerClicked(ActionEvent actionEvent) {

        // todo: waiting location changes on registrationController and associated usecase
        if (registrationController.createAccount(usernameBox.getText(), passwordBox.getText(),
                typeBox.getSelectionModel().getSelectedIndex())) {
            windowHandler.changeScene(Scenes.MENU);
        }

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        if (typeBox.getItems().size() > 2) {
            windowHandler.changeScene(Scenes.MENU);
        } else {
            windowHandler.changeScene(Scenes.LANDING);
        }

    }

    @Override
    public void reload() {
        registrationController.updateAccessMode();
        typeBox.getItems().addAll(registrationPresenter.getAvailableTypes());
    }
}
