package org.twelve.controllers;

import org.twelve.entities.Permissions;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.usecases.system.SessionManager;
import org.twelve.usecases.account.StatusManager;
import org.twelve.usecases.system.ThresholdRepository;
import org.twelve.usecases.UseCasePool;

/**
 * Controller that deals with changing restrictions
 */
public class ThresholdController {

    private final ThresholdRepository thresholdRepository;

    private ThresholdPresenter thresholdPresenter;

    private final UseCasePool useCasePool;

    private final StatusManager statusManager;

    private final SessionManager sessionManager;

    /**
     * Initializes the class with thresholdRepository from useCasePool
     *
     * @param useCasePool the useCasePool for getting thresholdRepository
     */
    public ThresholdController(UseCasePool useCasePool) {
        thresholdRepository = useCasePool.getThresholdRepository();
        this.useCasePool = useCasePool;
        this.statusManager = useCasePool.getStatusManager();
        this.sessionManager = useCasePool.getSessionManager();
    }

    /**
     * Calls thresholdRepository and sets LendMoreThanBorrow to the newNumber
     *
     * @param newNumber the new number of items you need to lend more than borrow
     */
    public void lendMoreThanBorrow(int newNumber) {
        if (newNumber != thresholdRepository.getLendMoreThanBorrow())
            thresholdRepository.setLendMoreThanBorrow(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxIncompleteTrades to the newNumber
     *
     * @param newNumber the new number of max incomplete trades you can have
     */
    public void maxIncompleteTrades(int newNumber) {
        if (newNumber != thresholdRepository.getMaxIncompleteTrade())
            thresholdRepository.setMaxIncompleteTrade(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxWeeklyTrades to the newNumber
     *
     * @param newNumber the new number of max weekly trades you can have
     */
    public void maxWeeklyTrades(int newNumber) {
        if (newNumber != thresholdRepository.getMaxWeeklyTrade())
            thresholdRepository.setMaxWeeklyTrade(newNumber);
    }

    /**
     * Calls thresholdRepository and sets numberOfDays to the newNumber
     *
     * @param newNumber the new number of max weekly trades you can have
     */
    public void numberOfDays(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfDays())
            thresholdRepository.setNumberOfDays(newNumber);
    }

    /**
     * Calls thresholdRepository and sets numberOfStats to the newNumber
     *
     * @param newNumber the new number of max weekly trades you can have
     */
    public void numberOfStats(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfStats())
            thresholdRepository.setNumberOfStats(newNumber);
    }

    /**
     * Calls thresholdRepository and sets numberOfEdits to the newNumber
     *
     * @param newNumber the new number of max weekly trades you can have
     */
    public void numberOfEdits(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfEdits())
            thresholdRepository.setNumberOfEdits(newNumber);
    }

    /**
     * Calls thresholdRepository and sets numberOfTradesUntilTrusted to the newNumber
     *
     * @param newNumber the new number of max weekly trades you can have
     */
    public void numberOfTradesUntilTrusted(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfEdits())
            thresholdRepository.setRequiredTradesForTrusted(newNumber);
    }

    /**
     * Set the presenter for this controller
     *
     * @param thresholdPresenter an instance of a class that implements {@link org.twelve.presenters.ThresholdPresenter}
     */
    public void setThresholdPresenter(ThresholdPresenter thresholdPresenter) {
        this.thresholdPresenter = thresholdPresenter;
    }

    /**
     * Re-populate the threshold values in the presenter
     */
    public void displayThresholds() {

        useCasePool.populateAll();

        thresholdPresenter.setThresholds(thresholdRepository.getLendMoreThanBorrow(),
                thresholdRepository.getMaxIncompleteTrade(), thresholdRepository.getMaxWeeklyTrade(),
                thresholdRepository.getNumberOfDays(), thresholdRepository.getNumberOfStats(),
                thresholdRepository.getNumberOfEdits(), thresholdRepository.getRequiredTradesForTrusted());

        thresholdPresenter.setIsAdmin(statusManager.hasPermission(sessionManager.getCurrAccountID(), Permissions.CHANGE_THRESHOLDS));

    }
}
