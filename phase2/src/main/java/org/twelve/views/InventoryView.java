package org.twelve.views;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryView implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    public InventoryView(WindowHandler windowHandler) {

        this.windowHandler = windowHandler;

    }

    @Override
    public void reload() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
