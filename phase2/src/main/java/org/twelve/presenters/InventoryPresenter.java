package org.twelve.presenters;

import java.util.List;

public interface InventoryPresenter {

    void setApprovedItems(List<String> approvedItems);
    List<String> getApprovedItems();
    void setPendingItems(List<String> pendingItems);
    List<String> getPendingItems();
    void setSelectedItemName(String name);
    String getSelectedItemName();
    void setSelectedItemDesc(String desc);
    String getSelectedItemDesc();

}
