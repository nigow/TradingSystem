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
     * Presents the user with a custom message.
     *
     * @param message Message to display
     */
    void showMessage(String message);
}
