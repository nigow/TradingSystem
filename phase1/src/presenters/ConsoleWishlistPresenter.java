package presenters;

import java.util.List;
import java.util.Scanner;

public class ConsoleWishlistPresenter implements WishlistPresenter {
    @Override
    public String displayWishlistOptions(List<String> wishlistOptions) {
        for (int i = 0; i < wishlistOptions.size(); i++) {

            System.out.println(i + ". " + wishlistOptions.get(i));

        }
        Scanner input = new Scanner(System.in);
        System.out.print("Select action: ");
        return input.next();
    }

    @Override
    public void displayWishlist(List<String> wishlist) {
        for (int i = 0; i < wishlist.size(); i++) {

            System.out.println(i + ". " + wishlist.get(i));

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
}
