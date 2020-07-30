package org.twelve.presenters.console;

import java.util.List;
import java.util.Scanner;

/**
 * Console presenter for managing the wishlist and starting trades.
 */
public class ConsoleWishlistPresenter implements WishlistPresenter {

    private final Scanner input;

    /**
     * Create a console presenter for {@link org.twelve.controllers.console.WishlistController}.
     */
    public ConsoleWishlistPresenter() {
        input = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayWishlistOptions(List<String> wishlistOptions) {
        for (int i = 0; i < wishlistOptions.size(); i++) {

            System.out.println(i + ". " + wishlistOptions.get(i));

        }
        System.out.print("Select action: ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayWishlist(List<String> wishlist) {
        System.out.println("Wishlist items:");
        for (int i = 0; i < wishlist.size(); i++) {

            System.out.println(i + ". " + wishlist.get(i));

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String startTrade() {
        System.out.print("Select desired item (index, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeFromWishlist() {
        System.out.print("Select item to remove (index, -1 to abort): ");
        return input.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidInput() {
        System.out.println("Invalid input, try again.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String viewWishlist() {
        String message = "View wishlist.";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeItem() {
        String message = "Remove item from wishlist.";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String startNewTrade() {
        String message = "Start trade.";
        System.out.println(message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String back() {
        String message = "Back.";
        System.out.println(message);
        return message;
    }
}
