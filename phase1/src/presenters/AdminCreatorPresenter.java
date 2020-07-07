package presenters;

/**
 * interface for the presenter handling the log-in of admin
 * @author Catherine
 */

public interface AdminCreatorPresenter {
    /**
     * creates admin username
     * @return username
     */
    String createAdminUsername();

    /**
     * create admin password
     * @return password
     */
    String createAdminPassword();

    /**
     * lets user know that their input for username and/or password is invalid
     */
    void invalidInput();
}
