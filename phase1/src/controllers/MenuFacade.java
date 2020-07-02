package controllers;

import gateways.ManualConfig;
import presenters.ConsoleMenuPresenter;
import presenters.MenuPresenter;
import usecases.AuthManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages a user's options according to their permissions.
 * @author Maryam
 */
public class MenuFacade {

    /**
     * An instance of AuthManager to access a user's permissions.
     */
    private AuthManager authManager;

    /**
     * An instance of MenuPresenter to display options.
     */
    private MenuPresenter menuPresenter;

    /**
     * Initializes MenuFacade based on information from ManualConfig and creates instances of
     * AuthManager and necessary controllers.
     * @param mc An instance of ManualConfig
     */
    public MenuFacade(ManualConfig mc) {
        authManager = mc.getAuthManager();
        menuPresenter = new ConsoleMenuPresenter();
    }

    /**
     * Calls the presenter with options for the user based on their permission and executes the action.
     */
    public void run() {
        List<String> options = new ArrayList<>();
        List<Runnable> method = new ArrayList<>();

        /*
        add options and which controller 'run' should be called based on that
        LOGIN,
        FREEZE,
        UNFREEZE,
        CREATE_ITEM,
        CONFIRM_ITEM,
        ADD_TO_WISHLIST,
        LEND,
        BORROW,
        BROWSE_INVENTORY,
        CHANGE_RESTRICTIONS,
        ADD_ADMIN,
        REQUEST_UNFREEZE
        */

        // TODO waiting on all controllers

        method.add(() -> {});

        String action = menuPresenter.displayMenu(options);
        try {
            int i = Integer.parseInt(action);
            if (0 <= i && i < method.size()) {
                method.get(i).run();
            } else {
                menuPresenter.invalidInput();
                run();
            }
        } catch (NumberFormatException e) {
            menuPresenter.invalidInput();
            run();
        }
    }
}
