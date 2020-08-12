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

    /**
     * Constructor for observable inventory presenter.
     * @param localizedResources Pack containing any localized strings.
     */
    public UIInventoryPresenter(ResourceBundle localizedResources) {

        super();
        this.localizedResources = localizedResources;
        setSelectedItemName("");
        setSelectedItemDesc("");
        setApprovedItems(new ArrayList<>());
        setPendingItems(new ArrayList<>());
        setError("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setError(String errorKey) {
        String oldErrorMsg = this.errorMsg;
        this.errorMsg = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("error", oldErrorMsg, this.errorMsg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getError() {
        return errorMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApprovedItems(List<String> approvedItems) {
        List<String> oldApprovedItems = this.approvedItems;
        this.approvedItems = approvedItems;
        propertyChangeSupport.firePropertyChange("approvedItems", oldApprovedItems, this.approvedItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getApprovedItems() {
        return approvedItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingItems(List<String> pendingItems) {
        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, this.pendingItems);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPendingItems() {
        return pendingItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemName(String name) {
        String oldSelectedItemName = this.selectedItemName;
        this.selectedItemName = name;
        propertyChangeSupport.firePropertyChange("selectedItemName", oldSelectedItemName, this.selectedItemName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedItemDesc(String desc) {
        String oldSelectedItemDesc = this.selectedItemDesc;
        this.selectedItemDesc = desc;
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldSelectedItemDesc, this.selectedItemDesc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }
}
