package org.twelve.presenters.ui;

import org.twelve.presenters.ProfilePresenter;

import java.util.ResourceBundle;

public class UIProfilePresenter extends ObservablePresenter implements ProfilePresenter {

    private final ResourceBundle localizedResources;

    private boolean vacationStatus;
    private boolean canVacation;

    public UIProfilePresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
    }

    @Override
    public void setVacationStatus(boolean vacationStatus) {

        propertyChangeSupport.firePropertyChange("vacationStatus", this.vacationStatus, vacationStatus);
        this.vacationStatus = vacationStatus;
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

}
