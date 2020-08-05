package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.twelve.controllers.RegistrationController;
import org.twelve.presenters.RegistrationPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationView<T extends ObservablePresenter & RegistrationPresenter> implements SceneView,
        Initializable {

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private ComboBox<String> typeBox;

    private final WindowHandler windowHandler;
    private final RegistrationController registrationController;
    private final T registrationPresenter;

    public RegistrationView(WindowHandler windowHandler, RegistrationController registrationController, T registrationPresenter) {
        this.windowHandler = windowHandler;
        this.registrationPresenter = registrationPresenter;

        this.registrationController = registrationController;
        this.registrationController.setRegistrationPresenter(this.registrationPresenter);
    }

    @FXML
    private void registerClicked(ActionEvent actionEvent) {

        if (registrationController.createAccount(usernameBox.getText(), passwordBox.getText(), locationBox.getValue(),
                typeBox.getSelectionModel().getSelectedIndex())) {

            windowHandler.changeScene(Scenes.MENU);

        }

    }

    @FXML
    private void backClicked(ActionEvent actionEvent) {

        if (typeBox.getItems().size() > 1) {
            windowHandler.changeScene(Scenes.LANDING);
        } else {
            windowHandler.changeScene(Scenes.MENU);
        }

    }

    @Override
    public void reload() {
        registrationController.updateOptions();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> typesBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(registrationPresenter.getAvailableTypes());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(registrationPresenter).name("availableTypes").build());

            typeBox.itemsProperty().bind(typesBinding);

            ObjectBinding<ObservableList<String>> citiesBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(registrationPresenter.getExistingCities());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(registrationPresenter).name("existingCities").build());

            locationBox.itemsProperty().bind(citiesBinding);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
