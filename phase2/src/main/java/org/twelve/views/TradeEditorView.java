package org.twelve.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.ReadOnlyJavaBeanStringPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.twelve.controllers.TradeEditorController;
import org.twelve.controllers.TradeListController;
import org.twelve.presenters.TradeEditorPresenter;
import org.twelve.presenters.ui.ObservablePresenter;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeEditorView<T extends ObservablePresenter & TradeEditorPresenter> implements SceneView, Initializable {
    private WindowHandler windowHandler;

    private final TradeEditorController tradeEditorController;
    private final T tradeEditorPresenter;

    @FXML
    private GridPane graphic;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button completeButton;
    @FXML
    private TextField locationBox;
    @FXML
    private DatePicker dateBox;
    @FXML
    private Spinner<Integer> hourChosen;
    @FXML
    private Spinner<Integer> minuteChosen;
    @FXML
    private ListView<String> yourItems;
    @FXML
    private ListView<String> theirItems;
    @FXML
    private Label tradeStatus;
    @FXML
    private CheckBox isPermanent;
    @FXML
    private Label peerUsername;

    public TradeEditorView(WindowHandler windowHandler, TradeEditorController tradeEditorController, T tradeEditorPresenter) {
        this.windowHandler = windowHandler;
        this.tradeEditorController = tradeEditorController;
        this.tradeEditorPresenter = tradeEditorPresenter;
        this.tradeEditorController.setTradeEditorPresenter(this.tradeEditorPresenter);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ObjectBinding<ObservableList<String>> userItems = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeEditorPresenter.getUserItems());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("userItems").build());
            yourItems.itemsProperty().bind(userItems);

            ObjectBinding<ObservableList<String>> peerItems = Bindings.createObjectBinding(() -> {

                return FXCollections.observableArrayList(tradeEditorPresenter.getPeerItems());

            }, ReadOnlyJavaBeanObjectPropertyBuilder.<java.util.List<String>>create().bean(tradeEditorPresenter).name("peerItems").build());
            theirItems.itemsProperty().bind(peerItems);

            // TODO the following cause exceptions
            peerUsername.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("peerUsername").build());
            tradeStatus.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("tradeStatus").build());

            isPermanent.selectedProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("isPermanent").build());

            confirmButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canConfirm").build().not()); // TODO disable if user changed stuff in the prompt box

            completeButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canComplete").build().not());
            cancelButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canCancel").build().not());

            locationBox.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            dateBox.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            hourChosen.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            minuteChosen.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not());
            editButton.disableProperty().bind(ReadOnlyJavaBeanBooleanPropertyBuilder.create()
                    .bean(tradeEditorPresenter).name("canEdit").build().not()); // TODO disable if the date is before today


            /**
             *     private Button editButton;
             *     private Button cancelButton;
             *     private Button confirmButton;
             *     private Button completeButton;
             *
             *     private TextField locationBox;
             *     private DatePicker dateBox;
             *     private Spinner<Integer> hourChosen;
             *     private Spinner<Integer> minuteChosen;
             */

//            itemDescription.textProperty().bind(ReadOnlyJavaBeanStringPropertyBuilder.create()
//                    .bean(adminWishlistPresenter).name("selectedItemDescription").build());

        } catch (NoSuchMethodException ignored) {
            System.out.println("failure");
        }

        isPermanent.setDisable(true);

//        removeButton.disableProperty().bind(wishlistOfUser.getSelectionModel().selectedItemProperty().isNull());

    }

    @Override
    public void reload() {
        tradeEditorController.setTradeProperties();
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @FXML
    private void backClicked() {
        tradeEditorController.removeSelectedTrade();
        windowHandler.changeScene(Scenes.MENU);
    }

    @FXML
    private void cancelClicked() {
        tradeEditorController.cancelTrade();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void editClicked() {
        // TODO fix this!!!
        tradeEditorController.editTrade(null, null);
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void completeClicked() {
        tradeEditorController.completeTrade();
        tradeEditorController.setTradeProperties();
    }

    @FXML
    private void confirmClicked() {
        tradeEditorController.confirmTrade();
        tradeEditorController.setTradeProperties();
    }
}
