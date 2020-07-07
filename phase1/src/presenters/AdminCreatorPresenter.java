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
     * presents the user with a custom message
     */
    void showMessage(String message);
}
