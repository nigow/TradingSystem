package org.twelve.presenters.ui;


import org.twelve.presenters.InventoryPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Presenter for displaying your inventory
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class UIInventoryPresenter extends ObservablePresenter implements InventoryPresenter {

    private List<String> approvedItems;
    private List<String> pendingItems;

    private String selectedItemName;
    private String selectedItemDesc;
    private String errorMsg;

    private final ResourceBundle localizedResources;

    public UIInventoryPresenter(ResourceBundle localizedResources) {

        super();
        this.localizedResources = localizedResources;
        setSelectedItemName("");
        setSelectedItemDesc("");
        setApprovedItems(new ArrayList<>());
        setPendingItems(new ArrayList<>());
        setError("");
    }

    @Override
    public void setError(String errorKey) {
        String oldErrorMsg = this.errorMsg;
        this.errorMsg = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("error", oldErrorMsg, this.errorMsg);
    }

    @Override
    public String getError() {
        return errorMsg;
    }

    @Override
    public void setApprovedItems(List<String> approvedItems) {
        List<String> oldApprovedItems = this.approvedItems;
        this.approvedItems = approvedItems;
        propertyChangeSupport.firePropertyChange("approvedItems", oldApprovedItems, this.approvedItems);
    }

    @Override
    public List<String> getApprovedItems() {
        return approvedItems;
    }

    @Override
    public void setPendingItems(List<String> pendingItems) {
        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, this.pendingItems);
    }

    @Override
    public List<String> getPendingItems() {
        return pendingItems;
    }

    @Override
    public void setSelectedItemName(String name) {
        String oldSelectedItemName = this.selectedItemName;
        this.selectedItemName = name;
        propertyChangeSupport.firePropertyChange("selectedItemName", oldSelectedItemName, this.selectedItemName);
    }

    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    @Override
    public void setSelectedItemDesc(String desc) {
        String oldSelectedItemDesc = this.selectedItemDesc;
        this.selectedItemDesc = desc;
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldSelectedItemDesc, this.selectedItemDesc);
    }

    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }
}
