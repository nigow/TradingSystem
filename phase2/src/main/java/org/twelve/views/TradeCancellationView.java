package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.twelve.controllers.TradeCancellationController;
import org.twelve.presenters.TradeCancellationPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing trade cancellations
 *
 * @param <T> Presenter.
 */
public class TradeCancellationView<T extends ObservablePresenter & TradeCancellationPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final TradeCancellationController tradeCancellationController;
    private final T tradeCancellationPresenter;

    @FXML
    private VBox graphic;
    @FXML
    private ListView<String> allTrades;
    @FXML
    private Button cancelButton;

    /**
     * Constructor of view for managing trade cancellations.
     *
     * @param windowHandler               An instance of {@link org.twelve.views.WindowHandler}.
     * @param tradeCancellationController Controller for managing trade cancellations.
     * @param tradeCancellationPresenter  Presenter for displaying trade cancellations.
     */
    public TradeCancellationView(WindowHandler windowHandler, TradeCancellationController tradeCancellationController,
                                 T tradeCancellationPresenter) {
        this.windowHandler = windowHandler;
        this.tradeCancellationController = tradeCancellationController;
        this.tradeCancellationPresenter = tradeCancellationPresenter;
        this.tradeCancellationController.setTradeCancellationPresenter(this.tradeCancellationPresenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            //The trades list
            ReadOnlyJavaBeanObjectProperty<List<String>> trades =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCancellationPresenter).name("allTrades").build();

            allTrades.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(trades.get()), trades));

        } catch (NoSuchMethodException ignored) {
            System.out.println("failure");
        }

        cancelButton.disableProperty().bind(allTrades.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        allTrades.getSelectionModel().clearSelection();
        tradeCancellationController.updateList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked() {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void cancelClicked() {
        tradeCancellationController.cancelTrade(allTrades.getSelectionModel().getSelectedIndex());
        allTrades.getSelectionModel().clearSelection();
        tradeCancellationController.updateList();
    }
}
