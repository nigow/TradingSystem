package org.twelve.presenters;

import java.text.MessageFormat;
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

    public UIWarehousePresenter(Locale locale) {
        localizedResources = ResourceBundle.getBundle("org.twelve.presenters.Warehouse", locale);
    }

    @Override
    public void setPendingItems(List<String> pendingItems) {
        this.pendingItems = pendingItems;
    }

    @Override
    public List<String> getPendingItems() {
        return pendingItems;
    }

    @Override
    public void setSelectedItemName(String name) {
        selectedItemName = MessageFormat.format(localizedResources.getString("itemName"), name);
    }

    @Override
    public String getSelectedItemName() {
        return selectedItemName;
    }

    @Override
    public void setSelectedItemDesc(String desc) {
        selectedItemDesc = MessageFormat.format(localizedResources.getString("itemDesc"), desc);
    }

    @Override
    public String getSelectedItemDesc() {
        return selectedItemDesc;
    }
}
