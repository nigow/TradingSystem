package org.twelve.presenters.ui;

import org.twelve.presenters.ProfilePresenter;

import java.util.ResourceBundle;

public class UIProfilePresenter extends ObservablePresenter implements ProfilePresenter {

    private final ResourceBundle localizedResources;

    private String errorMsg;
    private boolean vacationStatus;
    private boolean canVacation;
    private boolean canRequestUnfreeze;

    public UIProfilePresenter(ResourceBundle localizedResources) {
        super();
        this.localizedResources = localizedResources;
        setError("");
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

        boolean oldCanVacation = this.canVacation;
        this.canVacation = canVacation;
        propertyChangeSupport.firePropertyChange("canVacation", oldCanVacation, this.canVacation);

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
