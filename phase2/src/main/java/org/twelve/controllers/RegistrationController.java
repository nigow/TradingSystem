package org.twelve.controllers;

import org.twelve.entities.AccountType;
import org.twelve.entities.Permissions;
import org.twelve.presenters.RegistrationPresenter;
import org.twelve.usecases.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for registering an account
 * @author Ethan (follow him on instagram @ethannomiddlenamelam)
 */
public class RegistrationController {

    /**
     * An instance of AccountRepository to deal with creating accounts
     */
    private final AccountRepository accountRepository;

    /**
     * An instance of SessionManager to deal with the current session
     */
    private final SessionManager sessionManager;

    /**
     * An instance of StatusManager to get if an account has a permission
     */
    private final StatusManager statusManager;

    private RegistrationPresenter registrationPresenter;

    private List<AccountType> availableTypes;

    private CityManager cityManager;

    /**
     * Initializer for RegistrationController
     * @param useCasePool used to get all the use cases.
     */
    public RegistrationController(UseCasePool useCasePool) {
        this.accountRepository = useCasePool.getAccountRepository();
        this.sessionManager = useCasePool.getSessionManager();
        this.statusManager = useCasePool.getStatusManager();
        this.cityManager = useCasePool.getCityManager();
    }

    /**
     * A method for updating if user can create admins
     * @return Whether user can create admins
     */
    public void updateOptions() {
        availableTypes = new ArrayList<>();

        if (sessionManager.getCurrAccountID() == -1) {

            availableTypes.addAll(Arrays.asList(AccountType.values()));
            availableTypes.remove(AccountType.ADMIN);

        } else {

            availableTypes.add(AccountType.ADMIN);

        }

        registrationPresenter.setAvailableTypes(availableTypes);

        registrationPresenter.setExistingCities(cityManager.getAllCities());

    }

    /**
     * A method to create an account
     * @param username the username of the account
     * @param password the password of the account
     * @param typeIndex index corresponding to account type of the account
     * @return true if the account has been successfully created.
     */
    public boolean createAccount(String username, String password, String location, int typeIndex) {
        List<Permissions> perms = null;
        switch (availableTypes.get(typeIndex)) {
            case ADMIN:
                perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.FREEZE,
                        Permissions.UNFREEZE,
                        Permissions.CREATE_ITEM,
                        Permissions.CONFIRM_ITEM,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.LEND,
                        Permissions.BORROW,
                        Permissions.BROWSE_INVENTORY,
                        Permissions.CHANGE_THRESHOLDS,
                        Permissions.ADD_ADMIN,
                        Permissions.REQUEST_UNFREEZE,
                        Permissions.REQUEST_UNFREEZE,
                        Permissions.REQUEST_VACATION,
                        Permissions.MAKE_TRUSTED,
                        Permissions.CAN_BAN);
                break;
            case USER:
                perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.CREATE_ITEM,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.LEND,
                        Permissions.REQUEST_VACATION,
                        Permissions.BORROW,
                        Permissions.BROWSE_INVENTORY);
                break;
            case DEMO:
                perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.BROWSE_INVENTORY);
                break;
        }

        if (!cityManager.getAllCities().contains(location)) cityManager.createCity(location);

        if (accountRepository.createAccount(username, password, perms, location)) {

            if (sessionManager.getCurrAccountID() == -1) sessionManager.login(username);
            return true;

        }

        return false;
    }

    public void setRegistrationPresenter(RegistrationPresenter registrationPresenter) {
        this.registrationPresenter = registrationPresenter;
    }
}
