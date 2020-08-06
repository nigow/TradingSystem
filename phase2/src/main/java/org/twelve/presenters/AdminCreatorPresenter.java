package org.twelve.presenters;

/**
 * Interface for handling the login of admin.
 *
 * @author Catherine
 */

public interface AdminCreatorPresenter {
    /**
     * Creates admin username.
     *
     * @return Username of admin
     */
    String createAdminUsername();

    /**
     * Create admin password.
     *
     * @return Password of admin
     */
    String createAdminPassword();

    /**
     * Create admin location.
     *
     * @return Location of admin
     */
    String createAdminLocation();

    /**
     * tells user their username and/or password is invalid
     */
    void displayInvalidInfo();

    /**
     * tells user that the admin account has been made successfully
     */
    void displaySuccessfulAccount();

    /**
     * tells user that an account has already been made with the info
     */
    void displayOverlappingInfo();
}
