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
            public void displayApprovedItems(List<String> approvedItems) {

            }

            @Override
            public void displayAllItems(List<String> allItems) {

            }

            @Override
            public void displayUserPendingItems(List<String> pendingItems) {

            }

            @Override
            public void displayAvailableItems(List<String> availableItems) {

            }

            @Override
            public void displayAllPendingItems(List<String> pendingItems) {

            }

            @Override
            public void displayOthersItems(List<String> othersItems) {

            }

            @Override
            public void displayUserItems(List<String> userItems) {

            }

            @Override
            public void displayDoesNotCorrespond() {

            }

            @Override
            public void commaError() {

            }

            @Override
            public void itemError() {

            }

            @Override
            public void itemSuccess() {

            }

            @Override
            public void pending() {

            }

            @Override
            public void itemRemovalSuccess() {

            }

            @Override
            public void itemRemovalError() {

            }

            @Override
            public void itemApproved() {

            }


        };

    }

}
