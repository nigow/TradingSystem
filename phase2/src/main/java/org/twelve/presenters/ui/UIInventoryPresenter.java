package org.twelve.presenters.ui;


import org.twelve.presenters.InventoryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for displaying your inventory
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class UIInventoryPresenter extends ObservablePresenter implements InventoryPresenter {

    private List<String> approvedItems;
    private List<String> pendingItems;

    public UIInventoryPresenter() {

        super();
        setInventoryItems(new ArrayList<>(), new ArrayList<>()); // todo: figure out if we can initialize somewhere else

    }

    public void displayInventory(List<String> items) {
        //TODO: figure out how to make the view observe this.
    }

    @Override
    public void setInventoryItems(List<String> approvedItems, List<String> pendingItems) {

        List<String> oldApprovedItems = this.approvedItems;
        this.approvedItems = approvedItems;
        propertyChangeSupport.firePropertyChange("approvedItems", oldApprovedItems, approvedItems);

        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, pendingItems);
    }

    @Override
    public List<String> getApprovedItems() {
        return approvedItems;
    }

    @Override
    public List<String> getPendingItems() {
        return pendingItems;
    }
}
