package org.twelve.presenters;

import java.util.List;

public class MockMenuPresenter implements MenuPresenter {
    private String[] options;
    private int counter;


    @Override
    public String displayMenu(List<String> menuOptions) {
        return options[counter++];
    }

    @Override
    public void invalidInput() {
        //pass
    }

    @Override
    public String manageTrades() {
        return null;
    }

    @Override
    public String browseInventory() {
        return null;
    }

    @Override
    public String manageWishlist() {
        return null;
    }

    @Override
    public String initiateTrade() {
        return null;
    }

    @Override
    public String modifyRestrictions() {
        return null;
    }

    @Override
    public String manageFrozen() {
        return null;
    }

    @Override
    public String addAdmin() {
        return null;
    }

    @Override
    public String requestUnfreeze() {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
