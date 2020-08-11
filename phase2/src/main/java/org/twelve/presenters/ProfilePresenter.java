package org.twelve.presenters;

import java.util.List;

public interface ProfilePresenter {

    void setError(String errorKey);
    String getError();
    void setVacationStatus(boolean vacationStatus);
    boolean getVacationStatus();
    void setCanVacation(boolean canVacation);
    boolean getCanVacation();
    void setCanRequestUnfreeze(boolean canRequestUnfreeze);
    boolean getCanRequestUnfreeze();

    void setExistingCities(List<String> existingCities);
    List<String> getExistingCities();
}
