package org.twelve.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class WindowHandler extends Application {

    private Stage primaryStage;
    private Map<Scenes, SceneView> views;

    // don't add parameters to this, Application.launch() will explode if you do
    public WindowHandler() {

        ViewBuilder viewBuilder = new ViewBuilder(this);

        if (!viewBuilder.buildGateways("json")) return; // we can add more flexibility if we want here

        viewBuilder.buildControllers();

        views = new HashMap<>();

        for (Scenes scene : Scenes.values()) {

            ResourceBundle resourceBundle = ResourceBundle.getBundle("org.twelve.presenters." + scene.toString());
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.toString() + "View.fxml"), resourceBundle);

            loader.setControllerFactory(v -> viewBuilder.getView(scene, resourceBundle));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            views.put(scene, loader.getController());

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

        SceneView sceneView = views.get(scene);
        sceneView.reload();

        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(sceneView.getGraphic()));
        } else {
            primaryStage.getScene().setRoot(sceneView.getGraphic());
        }

    }

    public void changeLanguage() {
        // todo
    }

}
