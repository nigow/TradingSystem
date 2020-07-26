package org.twelve.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class WindowHandler extends Application {

    private Stage primaryStage;
    private final Map<Scenes, Scene> scenes;

    // don't add parameters to this, Application.launch() will explode if you do
    public WindowHandler() {

        scenes = new HashMap<>();
        for (Scenes scene : Scenes.values()) {

            try {
                scenes.put(scene, createScene(scene));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }

    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Trading Application");
        changeScene(Scenes.LANDING);
        this.primaryStage.show();

    }

    public void changeScene(Scenes scene) {
        primaryStage.setScene(scenes.get(scene));
    }

    private Scene createScene(Scenes scene) throws IOException {


        // todo: make below code adapt to DI when backend changes are done
        ResourceBundle res = ResourceBundle.getBundle("localization." + scene.toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.toString() + "View.fxml"), res);
        Scene newScene = new Scene(loader.load());

        loader.<SceneView>getController().setWindowHandler(this);

        return newScene;

    }
}
