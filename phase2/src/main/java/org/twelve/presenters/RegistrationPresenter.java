package org.twelve.presenters;

import java.util.List;

/**
 * Presenter for registration.
 */
public interface RegistrationPresenter {

    /**
     * Set registration error.
     * @param errorKey Key of registration error.
     */
    void setError(String errorKey);

    /**
     * Get registration error.
     * @return Registration error.
     */
    String getError();

    /**
     * Set whether registration is being performed by an admin.
     * @param adminMode Whether registration is being performed by an admin.
     */
    void setAdminMode(boolean adminMode);

    /**
     * Get whether registration is being performed by an admin.
     * @return Whether registration is being performed by an admin.
     */
    boolean getAdminMode();

    /**
     * Set existing cities.
     * @param existingCities List of existing cities.
     */
    void setExistingCities(List<String> existingCities);

    /**
     * Get existing cities.
     * @return List of existing cities.
     */
    List<String> getExistingCities();

}
