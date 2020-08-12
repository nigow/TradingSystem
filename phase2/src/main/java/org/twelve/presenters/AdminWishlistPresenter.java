package org.twelve.presenters;

import java.util.List;

public interface AdminWishlistPresenter {
    void setWishlistOfUser(List<String> itemsToReceive);
    List<String> getWishlistOfUser();
    void setAllUsers(List<String> allUsers);
    List<String> getAllUsers();
    void setSelectedItemDescription(String itemToLend);
    String getSelectedItemDescription();
}
