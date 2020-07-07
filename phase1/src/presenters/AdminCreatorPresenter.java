package presenters;

/**
 * Interface for handling the login of admin.
 *
 * @author Catherine
 */

public interface AdminCreatorPresenter {
    /**
     * Creates admin username.
     *
     * @return username
     */
    String createAdminUsername();

    /**
     * Create admin password.
     *
     * @return password
     */
    String createAdminPassword();

    /**
     * Presents the user with a custom message.
     */
    void showMessage(String message);
}
