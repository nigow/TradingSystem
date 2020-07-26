package org.twelve.controllers.experimental;


import org.twelve.controllers.UseCasePool;
import org.twelve.presenters.experimental.ItemApprovalPresenter;
import org.twelve.usecases.ItemManager;
import org.twelve.usecases.ItemUtility;

import java.util.List;


/**
 * A controller to deal with the approval of items by admins.
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class ItemApprovalController {


    /**
     * An instance of ItemUtility to utilize items
     */
    private final ItemUtility itemUtility;

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
        this.itemUtility = useCasePool.getItemUtility();
        this.itemManager = useCasePool.getItemManager();
        this.itemApprovalPresenter = itemApprovalPresenter;
    }


    /**
     * Calls the presenter to display all pending items
     */
    private void displayAllPending() {
        List<String> allPendingItems = itemUtility.getDisapprovedString();
        itemApprovalPresenter.displayPendingItems(allPendingItems);
    }

    private void approveItem(String ind) {
        //TODO: figure out index removal
        itemManager.updateApproval(itemUtility.getDisapproved().get(Integer.parseInt(ind)), true);
    }
}
