package org.twelve.presenters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Presenter for displaying pending items.
 *
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class UIWarehousePresenter implements WarehousePresenter {

    private List<String> pendingItems;
    private String selectedItemName;
    private String selectedItemDesc;

    private final ResourceBundle localizedResources;
    private final PropertyChangeSupport propertyChangeSupport;

    public UIWarehousePresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        propertyChangeSupport = new PropertyChangeSupport(this);

        setSelectedItemName("");
        setSelectedItemDesc("");
        setPendingItems(new ArrayList<>());
    }

    @Override
    public void setPendingItems(List<String> pendingItems) {
        List<String> oldPendingItems = this.pendingItems;
        this.pendingItems = pendingItems;
        propertyChangeSupport.firePropertyChange("pendingItems", oldPendingItems, pendingItems);
    }

    @Override
    public List<String> getPendingItems() {
        return pendingItems;
    }

    @Override
    public void setSelectedItemName(String name) {

        String oldSelectedItemName = selectedItemName;
        selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
        propertyChangeSupport.firePropertyChange("selectedItemName", oldSelectedItemName, selectedItemName);

    }

    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    @Override
    public void setSelectedItemDesc(String desc) {

        String oldSelectedItemDesc = selectedItemDesc;
        selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
        propertyChangeSupport.firePropertyChange("selectedItemDesc", oldSelectedItemDesc, selectedItemDesc);

    }

    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
