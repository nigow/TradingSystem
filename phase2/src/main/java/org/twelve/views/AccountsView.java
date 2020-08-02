package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsView implements SceneView, Initializable {
    private final WindowHandler windowHandler;
    private TableView accountsTable;

    public AccountsView(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


    @Override
    public void reload() {

    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
