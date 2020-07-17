package views;

import javafx.event.ActionEvent;

public class LoginView implements SceneView {

    private WindowHandler windowHandler;

    @Override
    public void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

    public void loginAttempted(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.MENU);

    }
}
