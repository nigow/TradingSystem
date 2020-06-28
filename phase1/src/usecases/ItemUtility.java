package usecases;

import entities.Item;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class for items to access certain types of items
 * @author Isaac
 */
public class ItemUtility {

    /**
     * The manager for editing items
     */
    private final ItemManager itemManager;

    /**
     * Constructor for ItemUtility
     * @param itemManager A manager for editing items
     */
    public ItemUtility(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    /**
     * Retrieves all approved items in the system
     * @return List of all approved items in the system
     */
    public List<Item> getApproved() {
        List<Item> approvedItems = new ArrayList<>();
        for (Item item : itemManager.getAllItems()) {
            if (item.isApproved()) {
                approvedItems.add(item);
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves a string representation of all approved items in the system
     * @return List of all approved items in the system in string format
     */
    public List<String> getApprovedString() {
        List<String> approvedItems = new ArrayList<>();
        for (Item item : itemManager.getAllItems()) {
            if (!item.isApproved()) {
                approvedItems.add(item.toString());
            }
        }
        return approvedItems;
    }

    /**
     * Retrieves all non-approved items in the system
     * @return List of all non-approved items in the system
     */
    public List<Item> getDisapproved() {
        List<Item> disapprovedItems = new ArrayList<>();
        for (Item item : itemManager.getAllItems()) {
            if (!item.isApproved()) {
                disapprovedItems.add(item);
            }
        }
        return disapprovedItems;
    }

    /**
     * Retrieves a string representation of all non-approved items in the system
     * @return List of all non-approved items in the system in string format
     */
    public List<String> getDisapprovedString() {
        List<String> disapprovedItems = new ArrayList<>();
        for (Item item : itemManager.getAllItems()) {
            if (!item.isApproved()) {
                disapprovedItems.add(item.toString());
            }
        }
        return disapprovedItems;
    }

}
