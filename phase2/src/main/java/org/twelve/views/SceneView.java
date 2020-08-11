package org.twelve.views;

import javafx.scene.Parent;

/**
 * Interface for scene classes.
 */
public interface SceneView {

    /**
     * Reloads the scene's information.
     */
    void reload();

    /**
     * Gets the scene's layout for loading into a JavaFX window.
     * @return The scene's layout.
     */
    Parent getGraphic();

}
