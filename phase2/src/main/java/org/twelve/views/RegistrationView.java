package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.twelve.controllers.RegistrationController;
import org.twelve.presenters.ui.UIRegistrationPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationView implements SceneView, Initializable {

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
    private UIRegistrationPresenter registrationPresenter;

    private boolean accessedByAdmin;

    public RegistrationView(WindowHandler windowHandler, RegistrationController registrationController) {
        this.windowHandler = windowHandler;
        this.registrationController = registrationController;

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

        if (accessedByAdmin) {
            windowHandler.changeScene(Scenes.MENU);
        } else {
            windowHandler.changeScene(Scenes.LANDING);
        }

    }

    @Override
    public void reload() {
        accessedByAdmin = registrationController.updateAccessMode();
        typeBox.getItems().clear();
        typeBox.getItems().addAll(registrationPresenter.getAvailableTypes());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registrationPresenter = new UIRegistrationPresenter(resources);
        registrationController.setRegistrationPresenter(registrationPresenter);

        // todo
    }
}
