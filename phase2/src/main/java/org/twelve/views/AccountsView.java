package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.twelve.controllers.FreezingController;
import org.twelve.presenters.FreezingPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AccountsView<T extends ObservablePresenter & FreezingPresenter> implements SceneView, Initializable {
    private final WindowHandler windowHandler;
    private final FreezingController freezingController;
    private final T freezingPresenter;

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


    public AccountsView(WindowHandler windowHandler, FreezingController freezingController, T freezingPresenter) {
        this.windowHandler = windowHandler;
        this.freezingPresenter = freezingPresenter;
        this.freezingController = freezingController;
        this.freezingController.setFreezingPresenter(this.freezingPresenter);
    }

    @Override
    public void reload() {
        freezingController.updateAccountLists();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

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

            ReadOnlyJavaBeanObjectProperty<List<Map<String, String>>> vacationing =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<Map<String, String>>>create().bean(freezingPresenter).name("vacationingAccounts").build();

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
                accounts.addAll(vacationing.get());
                accounts.addAll(admin.get());
                accounts.addAll(mod.get());
                accounts.addAll(trusted.get());
                accounts.addAll(regular.get());

                return FXCollections.observableArrayList(accounts);

            }, banned, unfreeze, frozen, toFreeze, vacationing, admin, mod, trusted, regular));

            usernameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("username")));
            roleCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("role")));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void trustClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.trust(trusted);
    }
    @FXML
    public void banClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.ban(trusted);
    }
    @FXML
    public void untrustClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.untrustAccount(trusted);
    }
    @FXML
    public void modClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.modAccount(trusted);
    }
    @FXML
    public void unmodClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.unmodAccount(trusted);
    }
    @FXML
    public void freezeClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.freeze(trusted);
    }
    @FXML
    public void unfreezeClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.unfreeze(trusted);
    }
    @FXML
    public void unbanClicked(ActionEvent actionEvent) {
        Map<String, String> selected = accountsTable.getSelectionModel().getSelectedItem();
        String trusted = selected.get("username");
        freezingController.unban(trusted);
    }
}
