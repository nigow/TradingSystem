package org.twelve.views;

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

    public void loginAttempted(ActionEvent actionEvent) {

        /*
        HomeController hc = new HomeController(null, null, null);

        hc. ? we can't pass info from view to controller RIPPPPP

        solution 1: augment presenters with ability to pass info from view to controller (more responsbility in presenters)
        solution 2: make controller methods take in parameters (sounds more reasonable tbh)

        Presenter presenter = new Presenter();

        String username = usernameBox.getText();

        presenter.setUsername(username);

        // inside controller

        loginUseCase.login(presenter.getUsername(), etc...)

        */

        if (loginController.logIn(usernameBox.getText(), passwordBox.getText())) {
            windowHandler.changeScene(Scenes.MENU);
            failMessageLabel.setVisible(false);
        } else {

            failMessageLabel.setVisible(true);
        }

    }
}
