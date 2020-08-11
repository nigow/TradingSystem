package org.twelve.presenters.ui;

import org.twelve.entities.Roles;
import org.twelve.presenters.RegistrationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIRegistrationPresenter extends ObservablePresenter implements RegistrationPresenter {

    private String availableTypes;
    private List<String> existingCities;
    private final ResourceBundle localizedResources;
    private String errorMsg;

    public UIRegistrationPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        setExistingCities(new ArrayList<>());
        setError("");
    }

    @Override
    public void setExistingCities(List<String> existingCities) {
        List<String> oldExistingCities = this.existingCities;
        this.existingCities = existingCities;
        propertyChangeSupport.firePropertyChange("existingCities", oldExistingCities, this.existingCities);
    }

    @Override
    public List<String> getExistingCities() {
        return existingCities;
    }

    @Override
    public void setError(String errorKey) {
        String oldErrorMsg = this.errorMsg;
        this.errorMsg = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("error", oldErrorMsg, this.errorMsg);
    }

    @Override
    public String getError() {
        return errorMsg;
    }

    @Override
    public void setAvailableTypes(Roles types) {
        String oldAvailableTypes = this.availableTypes;
        this.availableTypes = localizedResources.getString(types.name().toLowerCase());
        propertyChangeSupport.firePropertyChange("availableTypes", oldAvailableTypes, this.availableTypes);
    }

    @Override
    public String getAvailableTypes() {
        return availableTypes;
    }

}
