package org.twelve.presenters;

import java.util.List;

/**
 * Presenter for the warehouse.
 */
public interface WarehousePresenter {

    /**
     * Set unapproved items.
     * @param pendingItems List of names of unapproved items.
     */
    void setPendingItems(List<String> pendingItems);

    /**
     * Get unapproved items.
     * @return List of names of unapproved items.
     */
    List<String> getPendingItems();

    /**
     * Set approved items.
     * @param approvedItems List of names of approved items.
     */
    void setApprovedItems(List<String> approvedItems);

    /**
     * Get approved items.
     * @return List of names of approved items.
     */
    List<String> getApprovedItems();

    /**
     * Set name of selected item.
     * @param name Name of selected item.
     */
    void setSelectedItemName(String name);

    /**
     * Get name of selected item.
     * @return Name of selected item.
     */
    String getSelectedItemName();

    /**
     * Set description of selected item.
     * @param desc Description of selected item.
     */
    void setSelectedItemDesc(String desc);

    /**
     * Get description of selected item.
     * @return Description of selected item.
     */
    String getSelectedItemDesc();

}
