package org.twelve.presenters.ui;

import org.twelve.presenters.LoginPresenter;

import java.util.ResourceBundle;

public class UILoginPresenter extends ObservablePresenter implements LoginPresenter {

    private final ResourceBundle localizedResources;
    private String errorMsg;

    public UILoginPresenter(ResourceBundle localizedResources) {

        this.localizedResources = localizedResources;

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
}
