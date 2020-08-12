package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.RegistrationController;
import org.twelve.entities.Roles;
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
    private GridPane graphic;

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private Label typeBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Button registerBtn;

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
        if (typeBox.getText().equalsIgnoreCase(Roles.ADMIN.toString())) {
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


            typeBox.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create().bean(registrationPresenter).name("availableTypes").build());

            ReadOnlyJavaBeanObjectProperty<List<String>> existingCitiesBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(registrationPresenter).name("existingCities").build();

            ObjectBinding<ObservableList<String>> citiesBinding = Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(existingCitiesBinding.get()), existingCitiesBinding);

            locationBox.itemsProperty().bind(citiesBinding);

            errorLabel.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(registrationPresenter).name("error").build());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
