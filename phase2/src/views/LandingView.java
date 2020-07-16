package views;

import javafx.event.ActionEvent;

public class LandingView implements SceneView {

    private WindowHandler windowHandler;

    @Override
    public void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }


    public void loginClicked(ActionEvent actionEvent) {

        windowHandler.changeScene(Scenes.LOGIN);

    }
}
