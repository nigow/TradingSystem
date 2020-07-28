package org.twelve.controllers;

import org.twelve.presenters.experimental.ItemApprovalPresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;
import org.twelve.usecases.UseCasePool;

import java.util.List;


/**
 * A controller to deal with the approval of items by admins.
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class ItemApprovalController {

    /**
     * An instance of ItemManager to use items
     */
    private final ItemManager itemManager;

    /**
    * An instance of ItemApprovalPresenter to use to display pending items
    */
    private final ItemApprovalPresenter itemApprovalPresenter;

    /**
     * Constructor to intialize useCasePool and itemApprovalPresenter
     *
     * @param useCasePool
     * @param itemApprovalPresenter
     */
    public ItemApprovalController(UseCasePool useCasePool, ItemApprovalPresenter itemApprovalPresenter) {
        this.itemManager = useCasePool.getItemManager();
        this.itemApprovalPresenter = itemApprovalPresenter;
    }


    /**
     * Calls the presenter to display all pending items
     */
    public void displayAllPending() {
        List<String> allPendingItems = itemManager.getDisapprovedString();
        itemApprovalPresenter.displayPendingItems(allPendingItems);
    }

    public void approveItem(String ind) {
        //TODO: figure out index removal
        itemManager.updateApproval(itemManager.getDisapprovedIDs().get(Integer.parseInt(ind)), true);
    }
}
