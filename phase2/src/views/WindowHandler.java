package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.toString()));
        Scene newScene = new Scene(loader.load()); // todo: could handle size here

        loader.<SceneView>getController().setWindowHandler(this); // todo: pass whatever else needs to be passed

        return newScene;

    }
}
