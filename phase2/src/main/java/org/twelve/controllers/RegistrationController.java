package org.twelve.controllers;

import org.twelve.entities.AccountType;
import org.twelve.entities.Permissions;
import org.twelve.presenters.RegistrationPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.StatusManager;
import org.twelve.usecases.UseCasePool;

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

    /**
     * Initializer for RegistrationController
     * @param useCasePool used to get all the use cases.
     */
    public RegistrationController(UseCasePool useCasePool, RegistrationPresenter registrationPresenter) {
        this.accountRepository = useCasePool.getAccountRepository();
        this.sessionManager = useCasePool.getSessionManager();
        this.statusManager = useCasePool.getStatusManager();
        this.registrationPresenter = registrationPresenter;
    }

    /**
     * A method for updating if user can create admins
     */
    public void updateAccessMode() {
        List<AccountType> availableTypes = new ArrayList<>();
        availableTypes.add(AccountType.DEMO);
        availableTypes.add(AccountType.USER);

        if (sessionManager.getCurrAccountID() != -1 && statusManager.hasPermission(sessionManager.getCurrAccountID(),
                Permissions.ADD_ADMIN)) {

            availableTypes.add(AccountType.ADMIN);

        }

        registrationPresenter.setAvailableTypes(availableTypes);
    }

    /**
     * A method to create an account
     * @param username the username of the account
     * @param password the password of the account
     * @param accountType the account type of the account
     * @return true if the account has been successfully created.
     */
    public boolean createAccount(String username, String password, String accountType) {
        List<Permissions> perms = null;
        switch (accountType) {
            case "Admin":
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
            case "Standard":
                perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.CREATE_ITEM,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.LEND,
                        Permissions.REQUEST_VACATION,
                        Permissions.BORROW,
                        Permissions.BROWSE_INVENTORY);
                break;
            case "Demo":
                perms = Arrays.asList(Permissions.LOGIN,
                        Permissions.ADD_TO_WISHLIST,
                        Permissions.BROWSE_INVENTORY);
                break;
        }
        return accountRepository.createAccount(username, password, perms);

    }

    public void setRegistrationPresenter(RegistrationPresenter registrationPresenter) {
        this.registrationPresenter = registrationPresenter;
    }
}
