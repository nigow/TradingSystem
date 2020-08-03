package org.twelve.views;

import javafx.fxml.Initializable;
import org.twelve.controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView implements SceneView {

    @FXML
    private Label failMessageLabel;

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    private WindowHandler windowHandler;
    private LoginController loginController;

    public LoginView(WindowHandler windowHandler, LoginController loginController) {
        this.windowHandler = windowHandler;
        this.loginController = loginController;
    }

    @FXML
    private void loginAttempted(ActionEvent actionEvent) {

        if (loginController.logIn(usernameBox.getText(), passwordBox.getText())) {
            windowHandler.changeScene(Scenes.MENU);
            failMessageLabel.setVisible(false);
        } else {

            failMessageLabel.setVisible(true);
        }

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LANDING);

    }

    @Override
    public void reload() {

    }
}
