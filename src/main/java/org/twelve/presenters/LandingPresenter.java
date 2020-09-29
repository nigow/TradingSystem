package org.twelve.presenters;

import java.util.List;
import java.util.Locale;

/**
 * An interface for the presenter for the landing page
 */
public interface LandingPresenter {

    /**
     * Set the available languages for the program
     *
     * @param languages the available languages for the program
     */
    void setAvailableLanguages(List<Locale> languages);

    /**
     * Get the available languages for the program
     *
     * @return the available languages for the program
     */
    List<Locale> getAvailableLanguages();

    /**
     * Set whether or not the program will be in demo mode
     *
     * @param demoMode whether or not the program will be in demo mode
     */
    void setDemoMode(boolean demoMode);

    /**
     * Get whether or not the program will be in demo mode
     *
     * @return whether or not the program will be in demo mode
     */
    boolean getDemoMode();

    /**
     * Set the selected language for the program
     *
     * @param selectedLanguage the selected language for the program
     */
    void setSelectedLanguage(Locale selectedLanguage);

    /**
     * Get the selected language for the program
     *
     * @return the selected language for the program
     */
    Locale getSelectedLanguage();

}
