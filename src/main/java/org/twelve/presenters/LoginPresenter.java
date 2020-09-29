package org.twelve.presenters;

/**
 * Interface for presenters dealing with the login view
 */
public interface LoginPresenter {

    /**
     * Set the error message for unsuccessful logins
     *
     * @param errorKey the error message for unsuccessful logins
     */
    void setError(String errorKey);

    /**
     * Get the error message for unsuccessful logins
     *
     * @return the error message for unsuccessful logins
     */
    String getError();

}
