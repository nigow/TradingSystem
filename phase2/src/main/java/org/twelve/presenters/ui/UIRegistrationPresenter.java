package org.twelve.presenters.ui;

import org.twelve.presenters.RegistrationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Observable registration presenter.
 */
public class UIRegistrationPresenter extends ObservablePresenter implements RegistrationPresenter {

    private boolean adminMode;
    private List<String> existingCities;
    private final ResourceBundle localizedResources;
    private String errorMsg;

    /**
     * Constructor for observable registration presenter.
     * @param localizedResources Pack containing any localized strings.
     */
    public UIRegistrationPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        setExistingCities(new ArrayList<>());
        setError("");
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
    public void setError(String errorKey) {
        String oldErrorMsg = this.errorMsg;
        this.errorMsg = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("error", oldErrorMsg, this.errorMsg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getError() {
        return errorMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdminMode(boolean adminMode) {
        boolean oldAdminMode = this.adminMode;
        this.adminMode = adminMode;
        propertyChangeSupport.firePropertyChange("adminMode", oldAdminMode, this.adminMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getAdminMode() {
        return adminMode;
    }

}
