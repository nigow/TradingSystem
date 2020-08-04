package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.fxml.Initializable;
import org.twelve.controllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.twelve.presenters.LoginPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView<T extends ObservablePresenter & LoginPresenter> implements SceneView, Initializable {

    @FXML
    private Label failMessageLabel;

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    private final WindowHandler windowHandler;
    private final LoginController loginController;
    private final T loginPresenter;

    public LoginView(WindowHandler windowHandler, LoginController loginController, T loginPresenter) {
        this.windowHandler = windowHandler;
        this.loginPresenter = loginPresenter;

        this.loginController = loginController;
        this.loginController.setLoginPresenter(this.loginPresenter);
    }

    @FXML
    private void loginAttempted(ActionEvent actionEvent) {

        loginController.logIn(usernameBox.getText(), passwordBox.getText());

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LANDING);

    }

    @Override
    public void reload() {
        usernameBox.clear();
        passwordBox.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            failMessageLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(loginPresenter).name("error").build());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
