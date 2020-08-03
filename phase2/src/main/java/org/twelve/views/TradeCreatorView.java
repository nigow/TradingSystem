package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.twelve.controllers.TradeCreatorController;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.TradePresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class TradeCreatorView<T extends ObservablePresenter & TradePresenter> implements SceneView, Initializable {

    private WindowHandler windowHandler;

    private final TradeCreatorController tradeCreatorController;
    private final T tradeCreatorPresenter;
    private int peerIndex;

    @FXML
    private ListView<String> allAccounts;
    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> peerItems;

    public TradeCreatorView(WindowHandler windowHandler, TradeCreatorController tradeCreatorController,
                            T tradeCreatorPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCreatorController = tradeCreatorController;
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        this.tradeCreatorController.setTradeCreatorPresenter((TradeCreatorPresenter) this.tradeCreatorPresenter);

    }

    @Override
    public void reload() {
        tradeCreatorController.updateLists(peerIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ObjectBinding<ObservableList<String>> tradeCreatorBinding = Bindings.createObjectBinding(() -> {
                return FXCollections.observableArrayList(tradeCreatorPresenter.get)
            })

        }
    }

    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }
}
