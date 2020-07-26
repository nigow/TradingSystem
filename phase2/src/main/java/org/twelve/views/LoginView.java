package org.twelve.views;

import org.twelve.controllers.HomeController;
import org.twelve.controllers.experimental.LoginController;
import org.twelve.gateways.CSVUseCasePool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginView implements SceneView {

    @FXML
    private Label failMessageLabel;

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    private WindowHandler windowHandler;

    @Override
    public void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
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

        try {
            LoginController loginController = new LoginController(new CSVUseCasePool(), null, null);
            if (loginController.logIn(usernameBox.getText(), passwordBox.getText())) {
                windowHandler.changeScene(Scenes.MENU);
                failMessageLabel.setVisible(false);
            } else {

                failMessageLabel.setVisible(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
