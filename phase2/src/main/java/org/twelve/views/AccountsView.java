package org.twelve.views;

import javafx.event.ActionEvent;

public class AccountsView implements SceneView {
    private final WindowHandler windowHandler;

    public AccountsView(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


    @Override
    public void reload() {

    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }
}
