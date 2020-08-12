package org.twelve.presenters.ui;

import org.twelve.presenters.ProfilePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Observable profile presenter.
 */
public class UIProfilePresenter extends ObservablePresenter implements ProfilePresenter {

    private final ResourceBundle localizedResources;

    private String location;
    private String passwordError;
    private String locationError;
    private boolean vacationStatus;
    private boolean canVacation;
    private boolean canRequestUnfreeze;
    private boolean canBecomeTrusted;
    private List<String> existingCities;

    /**
     * Constructor for observable profile presenter.
     * @param localizedResources Pack containing any localized strings.
     */
    public UIProfilePresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setExistingCities(new ArrayList<>());
        setPasswordError("");
        setLocationError("");
        setCurrentLocation("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPasswordError(String errorKey) {
        String oldErrorMsg = this.passwordError;
        this.passwordError = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("passwordError", oldErrorMsg, this.passwordError);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocationError(String errorKey) {
        String oldErrorMsg = this.locationError;
        this.locationError = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("locationError", oldErrorMsg, this.locationError);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocationError() {
        return locationError;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentLocation(String location) {
        String oldLocation = this.location;
        this.location = location;
        propertyChangeSupport.firePropertyChange("currentLocation", oldLocation, this.location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentLocation() {
        return location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVacationStatus(boolean vacationStatus) {

        boolean oldVacationStatus = this.vacationStatus;
        this.vacationStatus = vacationStatus;
        propertyChangeSupport.firePropertyChange("vacationStatus", oldVacationStatus, vacationStatus);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getVacationStatus() {
        return vacationStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanVacation(boolean canVacation) {

        boolean oldCanVacation = this.canVacation;
        this.canVacation = canVacation;
        propertyChangeSupport.firePropertyChange("canVacation", oldCanVacation, this.canVacation);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanVacation() {
        return canVacation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanRequestUnfreeze(boolean canRequestUnfreeze) {
        boolean oldCanRequestUnfreeze = this.canRequestUnfreeze;
        this.canRequestUnfreeze = canRequestUnfreeze;
        propertyChangeSupport.firePropertyChange("canRequestUnfreeze", oldCanRequestUnfreeze, this.canRequestUnfreeze);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanRequestUnfreeze() {
        return canRequestUnfreeze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExistingCities(List<String> existingCities) {
        List<String> oldExistingCities = this.existingCities;
        this.existingCities = existingCities;
        propertyChangeSupport.firePropertyChange("existingCities", oldExistingCities, this.existingCities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getExistingCities() {
        return existingCities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCanBecomeTrusted() {
        return canBecomeTrusted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanBecomeTrusted(boolean canBecomeTrusted) {
        boolean oldCanBecomeTrusted = this.canBecomeTrusted;
        this.canBecomeTrusted = canBecomeTrusted;
        propertyChangeSupport.firePropertyChange("canBecomeTrusted", oldCanBecomeTrusted, this.canBecomeTrusted);
    }
}
