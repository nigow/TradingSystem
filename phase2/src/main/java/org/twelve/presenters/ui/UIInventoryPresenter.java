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

    @Override
    public void setInventoryItems(List<String> approvedItems, List<String> pendingItems) {

        List<String> oldApprovedItems = this.approvedItems;
        this.approvedItems = approvedItems;
        propertyChangeSupport.firePropertyChange("approvedItems", oldApprovedItems, this.approvedItems);

        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, this.pendingItems);
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
