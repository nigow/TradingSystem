package org.twelve.presenters;

import java.util.List;
import java.util.Locale;

public interface LandingPresenter {

    void setAvailableLanguages(List<Locale> languages);
    List<Locale> getAvailableLanguages();
    void setDemoMode(boolean demoMode);
    boolean getDemoMode();
    void setSelectedLanguage(Locale selectedLanguage);
    Locale getSelectedLanguage();

}
