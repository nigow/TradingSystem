package presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleWishlistPresenter implements WishlistPresenter {
    @Override
    public String displayWishlistOptions(List<String> WishlistOptions) {
        for (String action: WishlistOptions) {
            System.out.println(action);
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Select action: ");
        return input.next();
    }

    @Override
    public void displayWishlist(List<String> wishlist) {
        for (String itemStr : wishlist) {
            System.out.println(itemStr);
        }
    }

    @Override
    public String startTrade() {
        Scanner input = new Scanner(System.in);
        System.out.print("Select desired item (index): ");
        return input.next();
    }

    @Override
    public String removeFromWishlist() {
        Scanner input = new Scanner(System.in);
        System.out.print("Select item to remove (index): ");
        return input.next();
    }

    @Override
    public void invalidInput() {
        System.out.println("Invalid input, try again.");
    }

    @Override
    public void returnToMenu() {

    }
}
