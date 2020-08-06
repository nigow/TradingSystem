package org.twelve.controllers;

import org.twelve.presenters.LandingPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LandingController {

    private LandingPresenter landingPresenter;
    private List<Locale> availableLanguages;
    private boolean demoMode;
    private Locale selectedLanguage;

    public LandingController(Locale selectedLanguage, boolean demoMode) {

        this.demoMode = demoMode;
        this.selectedLanguage = selectedLanguage;

        availableLanguages = new ArrayList<>();
        availableLanguages.add(Locale.US);
        availableLanguages.add(Locale.SIMPLIFIED_CHINESE);

    }

    public void setLandingPresenter(LandingPresenter landingPresenter) {
        this.landingPresenter = landingPresenter;
        this.landingPresenter.setDemoMode(demoMode);
        this.landingPresenter.setAvailableLanguages(availableLanguages);
        this.landingPresenter.setSelectedLanguage(selectedLanguage);
    }

}
