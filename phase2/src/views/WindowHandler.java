package views;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WindowHandler extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        // changeScene(?)

    }

    void changeScene() {

        /* todo: implement this

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainView.fxml"));
        loader.setControllerFactory(controller -> new MainView(primaryStage));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(loader.load(), 300, 275));
        primaryStage.show();

         */

    }
}
