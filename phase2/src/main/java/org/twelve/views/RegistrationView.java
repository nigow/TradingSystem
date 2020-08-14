package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.RegistrationController;
import org.twelve.presenters.RegistrationPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for registering accounts.
 * @param <T> Presenter.
 */
public class RegistrationView<T extends ObservablePresenter & RegistrationPresenter> implements SceneView, Initializable {

    @FXML
    private Button registerBtn;

    @FXML
    private Label adminLabel;

    @FXML
    private Label userLabel;

    @FXML
    private GridPane graphic;

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private Label errorLabel;

    private final WindowHandler windowHandler;
    private final RegistrationController registrationController;
    private final T registrationPresenter;

    /**
     * Constructor of view for registering accounts.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param registrationController Controller for orchestrating registration of accounts.
     * @param registrationPresenter Presenter for displaying registration errors.
     */
    public RegistrationView(WindowHandler windowHandler, RegistrationController registrationController, T registrationPresenter) {
        this.windowHandler = windowHandler;
        this.registrationPresenter = registrationPresenter;

        this.registrationController = registrationController;
        this.registrationController.setRegistrationPresenter(this.registrationPresenter);

    }

    @FXML
    private void registerClicked() {

        if (registrationController.createAccount(usernameBox.getText(), passwordBox.getText(),
                locationBox.getEditor().getText())) {

            windowHandler.changeScene(Scenes.MENU);

        }

    }

    @FXML
    private void backClicked() {

        if (adminLabel.isVisible()) {
            windowHandler.changeScene(Scenes.MENU);
        } else {
            windowHandler.changeScene(Scenes.LANDING);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        registrationController.updateOptions();
        usernameBox.clear();
        passwordBox.clear();
        locationBox.getEditor().clear();
        registrationPresenter.setError("");
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

            adminLabel.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(registrationPresenter).name("adminMode").build());

            ReadOnlyJavaBeanObjectProperty<List<String>> existingCitiesBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(registrationPresenter).name("existingCities").build();

            locationBox.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(existingCitiesBinding.get()), existingCitiesBinding));

            errorLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(registrationPresenter).name("error").build());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        userLabel.visibleProperty().bind(adminLabel.visibleProperty().not());
        registerBtn.disableProperty().bind(Bindings.createBooleanBinding(()
                -> passwordBox.getText().isBlank(), passwordBox.textProperty()));
    }
}
