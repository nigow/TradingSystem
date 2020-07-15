package controllers;


import gateways.UseCasePool;
import org.junit.Test;
import presenters.InventoryPresenter;
import java.util.List;

public class InventoryControllerTest {
    private UseCasePool useCasePool;


    @Test
    public void validinput() {
        InventoryPresenter mockInventoryPresenter =  new InventoryPresenter() {
            public String displayInventoryOptions(List<String> s) {
                return null;
            }

            @Override
            public void displayInventory(List<String> inventory) {

            }

            @Override
            public String addToWishlist() {
                return null;
            }

            @Override
            public String askName() {
                return null;
            }

            @Override
            public String askDescription() {
                return null;
            }

            @Override
            public String confirmItem(String name, String description) {
                return null;
            }

            @Override
            public String removeFromInventory() {
                return null;
            }

            @Override
            public String approveItem() {
                return null;
            }

            @Override
            public void invalidInput() {

            }

            @Override
            public void abortMessage() {

            }

            @Override
            public void customMessage(String message) {

            }
        };

    }

}
