package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.twelve.controllers.ProfileController;
import org.twelve.presenters.ProfilePresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing personal account.
 * @param <T> Presenter.
 */
public class ProfileView<T extends ObservablePresenter & ProfilePresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;
    private final ProfileController profileController;
    private final T profilePresenter;

    @FXML
    private Button updateLocationBtn;
    @FXML
    private Label updatePasswordError;
    @FXML
    private Button updatePasswordBtn;
    @FXML
    private BorderPane graphic;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private Button requestUnfreezeBtn;
    @FXML
    private PasswordField newPassword;
    @FXML
    private ComboBox<String> locationBox;
    @FXML
    private CheckBox onVacation;

    /**
     * Constructor of view for managing personal account.
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param profileController Controller for managing account settings.
     * @param profilePresenter Presenter for displaying account status and current settings.
     */
    public ProfileView(WindowHandler windowHandler, ProfileController profileController, T profilePresenter) {
        this.windowHandler = windowHandler;
        this.profilePresenter = profilePresenter;

        this.profileController = profileController;
        this.profileController.setProfilePresenter(this.profilePresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        profileController.updateProfile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            JavaBeanBooleanProperty vacationProp = JavaBeanBooleanPropertyBuilder.create()
                    .bean(profilePresenter).name("vacationStatus").build();

            onVacation.selectedProperty().bindBidirectional(vacationProp);
            onVacation.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(profilePresenter).name("canVacation").build().not().and(vacationProp.not()));

            requestUnfreezeBtn.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(profilePresenter).name("canRequestUnfreeze").build().not());

            updatePasswordError.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(profilePresenter).name("error").build());

            ReadOnlyJavaBeanObjectProperty<List<String>> existingCitiesBinding =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(profilePresenter).name("existingCities").build();

            ObjectBinding<ObservableList<String>> citiesBinding = Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(existingCitiesBinding.get()), existingCitiesBinding);

            locationBox.itemsProperty().bind(citiesBinding);

        } catch (NoSuchMethodException ignored) {
        }

        updateLocationBtn.disableProperty().bind(locationBox.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void onVacationClicked() {

        profileController.changeVacationStatus(onVacation.isSelected());

    }

    @FXML
    private void updatePasswordClicked() {
        profileController.changePassword(oldPassword.getText(), newPassword.getText());
    }

    @FXML
    private void requestUnfreezeClicked() {
        profileController.requestUnfreeze();
    }

    @FXML
    private void updateLocationClicked() {
        profileController.changeLocation(locationBox.getEditor().getText());
    }
}
