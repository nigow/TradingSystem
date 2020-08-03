package org.twelve.views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.twelve.controllers.TradeCollectionController;
import org.twelve.presenters.TradeCollectionPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class TradeCollectionView<T extends ObservablePresenter & TradeCollectionPresenter> implements SceneView, Initializable {

    private WindowHandler windowHandler;
    private final TradeCollectionController tradeCollectionController;
    private final T tradeCollectionPresenter;

    public TradeCollectionView(WindowHandler windowHandler, TradeCollectionController tradeCollectionController,
                            T tradeCollectionPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCollectionController = tradeCollectionController;
        this.tradeCollectionPresenter = tradeCollectionPresenter;

        this.tradeCollectionController.setTradeCollectionPresenter(this.tradeCollectionPresenter);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void reload() {

    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }
}
