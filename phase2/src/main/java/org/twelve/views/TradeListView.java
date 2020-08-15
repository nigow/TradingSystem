package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeListController;
import org.twelve.presenters.TradeListPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View for managing the trade and stats.
 *
 * @param <T> Presenter.
 */
public class TradeListView<T extends ObservablePresenter & TradeListPresenter> implements SceneView, Initializable {

    private final WindowHandler windowHandler;

    private final TradeListController tradeListController;
    private final T tradeCollectionPresenter;

    @FXML
    private GridPane graphic;
    @FXML
    private ComboBox<String> tradeStatusBox;
    @FXML
    private ComboBox<String> statsBox;
    @FXML
    private ListView<String> tradesList;
    @FXML
    private ListView<String> statsList;
    @FXML
    private Button selectButton;

    /**
     * Constructor of view for managing the trade and stats.
     *
     * @param windowHandler            An instance of {@link org.twelve.views.WindowHandler}.
     * @param tradeCollectionPresenter Presenter for managing trade and stats.
     * @param tradeListController      Controller for displaying trade and stats.
     */
    public TradeListView(WindowHandler windowHandler, TradeListController tradeListController, T tradeCollectionPresenter) {
        this.windowHandler = windowHandler;
        this.tradeListController = tradeListController;
        this.tradeCollectionPresenter = tradeCollectionPresenter;
        this.tradeListController.setTradeListPresenter(this.tradeCollectionPresenter);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ReadOnlyJavaBeanObjectProperty<List<String>> tradeStatus =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("allTradeStatus").build();

            tradeStatusBox.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(tradeStatus.get()), tradeStatus));

            ReadOnlyJavaBeanObjectProperty<List<String>> statsType =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("statsTypes").build();

            statsBox.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(statsType.get()), statsType));

            ReadOnlyJavaBeanObjectProperty<List<String>> tradesShown =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("tradesShown").build();

            tradesList.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(tradesShown.get()), tradesShown));

            ReadOnlyJavaBeanObjectProperty<List<String>> statsShown =
                    ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCollectionPresenter).name("statsShown").build();

            statsList.itemsProperty().bind(Bindings.createObjectBinding(() ->
                    FXCollections.observableArrayList(statsShown.get()), statsShown));

        } catch (NoSuchMethodException ignored) {
            System.out.println("failure");
        }

        selectButton.disableProperty().bind(tradesList.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        tradeStatusBox.getSelectionModel().select(0);
        statsBox.getSelectionModel().select(0);
        tradeListController.updateTradeList(tradeStatusBox.getSelectionModel().getSelectedIndex());
        tradeListController.updateStatsList(statsBox.getSelectionModel().getSelectedIndex());
        tradesList.getSelectionModel().clearSelection();
        statsList.getSelectionModel().clearSelection();
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
    private void selectClicked() {
        tradeListController.setSelectedTrade(tradeStatusBox.getSelectionModel().getSelectedIndex(), tradesList.getSelectionModel().getSelectedIndex());
        windowHandler.changeScene(Scenes.TRADE_EDITOR);
    }

    @FXML
    private void tradeStatusSwitch() {
        tradeListController.updateTradeList(tradeStatusBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void statsSwitch() {
        tradeListController.updateStatsList(statsBox.getSelectionModel().getSelectedIndex());
    }
}
