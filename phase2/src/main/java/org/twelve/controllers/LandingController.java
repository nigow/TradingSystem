package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.LandingPresenter;
import org.twelve.usecases.AccountRepository;
import org.twelve.usecases.SessionManager;
import org.twelve.usecases.ThresholdRepository;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Controller for managing runtime preferences such as display language and demo mode.
 */
public class LandingController {

    private final List<Locale> availableLanguages;
    private final boolean demoMode;
    private final Locale selectedLanguage;

    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    private final ThresholdRepository thresholdRepository;

    private LandingPresenter landingPresenter;

    /**
     * Constructor of controller for managing runtime preferences.
     * @param selectedLanguage Current display language.
     * @param demoMode Whether demo mode is active.
     * @param useCasePool An instance of {@link org.twelve.usecases.UseCasePool}.
     */
    public LandingController(Locale selectedLanguage, boolean demoMode, UseCasePool useCasePool) {

        this.demoMode = demoMode;
        this.selectedLanguage = selectedLanguage;

        availableLanguages = new ArrayList<>();
        availableLanguages.add(Locale.US);
        availableLanguages.add(Locale.SIMPLIFIED_CHINESE);

        accountRepository = useCasePool.getAccountRepository();
        sessionManager = useCasePool.getSessionManager();
        thresholdRepository = useCasePool.getThresholdRepository();

    }

    /**
     * Provides the landing controller with an appropriate presenter.
     * @param landingPresenter An instance of a class that implements {@link org.twelve.presenters.LandingPresenter}.
     */
    public void setLandingPresenter(LandingPresenter landingPresenter) {
        this.landingPresenter = landingPresenter;
    }

    public void displaySettings() {

        landingPresenter.setDemoMode(demoMode);
        landingPresenter.setAvailableLanguages(availableLanguages);
        landingPresenter.setSelectedLanguage(selectedLanguage);

    }

    // todo: whoever made this should provide its JavaDoc.
    public void createDemoAccount() {
        List<Permissions> perms = Arrays.asList(Permissions.LOGIN, Permissions.CREATE_ITEM, Permissions.ADD_TO_WISHLIST,
                Permissions.TRADE, Permissions.BROWSE_INVENTORY, Permissions.REQUEST_VACATION);

        int counter = 1;
        while (!accountRepository.createAccount("demo" + counter, "12345", perms, "Toronto"))
            counter++;
        sessionManager.login("demo" + counter);

        // this loosens the regular threshold values of our program, so that the
        //  demo user can borrow/lend/trade without any restrictions and without
        //  getting frozen.
        thresholdRepository.setLendMoreThanBorrow(-1000);
        thresholdRepository.setMaxIncompleteTrade(1000);
        thresholdRepository.setMaxWeeklyTrade(1000);
        thresholdRepository.setNumberOfEdits(100);
        thresholdRepository.setRequiredTradesForTrusted(10);
    }
}
