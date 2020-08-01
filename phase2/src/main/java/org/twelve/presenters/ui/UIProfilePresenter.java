package org.twelve.presenters.ui;

import org.twelve.presenters.ProfilePresenter;

public class UIProfilePresenter extends ObservablePresenter implements ProfilePresenter {

    private boolean vacationStatus;
    private boolean canVacation;
    private boolean canRequestUnfreeze;

    @Override
    public void setVacationStatus(boolean vacationStatus) {

        boolean oldVacationStatus = this.vacationStatus;
        this.vacationStatus = vacationStatus;
        propertyChangeSupport.firePropertyChange("vacationStatus", oldVacationStatus, vacationStatus);

    }

    @Override
    public boolean getVacationStatus() {
        return vacationStatus;
    }

    @Override
    public void setCanVacation(boolean canVacation) {
        propertyChangeSupport.firePropertyChange("canVacation", this.canVacation, canVacation);
        this.canVacation = canVacation;
    }

    @Override
    public boolean getCanVacation() {
        return canVacation;
    }

    @Override
    public void setCanRequestUnfreeze(boolean canRequestUnfreeze) {
        boolean oldCanRequestUnfreeze = this.canRequestUnfreeze;
        this.canRequestUnfreeze = canRequestUnfreeze;
        propertyChangeSupport.firePropertyChange("canRequestUnfreeze", oldCanRequestUnfreeze, this.canRequestUnfreeze);
    }

    @Override
    public boolean getCanRequestUnfreeze() {
        return canRequestUnfreeze;
    }

}
