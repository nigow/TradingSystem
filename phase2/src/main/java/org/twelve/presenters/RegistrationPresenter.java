package org.twelve.presenters;

import java.util.List;

public interface RegistrationPresenter {

    void setError(String errorKey);
    String getError();
    void setAdminMode(boolean adminMode);
    boolean getAdminMode();
    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();

}
