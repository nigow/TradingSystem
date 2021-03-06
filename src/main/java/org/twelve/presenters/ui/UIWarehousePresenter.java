package org.twelve.presenters.ui;

import org.twelve.presenters.WarehousePresenter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Presenter for displaying pending items.
 */
public class UIWarehousePresenter extends ObservablePresenter implements WarehousePresenter {

    private List<String> pendingItems;
    private List<String> approvedItems;
    private String selectedItemName;
    private String selectedItemDesc;

    private final ResourceBundle localizedResources;

    /**
     * Constructor for observable warehouse presenter.
     *
     * @param localizedResources Pack containing any localized strings.
     */
    public UIWarehousePresenter(ResourceBundle localizedResources) {

        super();
        this.localizedResources = localizedResources;

        setSelectedItemName("");
        setSelectedItemDesc("");
        setPendingItems(new ArrayList<>());
        setApprovedItems(new ArrayList<>());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingItems(List<String> pendingItems) {
        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, pendingItems);
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
    public void setSelectedItemName(String name) {

        String oldSelectedItemName = selectedItemName;
        selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
        propertyChangeSupport.firePropertyChange("selectedItemName", oldSelectedItemName, selectedItemName);

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

        String oldSelectedItemDesc = selectedItemDesc;
        selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldSelectedItemDesc, selectedItemDesc);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }

}
