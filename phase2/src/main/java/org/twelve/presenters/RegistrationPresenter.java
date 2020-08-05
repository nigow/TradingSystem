package org.twelve.presenters;

import org.twelve.entities.AccountType;

import java.util.List;

public interface RegistrationPresenter {

    void setAvailableTypes(List<AccountType> types);
    List<String> getAvailableTypes();
    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();

}
