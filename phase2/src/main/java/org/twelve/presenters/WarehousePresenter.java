package org.twelve.presenters;

import java.util.List;

public interface WarehousePresenter {

    void setPendingItems(List<String> pendingItems);
    List<String> getPendingItems();
    void setApprovedItems(List<String> approvedItems);
    List<String> getApprovedItems();

    void setSelectedItemName(String name);
    String getSelectedItemName();
    void setSelectedItemDesc(String desc);
    String getSelectedItemDesc();

}
