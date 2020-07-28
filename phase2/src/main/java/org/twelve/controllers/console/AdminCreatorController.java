package org.twelve.controllers.console;



import org.twelve.controllers.InputHandler;
import org.twelve.entities.Permissions;
import org.twelve.presenters.AdminCreatorPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.UseCasePool;

import java.util.Arrays;
import java.util.List;

/**
 * Controller that creates new admin accounts.
 *
 * @author Catherine
 */
public class AdminCreatorController {
    /**
     * An instance of AdminCreatorPresenter to let user know what to do.
     */
    private final AdminCreatorPresenter adminPresenter;
    /**
     * An instance of AccountRepository to create new account.
     */
    private final AccountRepository accountRepository;
    /**
     * An instance of ControllerInputValidator to check for valid input.
     */
    private final InputHandler inputHandler;

    /**
     * Initializes AdminCreatorController with necessary presenter and use cases.
     *
     * @param useCasePool    An instance of ManualConfig to get use cases
     * @param adminPresenter An instance of AdminPresenter to display information
     */
    public AdminCreatorController(UseCasePool useCasePool, AdminCreatorPresenter adminPresenter) {
        accountRepository = useCasePool.getAccountRepository();
        this.adminPresenter = adminPresenter;
        inputHandler = new InputHandler();
    }

    /**
     * Calls presenters to get username and password for new admin account.
     */
    public void run() {
        while (true) {
            String username = adminPresenter.createAdminUsername();
            if (inputHandler.isExitStr(username))
                return;
            String password = adminPresenter.createAdminPassword();
            if (inputHandler.isExitStr(password))
                return;
            if (!inputHandler.isValidUserPass(username, password))
                adminPresenter.displayInvalidInfo();
            else {
                List<Permissions> perms = Arrays.asList(Permissions.LOGIN,
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
                        Permissions.REQUEST_VACATION,
                        Permissions.CAN_BAN,
                        Permissions.MAKE_TRUSTED);

                if (accountRepository.createAccount(username, password, perms)) {
                    adminPresenter.displaySuccessfulAccount();
                    return;
                } else
                    adminPresenter.displayOverlappingInfo();
            }
        }
    }

}
