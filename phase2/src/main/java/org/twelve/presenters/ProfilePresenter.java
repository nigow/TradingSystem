package org.twelve.presenters;

import java.util.List;

public interface ProfilePresenter {

    void setPasswordError(String errorKey);
    String getPasswordError();

    void setLocationError(String errorKey);
    String getLocationError();

    void setCurrentLocation(String location);
    String getCurrentLocation();

    void setVacationStatus(boolean vacationStatus);
    boolean getVacationStatus();
    void setCanVacation(boolean canVacation);
    boolean getCanVacation();
    void setCanRequestUnfreeze(boolean canRequestUnfreeze);
    boolean getCanRequestUnfreeze();
    void setCanBecomeTrusted(boolean canBecomeTrusted);
    boolean getCanBecomeTrusted();

    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();
}
