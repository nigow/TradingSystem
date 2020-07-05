package presenters;

/**
 * interface for the presenter handling the log-in of admin
 * @author Catherine
 */

public interface AdminPresenter {
    /**
     * creates admin account
     * @return username and password
     */
    public String[] createAdmin();

    /**
     * lets user know that their input for username and/or password is invalid
     */
    public void invalidInput();
}
