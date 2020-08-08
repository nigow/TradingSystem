package org.twelve.presenters.ui;

import org.twelve.entities.RegistrationTypes;
import org.twelve.presenters.RegistrationPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIRegistrationPresenter extends ObservablePresenter implements RegistrationPresenter {

    private List<String> availableTypes;
    private List<String> existingCities;
    private final ResourceBundle localizedResources;

    public UIRegistrationPresenter(ResourceBundle localizedResources) {
        this.localizedResources = localizedResources;
        setAvailableTypes(new ArrayList<>());
        setExistingCities(new ArrayList<>());
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
    public void setAvailableTypes(List<RegistrationTypes> types) {
        List<String> oldAvailableTypes = this.availableTypes;
        this.availableTypes = new ArrayList<>();
        for (RegistrationTypes type : types) {
            availableTypes.add(localizedResources.getString(type.name().toLowerCase()));
        }
        propertyChangeSupport.firePropertyChange("availableTypes", oldAvailableTypes, this.availableTypes);
    }

    @Override
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

}
