package org.twelve.presenters.ui;

import org.twelve.presenters.TradeCreatorPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Presenter that deals with the trade creation view.
 */
public class UITradeCreatorPresenter extends ObservablePresenter implements TradeCreatorPresenter {

    private List<String> itemsToReceive;
    private List<String> itemsToGive;
    private List<String> allUsers;
    private List<String> peerWishlist;
    private int hourChosen;
    private int minuteChosen;

    /**
     * Constructor for the trade creation presenter.
     */
    public UITradeCreatorPresenter() {
        super();
        setPeerWishlist(new ArrayList<>());
        setItemsToReceive(new ArrayList<>());
        setItemsToGive(new ArrayList<>());
        setAllUsers(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    public void setItemsToReceive(List<String> itemsToReceive) {
        List<String> oldItems = this.itemsToReceive;
        this.itemsToReceive = itemsToReceive;
        propertyChangeSupport.firePropertyChange("itemsToReceive", oldItems, itemsToReceive);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getItemsToReceive() {
        return itemsToReceive;
    }

    /**
     * {@inheritDoc}
     */
    public void setPeerWishlist(List<String> peerWishlist) {
        List<String> oldPeerWishlist = this.peerWishlist;
        this.peerWishlist = peerWishlist;
        propertyChangeSupport.firePropertyChange("peerWishlist", oldPeerWishlist, peerWishlist);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getPeerWishlist() {
        return this.peerWishlist;
    }

    /**
     * {@inheritDoc}
     */
    public void setItemsToGive(List<String> itemsToGive) {
        List<String> oldItems = this.itemsToGive;
        this.itemsToGive = itemsToGive;
        propertyChangeSupport.firePropertyChange("itemsToGive", oldItems, itemsToGive);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getItemsToGive() {
        return itemsToGive;
    }

    /**
     * {@inheritDoc}
     */
    public void setAllUsers(List<String> allUsers) {
        List<String> oldUsers = this.allUsers;
        this.allUsers = allUsers;
        propertyChangeSupport.firePropertyChange("allUsers", oldUsers, allUsers);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getAllUsers() {
        return allUsers;
    }

    /**
     * {@inheritDoc}
     */
    public void setHourChosen(int i) {
        int oldHourChosen = hourChosen;
        hourChosen = i;
        propertyChangeSupport.firePropertyChange("hourChosen", oldHourChosen, i);
    }

    /**
     * {@inheritDoc}
     */
    public int getHourChosen() {
        return hourChosen;
    }

    /**
     * {@inheritDoc}
     */
    public void setMinuteChosen(int i) {
        int oldMinuteChosen = minuteChosen;
        minuteChosen = i;
        propertyChangeSupport.firePropertyChange("minuteChosen", oldMinuteChosen, i);
    }

    /**
     * {@inheritDoc}
     */
    public int getMinuteChosen() {
        return minuteChosen;
    }
}
