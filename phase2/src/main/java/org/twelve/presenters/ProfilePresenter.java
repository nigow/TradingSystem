package org.twelve.presenters;

import java.util.List;

/**
 * Presenter for profile.
 */
public interface ProfilePresenter {

    /**
     * Set password error.
     * @param errorKey Key of password error.
     */
    void setPasswordError(String errorKey);

    /**
     * Get password error.
     * @return Password error.
     */
    String getPasswordError();

    /**
     * Set location error.
     * @param errorKey Key of location error.
     */
    void setLocationError(String errorKey);

    /**
     * Get location error.
     * @return Location error.
     */
    String getLocationError();

    /**
     * Set current location.
     * @param location Current location.
     */
    void setCurrentLocation(String location);

    /**
     * Get current location.
     * @return Current location.
     */
    String getCurrentLocation();

    /**
     * Set whether currently logged in account is on vacation.
     * @param vacationStatus Whether currently logged in account is on vacation.
     */
    void setVacationStatus(boolean vacationStatus);

    /**
     * Get whether currently logged in account is on vacation.
     * @return Whether currently logged in account is on vacation.
     */
    boolean getVacationStatus();

    /**
     * Set whether currently logged in account can go on vacation.
     * @param canVacation Whether currently logged in account can go on vacation.
     */
    void setCanVacation(boolean canVacation);

    /**
     * Get whether currently logged in account can go on vacation.
     * @return Whether currently logged in account can go on vacation.
     */
    boolean getCanVacation();

    /**
     * Set whether currently logged in account can request unfreeze.
     * @param canRequestUnfreeze Whether currently logged in account can request unfreeze.
     */
    void setCanRequestUnfreeze(boolean canRequestUnfreeze);

    /**
     * Get whether currently logged in account can request unfreeze.
     * @return Whether currently logged in account can request unfreeze.
     */
    boolean getCanRequestUnfreeze();

    /**
     * Set whether currently logged in account can become trusted.
     * @param canBecomeTrusted Whether currently logged in account can become trusted.
     */
    void setCanBecomeTrusted(boolean canBecomeTrusted);

    /**
     * Get whether currently logged in account can become trusted.
     * @return Whether currently logged in account can become trusted.
     */
    boolean getCanBecomeTrusted();

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
