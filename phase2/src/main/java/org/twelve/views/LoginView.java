package org.twelve.views;

import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.LoginController;
import org.twelve.presenters.LoginPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View for logging in.
 *
 * @param <T> Presenter.
 */
public class LoginView<T extends ObservablePresenter & LoginPresenter> implements SceneView, Initializable {

    @FXML
    private GridPane graphic;

    @FXML
    private Label failMessageLabel;

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    private final WindowHandler windowHandler;
    private final LoginController loginController;
    private final T loginPresenter;

    /**
     * Constructor of view for logging in.
     *
     * @param windowHandler   An instance of {@link org.twelve.views.WindowHandler}.
     * @param loginController Controller for managing login process.
     * @param loginPresenter  Presenter for displaying login errors.
     */
    public LoginView(WindowHandler windowHandler, LoginController loginController, T loginPresenter) {
        this.windowHandler = windowHandler;
        this.loginPresenter = loginPresenter;

        this.loginController = loginController;
        this.loginController.setLoginPresenter(this.loginPresenter);
    }

    /**
     * Lets user know if their log in attempt was successful.
     */
    @FXML
    private void loginAttempted() {

        if (loginController.logIn(usernameBox.getText(), passwordBox.getText())) {
            windowHandler.changeScene(Scenes.MENU);
        }

    }

    /**
     * Brings user back to the menu.
     */
    @FXML
    private void backClicked() {

        windowHandler.changeScene(Scenes.LANDING);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        usernameBox.clear();
        passwordBox.clear();
        loginPresenter.setError("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    /**
     * {@inheritDoc}
     */
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
