package org.twelve.presenters;

import org.twelve.entities.Roles;

import java.util.List;

public interface RegistrationPresenter {

    void setError(String errorKey);
    String getError();
    void setAvailableTypes(Roles types);
    String getAvailableTypes();
    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();

}
