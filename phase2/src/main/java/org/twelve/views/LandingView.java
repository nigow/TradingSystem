package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.twelve.controllers.LandingController;
import org.twelve.presenters.LandingPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * View for landing page.
 *
 * @param <T> Presenter.
 */
public class LandingView<T extends ObservablePresenter & LandingPresenter> implements SceneView, Initializable {

    @FXML
    private CheckBox demoMode;
    @FXML
    private ComboBox<Locale> languages;
    @FXML
    private GridPane graphic;
    @FXML
    private Button demoLogin;

    private final WindowHandler windowHandler;
    private final LandingController landingController;
    private final T landingPresenter;

    /**
     * Constructor of view for landing page.
     *
     * @param windowHandler     An instance of {@link org.twelve.views.WindowHandler}.
     * @param landingController Controller for managing local preferences such as display language.
     * @param landingPresenter  Presenter for displaying current local preferences.
     */
    public LandingView(WindowHandler windowHandler, LandingController landingController, T landingPresenter) {
        this.windowHandler = windowHandler;
        this.landingPresenter = landingPresenter;

        this.landingController = landingController;
        this.landingController.setLandingPresenter(this.landingPresenter);

    }

    @FXML
    private void loginClicked() {

        windowHandler.changeScene(Scenes.LOGIN);

    }

    @FXML
    private void registerClicked() {

        windowHandler.changeScene(Scenes.REGISTRATION);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        landingController.displaySettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    // have to be global to prevent garbage collection while in use
    @SuppressWarnings("FieldCanBeLocal")
    private ReadOnlyJavaBeanObjectProperty<Locale> selectedLanguageBinding;
    @SuppressWarnings("FieldCanBeLocal")
    private JavaBeanBooleanProperty demoModeBinding;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ReadOnlyJavaBeanObjectProperty<List<Locale>> availableLanguagesBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Locale>>create().bean(landingPresenter).name("availableLanguages").build();

            languages.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(availableLanguagesBinding.get()), availableLanguagesBinding));

            selectedLanguageBinding = ReadOnlyJavaBeanObjectPropertyBuilder.<Locale>create().bean(landingPresenter).name("selectedLanguage").build();
            selectedLanguageBinding.addListener((observable, oldValue, newValue) -> languages.getSelectionModel().select(newValue));

            demoModeBinding = JavaBeanBooleanPropertyBuilder.create().bean(landingPresenter).name("demoMode").build();
            demoMode.selectedProperty().bindBidirectional(demoModeBinding);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        languages.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Locale> call(ListView<Locale> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Locale item, boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {

                            setText(item.getDisplayName(languages.getValue()));
                        }

                    }
                };
            }
        });

        // workaround for setCellFactory not affecting button cell
        languages.setButtonCell(languages.getCellFactory().call(null));

        demoLogin.disableProperty().bind(demoMode.selectedProperty().not());
    }

    @FXML
    private void languageChanged() {

        windowHandler.restart(languages.getSelectionModel().getSelectedItem(), demoMode.isSelected());

    }

    @FXML
    private void demoModeChanged() {

        windowHandler.restart(languages.getSelectionModel().getSelectedItem(), demoMode.isSelected());

    }

    @FXML
    public void demoLoginClicked() {
        landingController.createDemoAccount();
        windowHandler.changeScene(Scenes.MENU);
    }
}
