package views;

import javafx.scene.control.Button;

public class MenuView implements SceneView {
    private WindowHandler windowHandler;

    @Override
    public void setWindowHandler(WindowHandler windowHandler) {
        this.windowHandler = windowHandler;
    }

}
