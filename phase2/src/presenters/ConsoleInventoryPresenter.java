package presenters;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for showing the inventory page.
 *
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class ConsoleInventoryPresenter implements InventoryPresenter {
    /**
     * {@inheritDoc}
     */
    @Override
    public String displayInventoryOptions(List<String> InventoryOptions) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option to do:");
        for (int i = 0; i < InventoryOptions.size(); i++) {
            System.out.println(i + ". " + InventoryOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addToWishlist() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to add to your wishlist, or -1 to go back: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String askName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the item you wish to add, or -1 to go back (no commas): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String askDescription() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the description of the item you wish to add, or -1 to go back (no commas): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String confirmItem(String name, String description) {
        Scanner input = new Scanner(System.in);
        System.out.print(name + ": " + description + "\nIs this correct? (y/n/-1 to go back) :");
        return input.nextLine();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String removeFromInventory() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you wish to remove, or -1 to go back: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String approveItem() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you wish to approve, or -1 to go back: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Your input was invalid. Please try again.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abortMessage() {
        System.out.println("Action successfully cancelled");
    }

    @Override
    public void displayApprovedItems(List<String> approvedItems) {
        System.out.println("Your approved items:");
        for (int i = 0; i < approvedItems.size(); i++) {
            System.out.println(i + ". " + approvedItems.get(i));
        }
    }

    @Override
    public void displayAllItems(List<String> allItems) {
        System.out.println("All your items, including pending items:");
        for (int i = 0; i < allItems.size(); i++) {
            System.out.println(i + ". " + allItems.get(i));
        }
    }

    @Override
    public void displayUserPendingItems(List<String> pendingItems) {
        System.out.println("Your pending items:");
        for (int i = 0; i < pendingItems.size(); i++) {
            System.out.println(i + ". " + pendingItems.get(i));
        }
    }

    @Override
    public void displayAvailableItems(List<String> availableItems) {
        System.out.println("Items awaiting approval:");
        for (int i = 0; i < availableItems.size(); i++) {
            System.out.println(i + ". " + availableItems.get(i));
        }
    }

    @Override
    public void displayAllPendingItems(List<String> pendingItems) {
        for (int i = 0; i < pendingItems.size(); i++) {
            System.out.println(i + ". " + pendingItems.get(i));
        }
    }

    @Override
    public void displayDoesNotCorrespond() {
        System.out.println("That number does not correspond to an item");
    }

    @Override
    public void commaError() {
        System.out.println("There cannot be a comma.");
    }

    @Override
    public void itemError() {
        System.out.println("Item not added.");
    }

    @Override
    public void itemSuccess() {
        System.out.println("Item successfully added.");
    }

    @Override
    public void pending() {
        System.out.println("Item is now pending admin approval.");
    }

    @Override
    public void itemRemovalSuccess() {
        System.out.println("Item has been successfully removed.");
    }

    @Override
    public void itemRemovalError() {
        System.out.println("Item was not successfully removed.");
    }

    @Override
    public void itemApproved() {
        System.out.println("Item has been approved.");
    }

    @Override
    public void displayOthersItems(List<String> othersItems) {
        System.out.println("Items available for trading: ");
        for (int i = 0; i < othersItems.size(); i++) {
            System.out.println(i + ". " + othersItems.get(i));
        }
    }

    @Override
    public void displayUserItems(List<String> userItems) {
        System.out.println("All your items: ");
        for (int i = 0; i < userItems.size(); i++) {
            System.out.println(i + ". " + userItems.get(i));
        }
    }
}
