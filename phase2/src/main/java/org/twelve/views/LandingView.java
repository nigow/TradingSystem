package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.LandingController;
import org.twelve.presenters.LandingPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LandingView<T extends ObservablePresenter & LandingPresenter> implements SceneView, Initializable {

    @FXML
    private CheckBox demoMode;
    @FXML
    private ComboBox<Locale> languages;
    @FXML
    private GridPane graphic;

    private final WindowHandler windowHandler;
    private final LandingController landingController;
    private final T landingPresenter;

    public LandingView(WindowHandler windowHandler, LandingController landingController, T landingPresenter) {
        this.windowHandler = windowHandler;
        this.landingPresenter = landingPresenter;

        this.landingController = landingController;
        this.landingController.setLandingPresenter(this.landingPresenter);
    }

    @FXML
    private void loginClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LOGIN);

    }

    @FXML
    private void registerClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.REGISTRATION);

    }

    @Override
    public void reload() {

    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            languages.itemsProperty().bind(Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(landingPresenter.getAvailableLanguages());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.create().bean(landingPresenter).name("availableLanguages").build()));

            languages.getSelectionModel().select(landingPresenter.getSelectedLanguage());

            demoMode.selectedProperty().bindBidirectional(JavaBeanBooleanPropertyBuilder.create()
                    .bean(landingPresenter).name("demoMode").build());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void languageChanged(ActionEvent actionEvent) {

        windowHandler.restart(languages.getSelectionModel().getSelectedItem(), demoMode.isSelected());

    }

    @FXML
    private void demoModeChanged(ActionEvent actionEvent) {

        windowHandler.restart(languages.getSelectionModel().getSelectedItem(), demoMode.isSelected());

    }
}
