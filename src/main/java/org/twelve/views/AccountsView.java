package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.twelve.controllers.AccountsController;
import org.twelve.presenters.AccountsPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * View for managing accounts.
 * @param <T> Presenter.
 */
public class AccountsView<T extends ObservablePresenter & AccountsPresenter> implements SceneView, Initializable {
    private final WindowHandler windowHandler;
    private final AccountsController accountsController;
    private final T freezingPresenter;

    @FXML
    private Button banBtn;

    @FXML
    private Button unbanBtn;

    @FXML
    private Button trustBtn;

    @FXML
    private Button untrustBtn;

    @FXML
    private BorderPane graphic;

    @FXML
    private TableView<Map<String, String>> accountsTable;

    @FXML
    private TableColumn<Map<String, String>, String> usernameCol;

    @FXML
    private TableColumn<Map<String, String>, String> roleCol;

    @FXML
    private Button modButton;

    @FXML
    private Button unmodButton;

    @FXML
    private Button freezeButton;

    @FXML
    private Button unfreezeButton;

    /**
     * Constructor of view for managing accounts.
     *
     * @param windowHandler An instance of {@link org.twelve.views.WindowHandler}.
     * @param accountsController Controller for managing accounts.
     * @param freezingPresenter Presenter for displaying existing accounts.
     */
    public AccountsView(WindowHandler windowHandler, AccountsController accountsController, T freezingPresenter) {
        this.windowHandler = windowHandler;
        this.freezingPresenter = freezingPresenter;
        this.accountsController = accountsController;
        this.accountsController.setAccountsPresenter(this.freezingPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        accountsController.updateAccountLists();
        accountsTable.getSelectionModel().clearSelection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    /**
     * Brings user back to the menu.
     */
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
            modButton.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(freezingPresenter).name("canMod").build());
            unmodButton.visibleProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(freezingPresenter).name("canUnmod").build());

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> banned =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("bannedAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> unfreeze =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("unfreezeAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> frozen =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("frozenAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> toFreeze =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("toFreezeAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> admin =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("adminAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> mod =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("modAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> trusted =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("trustedAccounts").build();

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> regular =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("regularAccounts").build();

            accountsTable.itemsProperty().bind(Bindings.createObjectBinding(() -> {

                List<Map<String, String>> accounts = new ArrayList<>(banned.get());
                accounts.addAll(unfreeze.get());
                accounts.addAll(frozen.get());
                accounts.addAll(toFreeze.get());
                accounts.addAll(admin.get());
                accounts.addAll(mod.get());
                accounts.addAll(trusted.get());
                accounts.addAll(regular.get());

                return FXCollections.observableArrayList(accounts);

            }, banned, unfreeze, frozen, toFreeze, admin, mod, trusted, regular));

            usernameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("username")));
            roleCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("role")));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        userSelected();
    }

    /**
     * Selected user becomes trusted.
     */
    @FXML
    private void trustClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.trust(trusted);
    }

    /**
     * Selected user gets banned.
     */
    @FXML
    private void banClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.ban(trusted);
    }

    /**
     * Selected user gets untrusted.
     */
    @FXML
    private void untrustClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.untrustAccount(trusted);
    }

    /**
     * Selected user becomes a mod.
     */
    @FXML
    private void modClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.modAccount(trusted);
    }

    /**
     * Selected user loses mod responsibilities.
     */
    @FXML
    private void unmodClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.unmodAccount(trusted);
    }

    /**
     * Selected user gets frozen.
     */
    @FXML
    private void freezeClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.freeze(trusted);
    }

    /**
     * Selected user gets unfrozen.
     */
    @FXML
    private void unfreezeClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.unfreeze(trusted);
    }

    /**
     * Selected user gets unbanned.
     */
    @FXML
    private void unbanClicked() {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        accountsController.unban(trusted);
    }

    /**
     * Lets user know what actions can be done to a selected account.
     */
    private void userSelected() {
        ReadOnlyObjectProperty<Map<String, String>> selectedProp = accountsTable.getSelectionModel().selectedItemProperty();

        modButton.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !(freezingPresenter.getRegularAccounts().contains(selectedProp.get()) ||
                freezingPresenter.getTrustedAccounts().contains(selectedProp.get())), selectedProp));

        unmodButton.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !freezingPresenter.getModAccounts().contains(selectedProp.get()), selectedProp));

        freezeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                (!freezingPresenter.getToFreezeAccounts().contains(selectedProp.get()) || freezingPresenter.getFrozenAccounts().contains(selectedProp.get())), selectedProp));

        unfreezeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !freezingPresenter.getUnfreezeAccounts().contains(selectedProp.get()), selectedProp));

        banBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                freezingPresenter.getBannedAccounts().contains(selectedProp.get()) || freezingPresenter.getAdminAccounts().contains(selectedProp.get()), selectedProp));

        unbanBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !freezingPresenter.getBannedAccounts().contains(selectedProp.get()), selectedProp));

        trustBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !freezingPresenter.getRegularAccounts().contains(selectedProp.get()), selectedProp));

        untrustBtn.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedProp.isNull().get() ||
                !freezingPresenter.getTrustedAccounts().contains(selectedProp.get()), selectedProp));
    }

}
