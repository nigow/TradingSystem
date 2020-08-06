package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringProperty;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeCreatorController;
import org.twelve.presenters.TradeCreatorPresenter;
import org.twelve.presenters.TradePresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import javafx.scene.control.ListView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeCreatorView<T extends ObservablePresenter & TradeCreatorPresenter> implements SceneView, Initializable {


    private WindowHandler windowHandler;

    private final TradeCreatorController tradeCreatorController;
    private final T tradeCreatorPresenter;
    private int peerIndex;

    @FXML
    private GridPane graphic;
    @FXML
    private ListView<String> allAccounts;
    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> peerItems;
    @FXML
    private TextField initiatorField;

    public TradeCreatorView(WindowHandler windowHandler, TradeCreatorController tradeCreatorController,
                            T tradeCreatorPresenter) {

        this.windowHandler = windowHandler;
        this.tradeCreatorController = tradeCreatorController;
        this.tradeCreatorPresenter = tradeCreatorPresenter;

        this.tradeCreatorController.setTradeCreatorPresenter(this.tradeCreatorPresenter);

    }

    @Override
    public void reload() {
        tradeCreatorController.updateLists(peerIndex);
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ObjectBinding<ObservableList<String>> yourItemsBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getItemsToGive());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToGive").build());

            yourItems.itemsProperty().bind(yourItemsBinding);

            ObjectBinding<ObservableList<String>> peerItemsBinding = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getItemsToReceive());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("itemsToReceive").build());

            peerItems.itemsProperty().bind(peerItemsBinding);


            ObjectBinding<ObservableList<String>> allAccounts = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeCreatorPresenter.getAllUsers());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<List<String>>create().bean(tradeCreatorPresenter).name("allUsers").build());

//            initiatorField.promptTextProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
//                .bean(tradeCreatorPresenter).name("createdTrade").build());

        } catch (NoSuchMethodException ignored) { System.out.println("fail");}
    }

    @FXML
    public void backClicked(ActionEvent actionEvent) {
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    public void peerSwitch(ActionEvent actionEvent) {
        tradeCreatorController.changeSelectedUser(allAccounts.getSelectionModel().getSelectedIndex());
    }


    public void saveClicked(ActionEvent actionEvent) {
//        tradeCreatorController.createTrade(allAccounts.getSelectionModel().getSelectedIndex(), yourItems.getSelectionModel().getSelectedIndex(),
//                peerItems.getSelectionModel().getSelectedIndex(), );
    }

}
