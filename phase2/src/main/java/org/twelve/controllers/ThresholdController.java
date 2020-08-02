package org.twelve.controllers;

import org.twelve.gateways.ThresholdsGateway;
import org.twelve.presenters.ThresholdPresenter;
import org.twelve.usecases.ThresholdRepository;
import org.twelve.usecases.UseCasePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that deals with changing restrictions
 * @author Ethan (follow @ethannomiddlenamelam on instagram)
 */
public class ThresholdController {

    /**
     * An instance of ThresholdRepository to set new limits
     */
    private final ThresholdRepository thresholdRepository;

    private final ThresholdsGateway thresholdsGateway;

    private ThresholdPresenter thresholdPresenter;

    /**
     * Initializes the class with thresholdRepository from useCasePool
     * @param useCasePool the useCasePool for getting thresholdRepository
     */
    public ThresholdController(UseCasePool useCasePool, ThresholdsGateway thresholdsGateway) {
        thresholdRepository = useCasePool.getThresholdRepository();
        this.thresholdsGateway = thresholdsGateway;
    }

    /**
     * Calls thresholdRepository and sets LendMoreThanBorrow to the newNumber
     * @param newNumber the new number of items you need to lend more than borrow
     */
    public void lendMoreThanBorrow(int newNumber) {
        if (newNumber != thresholdRepository.getLendMoreThanBorrow())
            thresholdRepository.setLendMoreThanBorrow(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxIncompleteTrades to the newNumber
     * @param newNumber the new number of max incomplete trades you can have
     */
    public void maxIncompleteTrades(int newNumber) {
        if (newNumber != thresholdRepository.getMaxIncompleteTrade())
            thresholdRepository.setMaxIncompleteTrade(newNumber);
    }

    /**
     * Calls thresholdRepository and sets maxWeeklyTrades to the newNumber
     * @param newNumber the new number of max weekly trades you can have
     */
    public void maxWeeklyTrades(int newNumber) {
        if (newNumber != thresholdRepository.getMaxWeeklyTrade())
            thresholdRepository.setMaxWeeklyTrade(newNumber);
    }

    public void numberOfDays(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfDays())
            thresholdRepository.setNumberOfDays(newNumber);
    }

    public void numberOfStats(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfStats())
            thresholdRepository.setNumberOfStats(newNumber);
    }

    public void numberOfEdits(int newNumber) {
        if (newNumber != thresholdRepository.getNumberOfEdits())
            thresholdRepository.setNumberOfEdits(newNumber);
    }

    public void setThresholdPresenter(ThresholdPresenter thresholdPresenter) {
        this.thresholdPresenter = thresholdPresenter;
    }

    public void displayThresholds() {

        thresholdsGateway.populate(thresholdRepository);

        thresholdPresenter.setThresholds(thresholdRepository.getLendMoreThanBorrow(),
                thresholdRepository.getMaxIncompleteTrade(), thresholdRepository.getMaxWeeklyTrade(),
                thresholdRepository.getNumberOfDays(), thresholdRepository.getNumberOfStats(),
                thresholdRepository.getNumberOfEdits());

    }
}
