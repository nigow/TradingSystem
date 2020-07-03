package presenters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A presenter for the inventory page
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
            System.out.println(i + "-" + InventoryOptions.get(i));
        }
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayInventory(List<String> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(i + " " + inventory.get(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addToWishlist() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to add to your wishlist: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    public List<String> createItem() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the name of the item you wish to add (no commas): ");
        String name = input.nextLine();
        System.out.print("Enter the description of the item you wish to add (no commas): ");
        String description = input.nextLine();
        System.out.print(name + ": " + description +"\nIs this correct? (if so, type 'yes'): ");
        String yes = input.nextLine();
        List<String> inputs = new ArrayList<>();
        inputs.add(name);
        inputs.add(description);
        inputs.add(yes);
        return inputs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeFromInventory() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you wish to remove: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    public String approveItem() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of the item you wish to approve: ");
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
    public void customMessage(String message) {
        System.out.println(message);
    }

}
