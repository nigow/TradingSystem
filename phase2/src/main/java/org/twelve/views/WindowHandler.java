package org.twelve.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class WindowHandler extends Application {

    private Stage primaryStage;
    private ViewBuilder viewBuilder;
    private Map<Scenes, SceneView> views;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Trading Application");

        viewBuilder = new ViewBuilder(this);
        restart(Locale.US, false);

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

    public void restart(Locale language, boolean demoMode) {

        viewBuilder.buildControllers(language, demoMode);
        views = new HashMap<>();

        for (Scenes scene : Scenes.values()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.toString() + "View.fxml"),
                    ResourceBundle.getBundle("org.twelve.presenters." + scene.toString(), language));

            loader.setControllerFactory(v -> viewBuilder.getView(scene, loader.getResources()));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1); // don't let ppl run the program if views can't be found
            }

            views.put(scene, loader.getController());

        }

        changeScene(Scenes.LANDING);

    }

}
