package org.twelve.presenters;

import org.twelve.entities.RegistrationTypes;

import java.util.List;

public interface RegistrationPresenter {

    void setError(String errorKey);
    String getError();
    void setAvailableTypes(List<RegistrationTypes> types);
    List<String> getAvailableTypes();
    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();

}
