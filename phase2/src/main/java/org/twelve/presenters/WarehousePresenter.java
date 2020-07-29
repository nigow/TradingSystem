package org.twelve.presenters;

import java.util.List;

public interface WarehousePresenter {

    void setPendingItems(List<String> pendingItems);
    List<String> getPendingItems();
    void setSelectedItemName(String name);
    String getSelectedItemName();
    void setSelectedItemDesc(String desc);
    String getSelectedItemDesc();

}
