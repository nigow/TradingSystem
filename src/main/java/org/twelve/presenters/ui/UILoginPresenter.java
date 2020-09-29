package org.twelve.presenters.ui;

import org.twelve.presenters.LoginPresenter;

import java.util.ResourceBundle;

/**
 * Presenter for the login view
 */
public class UILoginPresenter extends ObservablePresenter implements LoginPresenter {

    private final ResourceBundle localizedResources;
    private String errorMsg;

    /**
     * Constructor for the presenter for the login view
     *
     * @param localizedResources Pack containing any localized strings
     */
    public UILoginPresenter(ResourceBundle localizedResources) {

        this.localizedResources = localizedResources;
        setError("");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setError(String errorKey) {

        String oldErrorMsg = this.errorMsg;
        this.errorMsg = localizedResources.containsKey(errorKey) ? localizedResources.getString(errorKey) : "";
        propertyChangeSupport.firePropertyChange("error", oldErrorMsg, this.errorMsg);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getError() {
        return errorMsg;
    }
}
