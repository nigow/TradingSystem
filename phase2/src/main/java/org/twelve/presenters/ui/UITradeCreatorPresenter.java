package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCreatorPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Presenter that deals with the trade creation view.
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class UITradeCreatorPresenter extends ObservablePresenter implements TradeCreatorPresenter {

    private List<String> itemsToReceive;
    private List<String> itemsToGive;
    private List<String> allUsers;
    private String selectedUser;
    private String itemToLend;
    private String itemToBorrow;
    private boolean createdTrade;

    private final ResourceBundle localizedResources;

    public UITradeCreatorPresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;

        setItemsToReceive(new ArrayList<>());
        setItemsToGive(new ArrayList<>());
        setAllUsers(new ArrayList<>());
        setSelectedUser("");
        setSelectedItemToBorrow("");
        setSelectedItemToLend("");
    }

    public void setItemsToReceive(List<String> itemsToReceive) {
        List<String> oldItems = this.itemsToReceive;
        this.itemsToReceive = itemsToReceive;
        propertyChangeSupport.firePropertyChange("itemsToReceive", oldItems, itemsToReceive);
    }

    public List<String> getItemsToReceive() {return itemsToReceive;}

    public void setItemsToGive(List<String> itemsToGive) {
        List<String> oldItems = this.itemsToGive;
        this.itemsToGive = itemsToGive;
        propertyChangeSupport.firePropertyChange("itemsToGive", oldItems, itemsToGive);
    }

    public boolean getCreatedTrade() {return createdTrade;}

    public void setCreatedTrade(boolean createdTrade) {
        boolean oldCreatedTrade = this.createdTrade;
        this.createdTrade = createdTrade;
        propertyChangeSupport.firePropertyChange("createdTrade", oldCreatedTrade, this.createdTrade);
    }

    public List<String> getItemsToGive() {return itemsToGive;}

    public void setAllUsers(List<String> allUsers) {
        List<String> oldUsers = this.allUsers;
        this.allUsers = allUsers;
        propertyChangeSupport.firePropertyChange("allUsers", oldUsers, allUsers);
    }

    public List<String> getAllUsers() {return allUsers;}

    public void setSelectedUser(String user) {
        String oldSelectedUser = this.selectedUser;
        this.selectedUser = user;
        propertyChangeSupport.firePropertyChange("selectedUser", oldSelectedUser, user);
    }

    public String getSelectedUser() {return selectedUser;}

    public void setSelectedItemToLend(String item) {
        String oldItemtoLend = this.itemToLend;
        this.itemToLend = item;
        propertyChangeSupport.firePropertyChange("itemToLend", oldItemtoLend, item);
    }

    public String getSelectedItemToLend() {return itemToLend;}

    public void setSelectedItemToBorrow(String item) {
        String oldItemToBorrow = this.itemToBorrow;
        this.itemToBorrow = item;
        propertyChangeSupport.firePropertyChange("itemToBorrow", oldItemToBorrow, item);

    }
    public String getSelectedItemToBorrow() {return itemToBorrow;}
}
