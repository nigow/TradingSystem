package org.twelve.presenters.ui;

import org.twelve.presenters.LandingPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UILandingPresenter extends ObservablePresenter implements LandingPresenter {

    private List<Locale> availableLanguages;
    private boolean demoMode;
    private Locale selectedLanguage;

    public UILandingPresenter() {

        super();
        setAvailableLanguages(new ArrayList<>());

    }

    @Override
    public void setAvailableLanguages(List<Locale> languages) {
        List<Locale> oldAvailableLanguages = this.availableLanguages;
        this.availableLanguages = languages;
        propertyChangeSupport.firePropertyChange("availableLanguages", oldAvailableLanguages, this.availableLanguages);
    }

    @Override
    public List<Locale> getAvailableLanguages() {
        return availableLanguages;
    }

    @Override
    public void setDemoMode(boolean demoMode) {
        boolean oldDemoMode = this.demoMode;
        this.demoMode = demoMode;
        propertyChangeSupport.firePropertyChange("demoMode", oldDemoMode, this.demoMode);
    }

    @Override
    public boolean getDemoMode() {
        return demoMode;
    }

    @Override
    public void setSelectedLanguage(Locale selectedLanguage) {
        Locale oldSelectedLanguage = this.selectedLanguage;
        this.selectedLanguage = selectedLanguage;
        propertyChangeSupport.firePropertyChange("selectedLanguage", oldSelectedLanguage, this.selectedLanguage);
    }

    @Override
    public Locale getSelectedLanguage() {
        return selectedLanguage;
    }
}
